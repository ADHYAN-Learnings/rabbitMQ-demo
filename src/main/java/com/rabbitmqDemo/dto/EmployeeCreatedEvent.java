package com.rabbitmqDemo.dto;

public record EmployeeCreatedEvent(Long employeeId,String name,String email,String department,String eventType) {

}
