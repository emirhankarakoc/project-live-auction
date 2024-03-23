package com.karakoc.mezat.config;


import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    @Operation(summary = "Çalışıyor mu?", description = "Projenin doğru çalışıp çalışmadığını anlamak için böyle bir metod var.")
    public String hello(){return "<div><h2>Projen dogru calisiyor.</h2></div>";}
}
