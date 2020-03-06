package com.nineone.nocm.controller.api;

import com.nineone.nocm.domain.Channel;
import com.nineone.nocm.service.ChannelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("/api/channel")
public class ChannelApiController {

    @Autowired
    private ChannelService channelService;

    @RequestMapping("/create")

    public boolean createChannel(@RequestBody Channel channel){
        log.info(channel.getName());
        return channelService.createChannel(channel);
    }
}
