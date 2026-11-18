package com.rabbitmqDemo.controller;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rabbitmqDemo.dto.EmployeeCreatedEvent;
import com.rabbitmqDemo.dto.EmployeeRequest;
import com.rabbitmqDemo.producer.EmployeeProducer;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
	
	private final EmployeeProducer employeeProducer;
	
	private final AtomicLong idGenerator = new AtomicLong(1000);

	public EmployeeController(EmployeeProducer employeeProducer) {
		this.employeeProducer = employeeProducer;
	}
	
	@PostMapping
	public ResponseEntity<Map<String, Object>> createEmployee(@RequestBody EmployeeRequest request) {
		Long employeeId = idGenerator.incrementAndGet();

		EmployeeCreatedEvent event = new EmployeeCreatedEvent(employeeId, request.name(), request.email(),
				request.department(), "EMPLOYEE_CREATED");
		employeeProducer.publishEmployeeCreated(event);

		return ResponseEntity.status(HttpStatus.ACCEPTED)
				.body(Map.of("message", "Employee event published", "employeeId", employeeId));
	}
	
	

}
