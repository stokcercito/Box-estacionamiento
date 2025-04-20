package com.agustin.SistemaEstacionamiento.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, this is a test endpoint!";
    }

    @GetMapping("/status")
    public String checkStatus() {
        return "Everything is running smoothly!";
    }
}
