package com.nineone.nocm.service;

import com.nineone.nocm.domain.Invite;
import com.nineone.nocm.domain.User;

import java.util.List;

public interface InviteService {
    boolean saveInvite(Invite invite);
    List<Invite> getInviteList(String recipient);
    boolean updateInvite(Invite invite);
    boolean isExistInvite(Invite invite);
}
