package com.csl.adi.controller;

import com.csl.adi.model.AdiTag;
import com.csl.adi.model.Erro;
import com.csl.adi.model.Personagem;
import com.csl.adi.repository.AdiTagRepository;
import com.csl.adi.repository.PersonagemRepository;
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
@RequestMapping("api/v1/info/personagem")
public class PersonagemController {

    @Autowired
    private PersonagemRepository personagemRepository;
    @Autowired
    private AdiTagRepository adiTagRepository;

    @GetMapping("")
    public ResponseEntity<?> getPersonagemLista (
            @PageableDefault Pageable pageable,
            @RequestParam (required = false) String nome,
            @RequestParam (required = false) Long serie,
            @RequestParam (required = false) Long artista
    ){
        if (nome == null) nome = "";

        Page<Personagem> page;
        if (serie == null) {
            page = personagemRepository.findByNomeIgnoreCaseContaining(pageable, nome);
        } else {
            if (artista == null) {
                page = personagemRepository.findByNomeIgnoreCaseContainingAndSerie(pageable, nome, serie);
            } else {
                page = personagemRepository.findByNomeIgnoreCaseContainingAndSerieAndArtista(pageable, nome, serie, artista);
            }
        }
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping("/tag/{tag}")
    public ResponseEntity<?> getPersonagemPorTag (@PathVariable String tag){
        Personagem personagem = personagemRepository.findByTagIgnoreCase(tag);
        if (personagem == null){
            Erro erro = new Erro(HttpStatus.NOT_FOUND,"Info-Personagem não encontrada com TAG: " + tag.toLowerCase() );
            return new ResponseEntity<>(erro, erro.getHttpStatus());
        } else {
            return new ResponseEntity<>(personagem, HttpStatus.OK);
        }
    }

    @PostMapping("")
    public ResponseEntity<?> postPersonagem (@RequestBody Personagem personagem){
        AdiTag adiTag = adiTagRepository.findOneById(personagem.getAdiTag());
        if (adiTag == null){
            Erro erro = new Erro(HttpStatus.NOT_FOUND,"AdiTag não encontrada com ID: " + personagem.getAdiTag() );
            return new ResponseEntity<>(erro, erro.getHttpStatus());
        } else {
            String tipoReq = "p";
            if (!adiTag.getTipo().equals(tipoReq)){
                Erro erro = new Erro(HttpStatus.BAD_REQUEST,"AdiTag inválida para Personagem - TAG: " + adiTag.printAdiTag()
                        + " Tipo requerido: '" + tipoReq + "' Tipo encontrado: '"+ adiTag.getTipo() + "'");
                return new ResponseEntity<>(erro, erro.getHttpStatus());
            } else {
                personagem.setTipo(adiTag.getTipo());

                if (personagem.getTag() == null) {
                    personagem.setTag(adiTag.getTag());
                }

                try {
                    Personagem created = personagemRepository.save(personagem);
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
    public ResponseEntity<?> putPersonagemPorId (@PathVariable Long id, @RequestBody Personagem personagem){
        Personagem original = personagemRepository.findOneById(id);
        if (original == null){
            Erro erro = new Erro(HttpStatus.NOT_FOUND,"Info-Personagem não encontrada com ID: " + id );
            return new ResponseEntity<>(erro, erro.getHttpStatus());
        } else {
            personagem.setId(original.getId());
            personagem.setAdiTag(original.getAdiTag());

            AdiTag adiTag = adiTagRepository.findOneById(personagem.getAdiTag());
            if (adiTag == null){
                Erro erro = new Erro(HttpStatus.NOT_FOUND,"AdiTag não encontrada com ID: " + personagem.getAdiTag() );
                return new ResponseEntity<>(erro, erro.getHttpStatus());
            } else {
                personagem.setTipo(adiTag.getTipo());
                if (personagem.getTag() == null) {
                    personagem.setTag(adiTag.getTag());
                }
                try {
                    personagemRepository.save(personagem);
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } catch (Exception e){
                    Erro erro = new Erro(500,e.getMessage());
                    return new ResponseEntity<>(erro, erro.getHttpStatus());
                }
            }
        }
    }
}
