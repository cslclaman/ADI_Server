package com.csl.adi.controller;

import com.csl.adi.model.Teste;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class TestController {

    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/teste")
    public Teste getText (@RequestParam(name = "texto", defaultValue = "...") String str){
        Teste t = new Teste(counter.incrementAndGet());
        t.setStr(str);

        return t;
    }

}
