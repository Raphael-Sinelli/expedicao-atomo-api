package br.com.fiap.t2tdsps.expedicaoatomo.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

// Quem abre http://IP:8080 no navegador cai direto no Swagger.
@Hidden
@RestController
public class InicioController {

    @GetMapping("/")
    public ResponseEntity<Void> redirecionarParaSwagger() {
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create("/swagger-ui.html"))
                .build();
    }
}
