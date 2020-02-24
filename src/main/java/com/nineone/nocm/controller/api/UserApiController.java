package com.nineone.nocm.controller.api;

import com.nineone.nocm.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class UserApiController {

    @RequestMapping("/api")
    public User test(){
        return User.builder()
                .nickName("test")
                .email("test")
                .build();
    }
}
