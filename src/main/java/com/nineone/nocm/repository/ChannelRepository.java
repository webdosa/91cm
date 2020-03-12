package com.nineone.nocm.repository;

import com.nineone.nocm.domain.Channel;

import java.util.List;

public interface ChannelRepository {
    int insertChannel(Channel channel);
    int deleteChannel(int channelId);
    int updateChannel(Channel channel);
    List<Channel> channelList (String userId);
}
