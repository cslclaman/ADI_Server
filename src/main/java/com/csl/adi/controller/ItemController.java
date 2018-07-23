package com.csl.adi.controller;

import com.csl.adi.model.AdiTag;
import com.csl.adi.model.Erro;
import com.csl.adi.model.Item;
import com.csl.adi.repository.AdiTagRepository;
import com.csl.adi.repository.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/info/item")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private AdiTagRepository adiTagRepository;

    @GetMapping("")
    public ResponseEntity<?> getItemLista (
            @PageableDefault Pageable pageable,
            @RequestParam (required = false) String nome,
            @RequestParam (required = false) Integer alcance
    ){
        if (nome == null) nome = "";

        Page<Item> page;
        if (alcance == null) {
            page = itemRepository.findByNomeIgnoreCaseContaining(pageable, nome);
        } else {
            page = itemRepository.findByNomeIgnoreCaseContainingAndAlcance(pageable, nome, alcance);
        }
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping("/tag/{tag}")
    public ResponseEntity<?> getItemPorTag (@PathVariable String tag){
        Item item = itemRepository.findByTagIgnoreCase(tag);
        if (item == null){
            Erro erro = new Erro(HttpStatus.NOT_FOUND,"Info-Item não encontrada com TAG: " + tag.toLowerCase() );
            return new ResponseEntity<>(erro, erro.getHttpStatus());
        } else {
            return new ResponseEntity<>(item, HttpStatus.OK);
        }
    }

    @PostMapping("")
    public ResponseEntity<?> postItem (@RequestBody Item item){
        AdiTag adiTag = adiTagRepository.findOneById(item.getAdiTag());
        if (adiTag == null){
            Erro erro = new Erro(HttpStatus.NOT_FOUND,"AdiTag não encontrada com ID: " + item.getAdiTag() );
            return new ResponseEntity<>(erro, erro.getHttpStatus());
        } else {
            String tipoReq = "i";
            if (!adiTag.getTipo().equals(tipoReq)){
                Erro erro = new Erro(HttpStatus.BAD_REQUEST,"AdiTag inválida para Item - TAG: " + adiTag.printAdiTag()
                        + " Tipo requerido: '" + tipoReq + "' Tipo encontrado: '"+ adiTag.getTipo() + "'");
                return new ResponseEntity<>(erro, erro.getHttpStatus());
            } else {
                item.setTipo(adiTag.getTipo());

                if (item.getTag() == null) {
                    item.setTag(adiTag.getTag());
                }

                try {
                    Item created = itemRepository.save(item);
                    adiTag.setArquivo(item.getAlcance() == Item.ALCANCE_ACOES || item.getAlcance() == Item.ALCANCE_CENARIO);
                    adiTag.setHasInfo(true);
                    adiTagRepository.save(adiTag);
                    return new ResponseEntity<>(created, HttpStatus.CREATED);
                } catch (Exception e) {
                    Erro erro = new Erro(500, e.getMessage());
                    return new ResponseEntity<>(erro, erro.getHttpStatus());
                }
            }
        }
    }

    @PutMapping ("/id/{id}")
    public ResponseEntity<?> putItemPorId (@PathVariable Long id, @RequestBody Item item){
        Item original = itemRepository.findOneById(id);
        if (original == null){
            Erro erro = new Erro(HttpStatus.NOT_FOUND,"Info-Item não encontrada com ID: " + id );
            return new ResponseEntity<>(erro, erro.getHttpStatus());
        } else {
            item.setId(original.getId());
            item.setAdiTag(original.getAdiTag());

            AdiTag adiTag = adiTagRepository.findOneById(item.getAdiTag());
            if (adiTag == null){
                Erro erro = new Erro(HttpStatus.NOT_FOUND,"AdiTag não encontrada com ID: " + item.getAdiTag() );
                return new ResponseEntity<>(erro, erro.getHttpStatus());
            } else {
                item.setTipo(adiTag.getTipo());
                if (item.getTag() == null) {
                    item.setTag(adiTag.getTag());
                }
                try {
                    itemRepository.save(item);
                    adiTag.setArquivo(item.getAlcance() == Item.ALCANCE_ACOES || item.getAlcance() == Item.ALCANCE_CENARIO);
                    adiTagRepository.save(adiTag);
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } catch (Exception e){
                    Erro erro = new Erro(500,e.getMessage());
                    return new ResponseEntity<>(erro, erro.getHttpStatus());
                }
            }
        }
    }
}
