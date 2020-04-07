package com.nineone.nocm.controller.websocket;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.nineone.nocm.domain.Message;
import com.nineone.nocm.repository.UserRepository;
import com.nineone.nocm.service.MessageService;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
public class MessageController {

	@Autowired
	private SimpMessageSendingOperations messagingTemplate;

	
	@Autowired
	private MessageService messageService; 

	//	@Autowired
//	WebsocketEventListener sessionListener;

	@MessageMapping("/chat/message")
	@Transactional
	public void message(Message message)throws ParseException {
		message.setSend_date(messageService.makeDate());
		message.setStr_send_date(messageService.makeStrDate(message.getSend_date()));
		message.setContent(messageService.replacemsg(message.getContent()));
		if(messageService.insertMessage(message) > 0) {
			messagingTemplate.convertAndSend("/sub/chat/room/"+message.getChannel_id(), message);
		}else {
			messagingTemplate.convertAndSend("/sub/"+message.getSender(), message);
		}
		 
	}
	@MessageMapping("/sync/info")
	public void storeUpdateMessage(){
		messagingTemplate.convertAndSend("/sub/sync/info","true");
	}
	

//	@MessageMapping("/notification")
//	public void notification(Notification notification) {
//
//		// 메세지 전송하기 전에 DB에 notification을 저장하는 로직이 있어야함
//		/*
//		String sessionId = sessionListener.getSessions().get(notification.getRecipient()).toString();
//		if(sessionId != null) {
//			messagingTemplate.convertAndSendToUser(sessionId, "/queue/noti", notification);
//		}
//		*/
//
//		if(sessionListener.getSessions().get(notification.getRecipient())!=null) {
//			messagingTemplate.convertAndSendToUser(notification.getRecipient(), "/queue/noti", notification);
//		}

//	}

}