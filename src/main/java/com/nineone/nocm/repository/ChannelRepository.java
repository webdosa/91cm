package com.nineone.nocm.repository;

import java.util.List;

import com.nineone.nocm.domain.Channel;
import com.nineone.nocm.domain.JoinInfo;

public interface ChannelRepository {
    int insertChannel(Channel channel);
    int deleteChannel(int channelId);
    int updateChannel(Channel channel);
    List<Channel> channelList (String userId);
    List<Channel> channelListAll ();
    int joinInfo(JoinInfo joinInfo);
    Channel getCurrentChannel(Channel channel);

}
