package com.nineone.nocm.service;

import com.nineone.nocm.domain.Channel;

import java.util.List;

public interface ChannelService {

    boolean createChannel(Channel channel);

    boolean deleteChannel(int id);

    List<Channel> channelList(String userId);

    boolean updateChannel(Channel name);
}
