package com.nineone.nocm.service;

import com.nineone.nocm.domain.Invite;
import com.nineone.nocm.domain.User;
import com.nineone.nocm.repository.InviteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InviteServiceImpl implements InviteService {

    @Autowired
    private InviteRepository inviteRepository;

    @Override
    public boolean saveInvite(Invite invite) {
        return (inviteRepository.saveInvite(invite)>0);
    }

    @Override
    public List<Invite> getInviteList(String recipient) {

        return inviteRepository.receiveInvites(recipient);
    }

    @Override
    public boolean updateInvite(Invite invite) {
        return (inviteRepository.updateInvite(invite) > 0);
    }
}
