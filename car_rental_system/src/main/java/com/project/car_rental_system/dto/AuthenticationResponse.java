package com.project.car_rental_system.dto;

import com.project.car_rental_system.enums.UserRole;

import lombok.Data;

@Data
public class AuthenticationResponse {
	private String jwt;
	private UserRole userRole;
	private Long userId;
}
