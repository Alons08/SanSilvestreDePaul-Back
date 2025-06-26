package com.utp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class DemoController {

    @GetMapping("/todos")
    @PreAuthorize("hasAnyAuthority('Administrador', 'Secretaria', 'Apoderado')")
    public String welcome() {
        return "Welcome from secure endpoint";
    }

    @GetMapping("/solo")
    @PreAuthorize("hasAuthority('Apoderado')")
    public String apoderadoOnly() {
        return "Welcome apoderado";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('Administrador')")
    public String adminOnly() {
        return "Welcome admin";
    }
}