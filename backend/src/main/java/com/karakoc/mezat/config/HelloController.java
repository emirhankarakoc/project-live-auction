package com.karakoc.mezat.config;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String hello(){return "<div><h2>Projen dogru calisiyor.</h2></div>";}
}
