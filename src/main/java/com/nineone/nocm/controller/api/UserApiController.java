package com.nineone.nocm.controller.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class UserApiController {

    @RequestMapping("/api")
    public Map<String, String> test(){
        log.info("api");
        Map<String, String> map = new HashMap<>();
        map.put("test","test");
        return map;
    }
}
