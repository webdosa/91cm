package com.nineone.nocm.service;

import com.nineone.nocm.domain.Channel;
import com.nineone.nocm.repository.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChannelServiceImpl implements ChannelService{

    @Autowired
    private ChannelRepository channelRepository;

    @Override
    public boolean createChannel(Channel channel) {
        return (channelRepository.insertChannel(channel) > 0) ? true : false;
    }

    @Override
    public boolean deleteChannel() {
        return false;
    }

    @Override
    public List<Channel> channelList(String userId) {
        return channelRepository.channelList(userId);
    }
}
