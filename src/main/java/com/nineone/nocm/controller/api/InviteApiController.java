package com.nineone.nocm.controller.api;

import com.nineone.nocm.domain.Invite;
import com.nineone.nocm.domain.JoinInfo;
import com.nineone.nocm.service.ChannelService;
import com.nineone.nocm.service.InviteService;
import com.nineone.nocm.service.JoinInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/invite")
public class InviteApiController {

    @Autowired
    private InviteService inviteService;
    @Autowired
    private JoinInfoService joinInfoService;

    @Autowired
    private ChannelService channelService;
    // map에 필요한 정보 생성자 정보 (권한 확인용)
    // 채널 정보, 초대자 정보(유저 id)
    @PostMapping
    public boolean inviteUser(@RequestBody Invite invite){
        inviteService.saveInvite(invite);
        JoinInfo joinInfo = JoinInfo.builder()
                .channel_id(invite.getChannel_id())
                .member_email(invite.getSender())
                .build();
        if (joinInfoService.AuthorityCheck(joinInfo)){
            log.info("권한 있음");
            joinInfoService.insertJoinInfo(JoinInfo.builder()
                    .channel_id(invite.getChannel_id())
                    .member_email(invite.getRecipient())
                    .build());
            return true;
        }else{
            log.info("권한 없음");
        }
        // 슈도코드
//        String channelId = map.get("channelId");
//        String 생성자id = map.get("생성자id");
//        String 초대자id = map.get("초대자id");
//        if (생성자가 채널 권한이 있는지 확인){
//            channelService.해당채널에유저추가(channelId,초대자id);
//            userService.유저에채널권한부여(channelId,생성자id);
//        }
        return false;
    }
}
