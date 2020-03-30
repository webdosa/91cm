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
    
	@Autowired
	private MessageService messageService; 

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
    	
    	if(map.get("oldChannelId")!=null) {
    		System.out.println(map.get("oldChannelId"));
    		Date last_access_date = messageService.makeDate();
        	JoinInfo joinInfo = JoinInfo.builder()
        			.channel_id((int)map.get("oldChannelId"))
        			.member_email(user.getEmail())
        			.last_access_date(last_access_date)
        			.build();
        	joinInfoService.updateLastAccessDate(joinInfo);
    	}
    }
    
    @RequestMapping(value ="/update/sessioniscw", method=RequestMethod.PUT)
    public void updateSessionIsCW(@RequestBody Map<String,Object> map, HttpSession session) {
    	// LastAccess 말고 map으로 받은 이유는 boolean값을 이상하게 가져와서 임시방편으로 썼다.
    	LastAccess originLastAccess = (LastAccess)session.getAttribute("lastAccess");
    	System.out.println(originLastAccess.isContentWrapper());
    	originLastAccess.setContentWrapper((boolean)map.get("isContentWrapper"));
    	System.out.println(originLastAccess.isContentWrapper());
    	System.out.println(originLastAccess.getCurrentChannelId());
    	session.setAttribute("lastAccess", originLastAccess);
    }
    
//    @RequestMapping(value ="/insert/sessionLA", method=RequestMethod.POST)
//    public void insertSessionLA(@RequestBody LastAccess lastAccess,HttpSession session) {
//    	session.setAttribute("lastAccess", lastAccess);
//    }
}
