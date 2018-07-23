package com.csl.adi.controller;

import com.csl.adi.model.AdiTag;
import com.csl.adi.model.Erro;
import com.csl.adi.model.Info;
import com.csl.adi.repository.*;
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
@RequestMapping ("api/v1/info")
public class InfoController {

    @Autowired
    private InfoRepository infoRepository;
    @Autowired
    private AdiTagRepository adiTagRepository;

    @GetMapping ("")
    public ResponseEntity<?> getInfoLista (
            @PageableDefault Pageable pageable,
            @RequestParam (required = false) String tipo,
            @RequestParam (required = false) String nome
            ){

        if (tipo == null) tipo = "";
        if (nome == null) nome = "";

        Page<Info> page = infoRepository.findByTipoIgnoreCaseContainingAndNomeIgnoreCaseContaining(
                pageable, tipo, nome);

        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping ("/aditag/{adiTag}")
    public ResponseEntity<?> getInfoPorAdiTag (@PathVariable Long adiTag){
        AdiTag found = adiTagRepository.findOneById(adiTag);
        if (found == null){
            Erro erro = new Erro(HttpStatus.NOT_FOUND,"AdiTag não encontrada com ID: " + adiTag );
            return new ResponseEntity<>(erro, erro.getHttpStatus());
        } else {
            Info info = infoRepository.findByAdiTag(found.getId());
            if (info == null){
                Erro erro = new Erro(HttpStatus.NOT_FOUND,
                        "Info não encontrada para AdiTag ID: " + adiTag + " TAG: " + found.printAdiTag() );
                return new ResponseEntity<>(erro, erro.getHttpStatus());
            } else {
                return new ResponseEntity<>(info, HttpStatus.OK);
            }
        }
    }

    @GetMapping ("/id/{id}")
    public ResponseEntity<?> getInfoPorId (@PathVariable Long id){
        Info info = infoRepository.findOneById(id);
        if (info == null){
            Erro erro = new Erro(HttpStatus.NOT_FOUND,"Info não encontrada com ID: " + id );
            return new ResponseEntity<>(erro, erro.getHttpStatus());
        } else {
            return new ResponseEntity<>(info, HttpStatus.OK);
        }
    }

    @GetMapping ("/tipo/{tipo}/tag/{tag}")
    public ResponseEntity<?> getInfoPorTipoETag (@PathVariable String tipo, @PathVariable String tag){
        Info info = infoRepository.findByTipoIgnoreCaseAndTagIgnoreCase(tipo, tag);
        if (info == null){
            Erro erro = new Erro(HttpStatus.NOT_FOUND,"Info não encontrada com TIPO: " + tipo + " TAG: " + tag );
            return new ResponseEntity<>(erro, erro.getHttpStatus());
        } else {
            return new ResponseEntity<>(info, HttpStatus.OK);
        }
    }
}
