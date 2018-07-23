package com.csl.adi.controller;

import com.csl.adi.model.Erro;
import com.csl.adi.model.Url;
import com.csl.adi.repository.OrigemRepository;
import com.csl.adi.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/url")
public class UrlController {

    @Autowired
    private UrlRepository urlRepository;

    @Autowired
    private OrigemRepository origemRepository;

    @GetMapping (path = "")
    public ResponseEntity<?> getUrlLista () {
        return new ResponseEntity(urlRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping (path = "origem/{origem}")
    public ResponseEntity<?> getUrlListaPorOrigem (@PathVariable Long origem) {
        if (origemRepository.findOneById(origem) == null){
            Erro erro = new Erro(HttpStatus.NOT_FOUND,"Origem não encontrada com ID: " + origem );
            return new ResponseEntity(erro, erro.getHttpStatus());
        } else {
            return new ResponseEntity(urlRepository.findByOrigem(origem), HttpStatus.OK);
        }
    }

    @PostMapping (path = "")
    public ResponseEntity<?> postUrl (@RequestBody Url url){
        if (origemRepository.findOneById(url.getOrigem()) == null){
            Erro erro = new Erro(HttpStatus.NOT_FOUND,"Origem não encontrada com ID: " + url.getOrigem() );
            return new ResponseEntity(erro, erro.getHttpStatus());
        } else {
            try {
                Url created = urlRepository.save(url);
                return new ResponseEntity(created, HttpStatus.CREATED);
            } catch (Exception e){
                Erro erro = new Erro( HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
                return new ResponseEntity(erro, erro.getHttpStatus());
            }
        }
    }

    @PutMapping (path = "/id/{id}")
    public ResponseEntity<?> putUrlPorId (@PathVariable Long id, @RequestBody Url url){
        Url original = urlRepository.findOneById(id);
        if (original == null) {
            Erro erro = new Erro(HttpStatus.NOT_FOUND, "Url não encontrada com ID: " + id);
            return new ResponseEntity(erro, erro.getHttpStatus());
        } else {
            try {
                url.setId(original.getId());
                urlRepository.save(url);
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            } catch (Exception e) {
                Erro erro = new Erro(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
                return new ResponseEntity(erro, erro.getHttpStatus());
            }
        }
    }

    @DeleteMapping (path = "/id/{id}")
    public ResponseEntity<?> deleteUrlPorId(@PathVariable Long id) {
        Url url = urlRepository.findOneById(id);
        if (url == null){
            Erro erro = new Erro(HttpStatus.NOT_FOUND,"Url não encontrada com ID: " + id );
            return new ResponseEntity(erro, erro.getHttpStatus());
        } else {
            try {
                urlRepository.delete(url);
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            } catch (Exception e){
                Erro erro = new Erro(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
                return new ResponseEntity(erro, erro.getHttpStatus());
            }
        }
    }
}
