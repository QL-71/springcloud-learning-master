package com.example.architecturemaster.common.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class hello {
    @GetMapping("/say")
    public String say(){
        return "hello world";
    }
    @GetMapping("/sayHello/{name}")
    public String sayHello(@PathVariable String name){
        return name+" hello world";
    }
}
