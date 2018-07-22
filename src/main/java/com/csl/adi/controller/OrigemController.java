package com.csl.adi.controller;

import com.csl.adi.model.Erro;
import com.csl.adi.model.Origem;
import com.csl.adi.repository.OrigemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/origem")
public class OrigemController {

    @Autowired
    private OrigemRepository origemRepository;

    @GetMapping (path = "")
    public @ResponseBody ResponseEntity<List> getListaOrigens() {
        return new ResponseEntity<>(origemRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping (path = "/id/{id}")
    public @ResponseBody ResponseEntity getOrigemPorId(@PathVariable Long id) {
        Origem origem = origemRepository.findOneById(id);
        if (origem == null){
            Erro erro = new Erro(HttpStatus.NOT_FOUND,"Origem não encontrada com ID: " + id );
            return new ResponseEntity(erro, erro.getHttpStatus());
        } else {
            return new ResponseEntity(origem, HttpStatus.OK);
        }
    }

    @GetMapping (path = "/sigla/{sigla}")
    public @ResponseBody ResponseEntity getOrigemPorSigla (@PathVariable String sigla) {
        Origem origem = origemRepository.findBySigla(sigla);
        if (origem == null){
            Erro erro = new Erro(HttpStatus.NOT_FOUND,"Origem não encontrada com Sigla: " + sigla );
            return new ResponseEntity(erro, erro.getHttpStatus());
        } else {
            return new ResponseEntity(origem, HttpStatus.OK);
        }
    }

    @PostMapping (path = "")
    public @ResponseBody ResponseEntity postOrigem (@RequestBody Origem origem) {
        try {
            Origem created = origemRepository.save(origem);
            return new ResponseEntity(created, HttpStatus.CREATED);
        } catch (Exception e){
            Erro erro = new Erro( HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            return new ResponseEntity(erro, erro.getHttpStatus());
        }
    }

    @PutMapping (path = "/id/{id}")
    public @ResponseBody ResponseEntity updateOrigemById (@PathVariable Long id, @RequestBody Origem origem){
        Origem original = origemRepository.findOneById(id);
        if (original == null){
            Erro erro = new Erro(HttpStatus.NOT_FOUND,"Origem não encontrada com ID: " + id );
            return new ResponseEntity(erro, erro.getHttpStatus());
        } else {
            try {
                origem.setId(original.getId());
                origem.setSigla(original.getSigla());
                origemRepository.save(origem);
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            } catch (Exception e){
                Erro erro = new Erro( HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
                return new ResponseEntity(erro, erro.getHttpStatus());
            }
        }
    }

    @DeleteMapping (path = "/id/{id}")
    public @ResponseBody ResponseEntity deleteOrigemById(@PathVariable Long id) {
        Origem origem = origemRepository.findOneById(id);
        if (origem == null){
            Erro erro = new Erro(HttpStatus.NOT_FOUND,"Origem não encontrada com ID: " + id );
            return new ResponseEntity(erro, erro.getHttpStatus());
        } else {
            try {
                origemRepository.delete(origem);
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            } catch (Exception e){
                Erro erro = new Erro( HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
                return new ResponseEntity(erro, erro.getHttpStatus());
            }
        }
    }

}
