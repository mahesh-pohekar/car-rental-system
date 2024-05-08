package com.project.car_rental_system.dto;

import lombok.Data;

@Data
public class AuthenticationRequest {
	private String email;
	private String password;
}
