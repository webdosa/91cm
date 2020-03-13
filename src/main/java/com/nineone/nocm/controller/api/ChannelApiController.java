package com.nineone.nocm.controller.api;

import com.nineone.nocm.annotation.Socialuser;
import com.nineone.nocm.domain.Channel;
import com.nineone.nocm.domain.JoinInfo;
import com.nineone.nocm.domain.User;
import com.nineone.nocm.service.ChannelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/channel")
public class ChannelApiController {

    @Autowired
    private ChannelService channelService;

    // 사용자의 채널 리스트를 반환
    @GetMapping("/list")
    public List<Channel> channelList(@Socialuser User user){
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
    public boolean updateChannel(@RequestBody  Map<String,String> channelInfo, @Socialuser User user){
        if (channelInfo.get("member_id").equals(user.getEmail())){

            return channelService.updateChannel(Channel.builder()
                    .id(Integer.valueOf(channelInfo.get("id")))
                    .name(channelInfo.get("name"))
                    .build());
        }
        return false;
    }

    @PostMapping("/delete")
    public boolean deleteChannel(@RequestBody Map<String, String> channelInfo, @Socialuser User user){
        // 유저 정보가 맞는지 비교 하는 코드
        if (channelInfo.get("member_id").equals(user.getEmail())){
            return channelService.deleteChannel(Integer.valueOf(channelInfo.get("id")));
        }
        // 삭제 실패 이유 메시지를 넣으면 좋을듯
        return false;
    }
}
