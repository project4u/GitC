package com.example.game;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class MainGameController {
    @GetMapping("/")
    public String sayHello(){
        return "Hello World";
    }
}
