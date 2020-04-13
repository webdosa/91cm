package com.nineone.nocm.repository;

import com.nineone.nocm.domain.Invite;

import java.util.List;

public interface InviteRepository{
    int saveInvite(Invite invite);
    List<Invite> receiveInvites(String recipient);
    int updateInvite(Invite invite);
}
