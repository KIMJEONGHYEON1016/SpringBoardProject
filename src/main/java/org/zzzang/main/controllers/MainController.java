package org.zzzang.main.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zzzang.global.exceptions.ExceptionProcessor;

@Controller
@RequestMapping("/")
public class MainController implements ExceptionProcessor {

    @GetMapping
    public String index() {

        return "front/main/index";
    }
}
