package com.nerdlab.microservice.adapter.in.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PortfolioController {

    @GetMapping("/")
    public String portfolio() {
        return "forward:/index.html";
    }
}
