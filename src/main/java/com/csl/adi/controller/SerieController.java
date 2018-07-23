package com.csl.adi.controller;

import com.csl.adi.model.AdiTag;
import com.csl.adi.model.Serie;
import com.csl.adi.model.Erro;
import com.csl.adi.repository.AdiTagRepository;
import com.csl.adi.repository.SerieRepository;
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
@RequestMapping("api/v1/info/serie")
public class SerieController {

    @Autowired
    private SerieRepository serieRepository;
    @Autowired
    private AdiTagRepository adiTagRepository;

    @GetMapping("")
    public ResponseEntity<?> getSerieLista (
            @PageableDefault Pageable pageable,
            @RequestParam (required = false) String nome
    ){
        if (nome == null) nome = "";

        Page<Serie> page = serieRepository.findByNomeIgnoreCaseContainingOrNomeAlternativoIgnoreCaseContaining (pageable, nome, nome);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping("/tag/{tag}")
    public ResponseEntity<?> getSeriePorTag (
            @PathVariable String tag
    ){
        Serie serie = serieRepository.findByTagIgnoreCase(tag);
        if (serie == null){
            Erro erro = new Erro(HttpStatus.NOT_FOUND,"Info-Série não encontrada com TAG: " + tag.toLowerCase() );
            return new ResponseEntity<>(erro, erro.getHttpStatus());
        } else {
            return new ResponseEntity<>(serie, HttpStatus.OK);
        }
    }

    @PostMapping("")
    public ResponseEntity<?> postSerie (@RequestBody Serie serie){
        AdiTag adiTag = adiTagRepository.findOneById(serie.getAdiTag());
        if (adiTag == null){
            Erro erro = new Erro(HttpStatus.NOT_FOUND,"AdiTag não encontrada com ID: " + serie.getAdiTag() );
            return new ResponseEntity<>(erro, erro.getHttpStatus());
        } else {
            String tipoReq = "c";
            if (!adiTag.getTipo().equals(tipoReq)){
                Erro erro = new Erro(HttpStatus.BAD_REQUEST,"AdiTag inválida para Artista - TAG: " + adiTag.printAdiTag()
                        + " Tipo requerido: '"+tipoReq+"' Tipo encontrado: '"+ adiTag.getTipo() + "'");
                return new ResponseEntity<>(erro, erro.getHttpStatus());
            } else {
                serie.setTipo(adiTag.getTipo());

                if (serie.getTag() == null) {
                    serie.setTag(adiTag.getTag());
                }

                try {
                    Serie created = serieRepository.save(serie);
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
    public ResponseEntity<?> putSeriePorId (@PathVariable Long id, @RequestBody Serie serie){
        Serie original = serieRepository.findOneById(id);
        if (original == null){
            Erro erro = new Erro(HttpStatus.NOT_FOUND,"Info-Série não encontrada com ID: " + id );
            return new ResponseEntity<>(erro, erro.getHttpStatus());
        } else {
            serie.setId(original.getId());
            serie.setAdiTag(original.getAdiTag());

            AdiTag adiTag = adiTagRepository.findOneById(serie.getAdiTag());
            if (adiTag == null){
                Erro erro = new Erro(HttpStatus.NOT_FOUND,"AdiTag não encontrada com ID: " + serie.getAdiTag() );
                return new ResponseEntity<>(erro, erro.getHttpStatus());
            } else {
                serie.setTipo(adiTag.getTipo());
                if (serie.getTag() == null) {
                    serie.setTag(adiTag.getTag());
                }
                try {
                    serieRepository.save(serie);
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } catch (Exception e){
                    Erro erro = new Erro(500,e.getMessage());
                    return new ResponseEntity<>(erro, erro.getHttpStatus());
                }
            }


        }
    }
}
