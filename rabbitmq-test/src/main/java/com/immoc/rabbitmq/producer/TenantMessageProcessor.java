package com.immoc.rabbitmq.producer;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TenantMessageProcessor implements MessagePostProcessor {
	@Override
	public Message postProcessMessage(Message message) throws AmqpException {
		message.getMessageProperties().getHeaders().put("tenantId", "11111111");
		return message;
	}
 
}
