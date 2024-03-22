package br.com.fiap.challengeecommercegestaoitens.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {

    @GetMapping("/ping")
    public String test() {
        try {
            return "Alive!";
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
