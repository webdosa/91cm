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
    

    @PostMapping("/leave")
    public boolean leaveChannel(@RequestBody Map<String, Object> info){
        // info에는 해당 유저의 email 값과 채널 id값이 들어 있음 -> key : (email , channel_id)
        return joinInfoService.leaveUser(info);
    }

    // 사용자의 채널 리스트를 반환
    @GetMapping("/list")
    public List<Channel> channelList(@Socialuser User user) {
        log.info(user.getName());
        return channelService.channelList(user.getEmail());
    }

    @PostMapping("/create")
    public boolean createChannel(@RequestBody Map<String, String> channelInfo) {
        Channel channel = Channel.builder()
                .name(channelInfo.get("name"))
                .member_email(channelInfo.get("member_email"))
                .build();
        
        return channelService.createChannel(channel);
    }

    @PostMapping("/update")
    public boolean updateChannel(@RequestBody Channel channel, @Socialuser User user) {
        if (channel.getMember_email().equals(user.getEmail())){
            channelService.updateChannel(channel);
            return true;
        }
        return false;
    }

    @PostMapping("/delete")
    public boolean deleteChannel(@RequestBody Channel channel) {
        return channelService.deleteChannel(channel.getId());
    }
    
    @RequestMapping(value ="/update/lastaccessdate", method=RequestMethod.PUT)
    public void updateLastAccessDate(@RequestBody Map<String,Object> map, @Socialuser User user,HttpSession session) {
    	
    	LastAccess lastAccess = (LastAccess)session.getAttribute("lastAccess");
    	System.out.println("뭐지 : "+lastAccess.isContentWrapper());
    	lastAccess.setCurrentChannelId((int)map.get("currentChannelId"));
    	session.setAttribute("lastAccess", lastAccess);
    	// 채널이 아무것도 없을 때는 갱신해주지 않아도 되니까 null확인으로 채널있는지 없는지 확인해줌
    	if(map.get("oldChannelId")!=null) {
    		System.out.println(map.get("oldChannelId"));
        	joinInfoService.updateLastAccessDate((int)map.get("oldChannelId"),user.getEmail());
    	}
    }
    
    @RequestMapping(value ="/update/sessioniscw", method=RequestMethod.PUT)
    public void updateSessionIsCW(@RequestBody Map<String,Object> map,@Socialuser User user, HttpSession session) {
    	// LastAccess 말고 map으로 받은 이유는 boolean값을 이상하게 가져와서 임시방편으로 썼다.
    	if(session.getAttribute("lastAccess")!=null) {
    		LastAccess originLastAccess = (LastAccess)session.getAttribute("lastAccess");
        	boolean isContentWrapper = (boolean)map.get("isContentWrapper");
        	if(!isContentWrapper) {
        		if(originLastAccess.getCurrentChannelId()!=0) {
                	joinInfoService.updateLastAccessDate(originLastAccess.getCurrentChannelId(),user.getEmail());
        		} 
        	}
        	originLastAccess.setContentWrapper(isContentWrapper);
        	session.setAttribute("lastAccess", originLastAccess);
    	}
    }
    
    @RequestMapping(value ="/update/sessioncc", method=RequestMethod.POST)
    public void initCurrentChannel(@RequestBody Map<String,Object> map, HttpSession session) {
    	LastAccess originLastAccess = (LastAccess)session.getAttribute("lastAccess");
    	originLastAccess.setContentWrapper(true);
    	originLastAccess.setCurrentChannelId((int)map.get("currentChannelId"));
    	session.setAttribute("lastAccess", originLastAccess);
    }
    @RequestMapping(value= "/update/sessionfocus", method=RequestMethod.POST)
    public void updateSessionIsFocus(@RequestBody Map<String,Object> map, HttpSession session) {
    	LastAccess originLastAccess = (LastAccess)session.getAttribute("lastAccess");
    	originLastAccess.setFocus((boolean)map.get("isFocus"));
    	session.setAttribute("lastAccess", originLastAccess);
    }
     
//    @RequestMapping(value ="/insert/sessionLA", method=RequestMethod.POST)
//    public void insertSessionLA(@RequestBody LastAccess lastAccess,HttpSession session) {
//    	session.setAttribute("lastAccess", lastAccess);
//    }
}
