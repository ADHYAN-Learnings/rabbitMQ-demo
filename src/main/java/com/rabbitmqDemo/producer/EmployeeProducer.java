package com.rabbitmqDemo.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.rabbitmqDemo.config.RabbitMQConfig;
import com.rabbitmqDemo.dto.EmployeeCreatedEvent;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

@Service
public class EmployeeProducer {
	
	private final RabbitTemplate rabbitTemplate;
	private final ObjectMapper objectMapper;
	
	public EmployeeProducer(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
		this.rabbitTemplate = rabbitTemplate;
		this.objectMapper = objectMapper;
	}
	
	public void publishEmployeeCreated(EmployeeCreatedEvent event) {
		try {
			String message = objectMapper.writeValueAsString(event);
			rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE,RabbitMQConfig.ROUTING_KEY,message);
			System.out.println("Message Published: "+message);
		} catch (JacksonException exception) {
			throw new IllegalStateException("Unable to convert employee event to Json",exception);
		}
	}
	
	

}
