package com.rabbitmqDemo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
	
	public static final String EXCHANGE = "employee.exchange";
	public static final String QUEUE = "employee.queue";
	public static final String ROUTING_KEY = "employee.created";
	
	@Bean
	DirectExchange employeeExchange() {
		return new DirectExchange(EXCHANGE);
	}
	
	@Bean
	Queue employeeQueue() {
		return new Queue(QUEUE,true);
	}
	
	@Bean
	Binding employeeBinding(Queue employeeQueue,DirectExchange employeeExchange) {
		return BindingBuilder.bind(employeeQueue).to(employeeExchange).with(ROUTING_KEY);
		
	}
	

}
