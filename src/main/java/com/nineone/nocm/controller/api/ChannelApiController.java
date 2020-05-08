package com.nineone.nocm.controller.api;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nineone.nocm.annotation.Socialuser;
import com.nineone.nocm.domain.Channel;
import com.nineone.nocm.domain.JoinInfo;
import com.nineone.nocm.domain.LastAccess;
import com.nineone.nocm.domain.User;
import com.nineone.nocm.service.ChannelService;
import com.nineone.nocm.service.JoinInfoService;
import com.nineone.nocm.service.MessageService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/channel")
public class ChannelApiController {

    @Autowired
    private ChannelService channelService;

    @Autowired
    private JoinInfoService joinInfoService;
    

    @PostMapping("/current")
    public Channel getCurrentChannel(@RequestBody Channel channel){
        return channelService.getCurrentChannel(channel);
    }

    @PostMapping("/leave")
    public boolean leaveChannel(@RequestBody Map<String, Object> info){
        // info에는 해당 유저의 email 값과 채널 id값이 들어 있음 -> key : (email , channel_id)
        return joinInfoService.leaveUser(info);
    }

    @GetMapping("/all")
    public List<Channel> channelListAll(){
        return channelService.channelListAll();
    }

    // 사용자의 채널 리스트를 반환
    @GetMapping("/list")
    public List<Channel> channelList(@Socialuser User user) {
        return channelService.channelList(user.getEmail());
    }

    @PostMapping("/create")
    public Channel createChannel(@RequestBody Map<String, String> channelInfo) {
        Channel channel = Channel.builder()
                .name(channelInfo.get("name"))
                .member_email(channelInfo.get("member_email"))
                .build();
        channelService.createChannel(channel);
        return channel;
    }

    @PostMapping("/update")
    public boolean updateChannel(@RequestBody Channel channel, @Socialuser User user) {
         return channelService.updateChannel(channel);
    }

    @PostMapping("/delete")
    public boolean deleteChannel(@RequestBody Channel channel) {
        return channelService.deleteChannel(channel.getId());
    }
    
    
    @RequestMapping(value ="/update/lastaccessdate", method=RequestMethod.PUT)
    public void updateLastAccessDate(@RequestBody Map<String,Object> map) {
    	log.info("이동");
    	if(map.get("oldChannelId")==null) {
    		log.info("current");
    		joinInfoService.updateLastAccessDate((int)map.get("currentChannelId"),map.get("userEmail").toString());
    	}else {
    		log.info("old");
    		joinInfoService.updateLastAccessDate((int)map.get("oldChannelId"),map.get("userEmail").toString());
    	}
    }

}
