package br.com.fiap.challengeecommercecarrinhodecompras.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {

    private static final String ALIVE_RESPONSE = "Alive!";

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/ping")
    public String pingResponse() {
        return ALIVE_RESPONSE;
    }
}
