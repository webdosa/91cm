package com.nineone.nocm.controller.api;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nineone.nocm.domain.Message;
import com.nineone.nocm.service.MessageService;

@Slf4j
@RestController
@RequestMapping("/api/message")
public class MessageApiController {
	
	@Autowired
	MessageService messageServie;
	
	@RequestMapping(value ="/getmsg",method=RequestMethod.POST)
	public List<Message> getMsg(@RequestBody Map<String,Object> map) throws ParseException{
		return messageServie.getMessageList(map);
	}

}
