package com.nineone.nocm.controller.api;

import com.nineone.nocm.domain.Channel;
import com.nineone.nocm.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/channel")
public class ChannelApiController {

    @Autowired
    private ChannelService channelService;

    @RequestMapping("/create")
    public void createChannel(@RequestBody Channel channel){

    }
}
