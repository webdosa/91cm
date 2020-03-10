package com.nineone.nocm.controller.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import com.nineone.nocm.domain.Message;

@Controller
public class MessageController {
	
	@Autowired
	SimpMessageSendingOperations messagingTemplate;
	
//	@Autowired
//	WebsocketEventListener sessionListener;
	
	@MessageMapping("/chat/message")
	public void message(Message message) {
		// 메세지 전송하기 전에 DB에 메세지 저장하는 로직이 있어야함.
		
		messagingTemplate.convertAndSend("/sub/chat/room/"+message.getChannel_id(), message );
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
