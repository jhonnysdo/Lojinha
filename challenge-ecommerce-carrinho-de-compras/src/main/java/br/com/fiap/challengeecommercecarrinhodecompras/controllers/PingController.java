package br.com.fiap.challengeecommercecarrinhodecompras.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/ping")
    public String test() {
        try {
            return "Alive!";
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
