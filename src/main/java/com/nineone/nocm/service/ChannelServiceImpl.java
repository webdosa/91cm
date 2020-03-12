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
    public boolean deleteChannel(int id) {
        return (channelRepository.deleteChannel(id) > 0) ? true : false;
    }

    @Override
    public List<Channel> channelList(String userEmail) {
        return channelRepository.channelList(userEmail);
    }

    @Override
    public boolean updateChannel(Channel channel) {
        return (channelRepository.updateChannel(channel) > 0) ? true : false;
    }
}
