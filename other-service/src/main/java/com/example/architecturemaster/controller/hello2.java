package com.example.architecturemaster.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/helloWorld")

public class hello2 {
    @GetMapping("/hello")
    public String sayHello(){
        return "hello";
    }

}
