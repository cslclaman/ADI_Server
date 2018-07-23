package com.csl.adi.controller;

import com.csl.adi.bean.MessageBean;
import com.csl.adi.bean.PostListResultBean;
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
    public ResponseEntity<?> getAdiTagLista (
            @PageableDefault Pageable pageable,
            @RequestParam (required = false) String tipo,
            @RequestParam (required = false) String tag,
            @RequestParam (required = false) Boolean info
    ){
        if (tipo == null) tipo = "";
        if (tag == null) tag = "";

        Page<AdiTag> page;
        if (info == null){
            page = adiTagRepository.findByTipoIgnoreCaseContainingAndTagIgnoreCaseContaining(pageable, tipo, tag);
        } else {
            page = adiTagRepository.findByTipoIgnoreCaseContainingAndTagIgnoreCaseContainingAndHasInfo(pageable, tipo, tag, info);
        }

        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping ("id/{id}")
    public ResponseEntity<?> getAdiTagPorId (@PathVariable Long id){
        AdiTag adiTag = adiTagRepository.findOneById(id);
        if (adiTag == null){
            Erro erro = new Erro(HttpStatus.NOT_FOUND,"AdiTag não encontrada com ID: " + id );
            return new ResponseEntity<>(erro, erro.getHttpStatus());
        } else {
            return new ResponseEntity<>(adiTag, HttpStatus.OK);
        }
    }

    @PostMapping ("")
    public ResponseEntity<?> postAdiTag (@RequestBody AdiTag adiTag){
        if (adiTag.getArquivo() == null) adiTag.setArquivo(true);
        if (adiTag.getHasInfo() == null) adiTag.setHasInfo(false);
        try {
            AdiTag created = adiTagRepository.save(adiTag);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (Exception e){
            Erro erro = new Erro( HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            return new ResponseEntity<>(erro, erro.getHttpStatus());
        }
    }

    @PostMapping ("list")
    public ResponseEntity<?> postAdiTagLista (@RequestBody List<AdiTag> adiTagList){
        List<MessageBean<AdiTag>> cadastradas = new LinkedList<>();
        List<MessageBean<AdiTag>> naoCadastr = new LinkedList<>();

        for (AdiTag adiTag : adiTagList){
            if (adiTag.getArquivo() == null) adiTag.setArquivo(true);
            if (adiTag.getHasInfo() == null) adiTag.setHasInfo(false);
            try {
                adiTag = adiTagRepository.save(adiTag);
                cadastradas.add(new MessageBean<>(adiTag, "Ok - ID " + adiTag.getId()));
            } catch (Exception e){
                naoCadastr.add(new MessageBean<>(adiTag,e.getMessage()));
            }
        }

        PostListResultBean<MessageBean<AdiTag>> res = new PostListResultBean<>(cadastradas, naoCadastr);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping ("id/{id}")
    public ResponseEntity<?> putAdiTagPorId (@PathVariable Long id, @RequestBody AdiTag adiTag){
        AdiTag original = adiTagRepository.findOneById(id);
        if (original == null){
            Erro erro = new Erro(404, "AdiTag não encontrada com ID: " + id);
            return new ResponseEntity<>(erro, erro.getHttpStatus());
        } else {
            adiTag.setId(original.getId());
            if (adiTag.getArquivo() == null) adiTag.setArquivo(original.getArquivo());
            if (adiTag.getHasInfo() == null) adiTag.setHasInfo(original.getHasInfo());
            try {
                adiTagRepository.save(adiTag);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } catch (Exception e){
                Erro erro = new Erro(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
                return new ResponseEntity<>(erro, erro.getHttpStatus());
            }
        }
    }

}
