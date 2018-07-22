package com.csl.adi.controller;

import com.csl.adi.bean.MessageBean;
import com.csl.adi.model.AdiTag;
import com.csl.adi.model.Erro;
import com.csl.adi.repository.AdiTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping ("api/v1/aditag")
public class AdiTagController {

    @Autowired
    private AdiTagRepository adiTagRepository;

    @GetMapping ("")
    public ResponseEntity<?> getAdiTagList (
            @PageableDefault Pageable pageable,
            @RequestParam (name = "tipo", required = false) String tipo,
            @RequestParam (name = "tag", required = false) String tag
    ){
        if (tipo == null) tipo = "";
        if (tag == null) tag = "";

        Page<AdiTag> page = adiTagRepository.findAdiTagByTipoIgnoreCaseContainingAndTagIgnoreCaseContaining(pageable, tipo, tag);
        return new ResponseEntity<Page<AdiTag>>(page, HttpStatus.OK);
    }

    @GetMapping ("id/{id}")
    public ResponseEntity<?> getAdiTagById (@PathVariable Long id){
        AdiTag adiTag = adiTagRepository.findOneById(id);
        if (adiTag == null){
            Erro erro = new Erro(HttpStatus.NOT_FOUND,"AdiTag não encontrada com ID: " + id );
            return new ResponseEntity<Erro>(erro, erro.getHttpStatus());
        } else {
            return new ResponseEntity<AdiTag>(adiTag, HttpStatus.OK);
        }
    }

    @PostMapping ("")
    public ResponseEntity<?> postAdiTag (@RequestBody AdiTag adiTag){
        try {
            AdiTag created = adiTagRepository.save(adiTag);
            return new ResponseEntity<AdiTag>(created, HttpStatus.CREATED);
        } catch (Exception e){
            Erro erro = new Erro( HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            return new ResponseEntity<Erro>(erro, erro.getHttpStatus());
        }
    }

    @PostMapping ("list")
    public ResponseEntity<?> postAdiTagList (@RequestBody List<AdiTag> adiTagList){
        List<MessageBean<AdiTag>> cadastradas = new LinkedList<>();
        List<MessageBean<AdiTag>> naoCadastr = new LinkedList<>();

        for (AdiTag adiTag : adiTagList){
            try {
                adiTag = adiTagRepository.save(adiTag);
                cadastradas.add(new MessageBean<AdiTag>(adiTag, "Ok - ID " + adiTag.getId()));
            } catch (Exception e){
                naoCadastr.add(new MessageBean<AdiTag>(adiTag,e.getMessage()));
            }
        }

        List<List<MessageBean<AdiTag>>>[] list = new List[] {cadastradas, naoCadastr};
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PutMapping ("id/{id}")
    public ResponseEntity<?> putAdiTagById (@PathVariable Long id, @RequestBody AdiTag adiTag){
        AdiTag original = adiTagRepository.findOneById(id);
        if (original == null){
            Erro erro = new Erro(404, "AdiTag não encontrada com ID: " + id);
            return new ResponseEntity<Erro>(erro, erro.getHttpStatus());
        } else {
            adiTag.setId(original.getId());
            try {
                adiTagRepository.save(adiTag);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } catch (Exception e){
                Erro erro = new Erro(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
                return new ResponseEntity<Erro>(erro, erro.getHttpStatus());
            }
        }
    }

}
