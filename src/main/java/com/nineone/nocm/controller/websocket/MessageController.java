package com.nineone.nocm.controller.websocket;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import com.nineone.nocm.domain.Message;
import com.nineone.nocm.domain.User;

@Controller
public class MessageController {
	
	@Autowired
	SimpMessageSendingOperations messagingTemplate;
	
//	@Autowired
//	WebsocketEventListener sessionListener;
	
	@MessageMapping("/chat/message")
	public void message(Message message) {
		// 메세지 전송하기 전에 DB에 메세지 저장하는 로직이 있어야함. and 유저정보 가져오는 로직..?
		Map<String,Object> msgmap = new HashMap<>();
		// String id = 메세지저장하는 함수, 반환값은 메세지 저장하면서 생성된 메세지 id값
		// message.setId(id);
		msgmap.put("message", message);
		// 송신자 유저 정보 가져오는 로직 (pk,이름,picture... 뭐 이런 필요한 데이터들..)
		// 여기서는 임시로 user객체 만들겠음 
		User user = new User();
		user.setIcon("asdasd");
		user.setName("asdasdww");
		msgmap.put("user", user);
		messagingTemplate.convertAndSend("/sub/chat/room/"+message.getChannel_id(), msgmap );
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
