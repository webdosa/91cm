package com.nineone.nocm.repository;

import com.nineone.nocm.domain.Invite;

import java.util.List;
import java.util.Map;

public interface InviteRepository{
    int saveInvite(Invite invite);
    List<Invite> receiveInvites(String recipient);
    int updateInvite(Invite invite);
    boolean isExistInvite(Invite invite);
    boolean deleteInvite(Map<String, Object> info);
}
