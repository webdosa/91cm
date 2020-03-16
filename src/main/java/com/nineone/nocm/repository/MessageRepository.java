package com.nineone.nocm.repository;

import com.nineone.nocm.domain.Message;

public interface MessageRepository {
	int insertMessage(Message msg);
}
