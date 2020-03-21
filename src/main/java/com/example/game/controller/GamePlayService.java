package com.example.game.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class GamePlayService {
    public String index(){
        return "index.html";
    }
}
