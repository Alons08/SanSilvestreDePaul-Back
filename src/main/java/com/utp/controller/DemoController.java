package com.utp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1") //Será una ruta protegida
@RequiredArgsConstructor
public class DemoController {

    @PostMapping(value = "demo")
    public String welcome(){
        return "Welcome form secure enpoint";
    }

}
