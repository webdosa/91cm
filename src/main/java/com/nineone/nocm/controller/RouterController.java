package com.nineone.nocm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RouterController {

    @RequestMapping("/")
    public String index(){
        return "index.html";
    }


    @RequestMapping(value = "/{path:[^\\.]*}")
    public String redirect(){
        return "forward:/";
    }
}
