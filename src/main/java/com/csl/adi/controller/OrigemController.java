package com.csl.adi.controller;

import com.csl.adi.model.Origem;
import com.csl.adi.repository.OrigemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/origem")
public class OrigemController {

    @Autowired
    private OrigemRepository origemRepository;

    @GetMapping (path = "")
    public @ResponseBody List<Origem> getListaOrigens() {
        return origemRepository.findAll();
    }

    @GetMapping (path = "/sigla/{sigla}")
    public @ResponseBody Origem getOrigemPorSigla (@PathVariable String sigla) {
        if (sigla == null){
            return null;
        } else {
            return origemRepository.findBySigla(sigla);
        }
    }

    @GetMapping (path = "/id/{id}")
    public @ResponseBody Origem getOrigemPorId(@PathVariable Integer id) {
        if (id == null){
            return null;
        } else {
            return origemRepository.findOneById(id);
        }
    }

    @PostMapping (path = "")
    public @ResponseBody Origem postOrigem (@RequestBody Origem origem) {
        return origemRepository.save(origem);
    }

    @PutMapping (path = "/id/{id}")
    public @ResponseBody Origem updateOrigemById (@PathVariable Integer id, @RequestBody Origem origem){
        Origem original = origemRepository.findOneById(id);
        if (original == null){
            return null;
        } else {
            origem.setId(original.getId());
            origem.setSigla(original.getSigla());
            return origemRepository.save(origem);
        }
    }

    @PutMapping (path = "/sigla/{sigla}")
    public @ResponseBody Origem updateOrigemBySigla (@PathVariable String sigla, @RequestBody Origem origem){
        Origem original = origemRepository.findBySigla(sigla);
        if (original == null){
            return null;
        } else {
            origem.setId(original.getId());
            origem.setSigla(original.getSigla());
            return origemRepository.save(origem);
        }
    }

    @DeleteMapping (path = "/id/{id}")
    public @ResponseBody String deleteOrigemById(@PathVariable Integer id) {
        Origem origem = origemRepository.findOneById(id);
        if (origem == null){
            return "Erro";
        } else {
            origemRepository.delete(origem);
            return "Deletado";
        }
    }

}
