package com.rabbitmqDemo.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.rabbitmqDemo.dto.EmployeeCreatedEvent;

import tools.jackson.databind.ObjectMapper;

@Component
public class EmployeeConsumer {
	
	private final ObjectMapper objectMapper;

	public EmployeeConsumer(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}
	
	@RabbitListener(queues = "employee.queue")
	public void receiveMessage(String message) {
		
		try {
			EmployeeCreatedEvent event = objectMapper.readValue(message, EmployeeCreatedEvent.class);
			System.out.println("Received Employee event!");
			System.out.println("Employee ID: "+event.employeeId());
			System.out.println("Name: "+event.name());
			System.out.println("Email: "+event.email());
			System.out.println("Department: "+event.department());
			
			System.out.println("Welcome email processed for: "+event.email());
		} catch(Exception exception) {
			throw new IllegalArgumentException("Unable to process employee message",exception);
		}
		
	}
	

}
