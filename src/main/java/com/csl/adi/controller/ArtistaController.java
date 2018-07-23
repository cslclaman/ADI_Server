package com.csl.adi.controller;

import com.csl.adi.model.AdiTag;
import com.csl.adi.model.Artista;
import com.csl.adi.model.Erro;
import com.csl.adi.repository.AdiTagRepository;
import com.csl.adi.repository.ArtistaRepository;
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
@RequestMapping("api/v1/info/artista")
public class ArtistaController {

    @Autowired
    private ArtistaRepository artistaRepository;
    @Autowired
    private AdiTagRepository adiTagRepository;

    @GetMapping("")
    public ResponseEntity<?> getArtistaLista (
            @PageableDefault Pageable pageable,
            @RequestParam (required = false) String nome,
            @RequestParam(required = false) Boolean ativo
    ){
        if (nome == null) nome = "";

        Page<Artista> page;

        if (ativo == null){
            page = artistaRepository.findByNomeIgnoreCaseContaining (pageable, nome);
        } else {
            page = artistaRepository.findByNomeIgnoreCaseContainingAndAtivo (pageable, nome, ativo);
        }
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping("/tag/{tag}")
    public ResponseEntity<?> getArtistaPorTag (
            @PathVariable String tag
    ){
        Artista artista = artistaRepository.findByTagIgnoreCase(tag);
        if (artista == null){
            Erro erro = new Erro(HttpStatus.NOT_FOUND,"Info-Artista não encontrada com TAG: " + tag.toLowerCase() );
            return new ResponseEntity<>(erro, erro.getHttpStatus());
        } else {
            return new ResponseEntity<>(artista, HttpStatus.OK);
        }
    }

    @PostMapping("")
    public ResponseEntity<?> postArtista (@RequestBody Artista artista){
        AdiTag adiTag = adiTagRepository.findOneById(artista.getAdiTag());
        if (adiTag == null){
            Erro erro = new Erro(HttpStatus.NOT_FOUND,"AdiTag não encontrada com ID: " + artista.getAdiTag() );
            return new ResponseEntity<>(erro, erro.getHttpStatus());
        } else {
            String tipoReq = "a";
            if (!adiTag.getTipo().equals(tipoReq)){
                Erro erro = new Erro(HttpStatus.BAD_REQUEST,"AdiTag inválida para Artista - TAG: " + adiTag.printAdiTag()
                + " Tipo requerido: '"+tipoReq+"' Tipo encontrado: '"+ adiTag.getTipo() + "'");
                return new ResponseEntity<>(erro, erro.getHttpStatus());
            } else {
                artista.setTipo(adiTag.getTipo());

                if (artista.getTag() == null) {
                    artista.setTag(adiTag.getTag());
                }
                if (artista.getNome() == null) {
                    String nome = adiTag.getTag();
                    nome = nome.replace("_", " ");
                    String[] nomes = nome.split(" ");
                    StringBuilder sb = new StringBuilder(adiTag.getTag().length() + 1);
                    for (String nm : nomes) {
                        if (nm.length() > 1) {
                            sb.append(nm.substring(0, 1).toUpperCase());
                            sb.append(nm.substring(1).toLowerCase());
                        } else {
                            sb.append(nm.toLowerCase());
                        }
                        sb.append(" ");
                    }
                    nome = sb.toString().trim();

                    artista.setNome(nome);
                }
                if (artista.getAtivo() == null) artista.setAtivo(true);

                try {
                    Artista created = artistaRepository.save(artista);
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
    public ResponseEntity<?> putArtistaPorId (@PathVariable Long id, @RequestBody Artista artista){
        Artista original = artistaRepository.findOneById(id);
        if (original == null){
            Erro erro = new Erro(HttpStatus.NOT_FOUND,"Info-Artista não encontrada com ID: " + id );
            return new ResponseEntity<>(erro, erro.getHttpStatus());
        } else {
            artista.setId(original.getId());
            artista.setAdiTag(original.getAdiTag());

            AdiTag adiTag = adiTagRepository.findOneById(artista.getAdiTag());
            if (adiTag == null){
                Erro erro = new Erro(HttpStatus.NOT_FOUND,"AdiTag não encontrada com ID: " + artista.getAdiTag() );
                return new ResponseEntity<>(erro, erro.getHttpStatus());
            } else {
                artista.setTipo(adiTag.getTipo());
                if (artista.getTag() == null) {
                    artista.setTag(adiTag.getTag());
                }
                try {
                    artistaRepository.save(artista);
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } catch (Exception e){
                    Erro erro = new Erro(500,e.getMessage());
                    return new ResponseEntity<>(erro, erro.getHttpStatus());
                }
            }


        }
    }
}
