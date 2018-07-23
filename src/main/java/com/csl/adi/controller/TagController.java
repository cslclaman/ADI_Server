package com.csl.adi.controller;

import com.csl.adi.model.Erro;
import com.csl.adi.model.Tag;
import com.csl.adi.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("api/v1/tag")
public class TagController {

    @Autowired
    private TagRepository tagRepository;

    @GetMapping ("")
    public ResponseEntity<?> getTagLista (
            @PageableDefault Pageable pageable,
            @RequestParam (name = "tag", required = false) String tag
    ){
        if (tag == null) tag = "";

        Page<Tag> page = tagRepository.findByTagIgnoreCaseContaining(pageable, tag);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping ("aditag/{adiTag}")
    public ResponseEntity<?> getTagListaPorAdiTag (
            @PageableDefault Pageable pageable,
            @PathVariable Long adiTag
    ){
        List<Tag> list = tagRepository.findByAdiTag(adiTag);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping ("id/{id}")
    public ResponseEntity<?> getTagPorId (@PathVariable Long id){
        Tag tag = tagRepository.findOneById(id);
        if (tag == null){
            Erro erro = new Erro(HttpStatus.NOT_FOUND,"Tag não encontrada com ID: " + id );
            return new ResponseEntity<>(erro, erro.getHttpStatus());
        } else {
            return new ResponseEntity<>(tag, HttpStatus.OK);
        }
    }

    @PostMapping ("")
    public ResponseEntity<?> postTag (@RequestBody Tag tag){
        try {
            Tag created = tagRepository.save(tag);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (Exception e){
            Erro erro = new Erro( HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            return new ResponseEntity<>(erro, erro.getHttpStatus());
        }
    }

    @PutMapping ("id/{id}")
    public ResponseEntity<?> putAdiTagPorId (@PathVariable Long id, @RequestBody Tag tag){
        Tag original = tagRepository.findOneById(id);
        if (original == null){
            Erro erro = new Erro(404, "Tag não encontrada com ID: " + id);
            return new ResponseEntity<>(erro, erro.getHttpStatus());
        } else {
            tag.setId(original.getId());
            tag.setTag(original.getTag());
            try {
                tagRepository.save(tag);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } catch (Exception e){
                Erro erro = new Erro(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
                return new ResponseEntity<>(erro, erro.getHttpStatus());
            }
        }
    }

}
