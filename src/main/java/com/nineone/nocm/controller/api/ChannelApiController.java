package com.nineone.nocm.controller.api;

import com.nineone.nocm.domain.Channel;
import com.nineone.nocm.service.ChannelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/channel")
public class ChannelApiController {

    @Autowired
    private ChannelService channelService;

    @RequestMapping(method = RequestMethod.GET, path = "/create")
    public boolean createChannel(@RequestParam(value = "name") String channelName){
        log.info(channelName);
        return true;
    }
}
