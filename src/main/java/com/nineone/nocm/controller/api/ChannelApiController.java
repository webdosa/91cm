package com.nineone.nocm.controller.api;

import com.nineone.nocm.domain.Channel;
import com.nineone.nocm.service.ChannelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/channel")
public class ChannelApiController {

    @Autowired
    private ChannelService channelService;

    @PostMapping("/create")
    public boolean createChannel(@RequestBody Map<String,String> test){
        log.info(test.get("name"));
        return true;
    }
}
