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
@RequestMapping(path = "/api/v1/url")
public class UrlController {

    @Autowired
    private UrlRepository urlRepository;

    @Autowired
    private OrigemRepository origemRepository;

    @GetMapping (path = "")
    public @ResponseBody ResponseEntity getListaUrls () {
        return new ResponseEntity(urlRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping (path = "/origem/{origem}")
    public @ResponseBody ResponseEntity getListaUrlsPorOrigem (@PathVariable Integer origem) {
        if (!origemRepository.existsById(origem)){
            Erro erro = new Erro(HttpStatus.NOT_FOUND,"Origem não encontrada com ID: " + origem );
            return new ResponseEntity(erro, erro.getHttpStatus());
        } else {
            return new ResponseEntity(urlRepository.findByOrigem(origem), HttpStatus.OK);
        }
    }

    @PostMapping (path = "")
    public ResponseEntity postUrl (@RequestBody Url url){
        if (!origemRepository.existsById(url.getOrigem())){
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

}
