package com.enterpriseai.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Welcome to Enterprise AI Copilot Backend";
    }

    @GetMapping("/api/hello")
    public String hello() {
        return "Hello Laxman! Enterprise AI Copilot Backend is Running Successfully.";
    }
    @GetMapping("/api/protected")
public String protectedEndpoint() {
    return "JWT Authentication Successful! You can access protected resources.";
}
}
