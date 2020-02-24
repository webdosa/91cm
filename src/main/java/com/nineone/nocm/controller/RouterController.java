package com.nineone.nocm.controller;

import com.nineone.nocm.annotation.Socialuser;
import com.nineone.nocm.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class RouterController {

    @RequestMapping(value = "/{path:[^\\.]*}")
    public String redirect(@Socialuser User user){
        log.info("url : forward:/");
        log.info("user : "+user.getEmail());
        log.info("user : "+user.getRole());
        return "forward:/";
    }
}
