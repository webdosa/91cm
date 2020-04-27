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
    public void updateLastAccessDate(@RequestBody Map<String,Object> map, @Socialuser User user,HttpSession session) {
    	// 채널에서 채널로 이동했을때 실행
    	LastAccess lastAccess = (LastAccess)session.getAttribute("lastAccess");
    	lastAccess.setCurrentChannelId((int)map.get("currentChannelId"));
    	session.setAttribute("lastAccess", lastAccess);
    	if(map.get("oldChannelId")==null) {
    		joinInfoService.updateLastAccessDate((int)map.get("currentChannelId"),user.getEmail());
    	}else {
    		joinInfoService.updateLastAccessDate((int)map.get("oldChannelId"),user.getEmail());
    	}
    }
    
    // 채팅화면에서 채팅화면이 아닌 곳으로 이동했을 때 session값 갱신해주기 위함
    // 세션값을 갱신해주는 이유는 세션타임아웃시 마지막접속시간을 조건부 아래 갱신시켜주기 위함
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

    //현재 채널 초기화
    @RequestMapping(value ="/update/sessioncc", method=RequestMethod.POST)
    public void initCurrentChannel(@RequestBody Map<String,Object> map, HttpSession session) {
    	LastAccess originLastAccess = (LastAccess)session.getAttribute("lastAccess");
    	originLastAccess.setContentWrapper(true);
    	originLastAccess.setCurrentChannelId((int)map.get("currentChannelId"));
    	session.setAttribute("lastAccess", originLastAccess);
    }
    // 포커스 중인지 아닌지 갱신
    // 포커스아닐 때 타임아웃시 마지막접속시간을 갱신해주면 안되니까..
    @RequestMapping(value= "/update/sessionfocus", method=RequestMethod.POST)
    public void updateSessionIsFocus(@RequestBody Map<String,Object> map, HttpSession session,@Socialuser User user) {
    	if(session.getAttribute("user")!=null) {
    		LastAccess originLastAccess = (LastAccess)session.getAttribute("lastAccess");
        	originLastAccess.setFocus((boolean)map.get("isFocus"));
        	session.setAttribute("lastAccess", originLastAccess);
        	if(!originLastAccess.isFocus() && originLastAccess.getCurrentChannelId() != 0) {
        		joinInfoService.updateLastAccessDate(originLastAccess.getCurrentChannelId(),user.getEmail());
        	}
    	}
    }
     

}
