package com.project.car_rental_system.services.auth;

import com.project.car_rental_system.dto.SignupRequest;
import com.project.car_rental_system.dto.UserDto;

public interface AuthService {
	
	UserDto createCustomer(SignupRequest signupRequest);
	boolean hasCustomerWithEmail(String email);
}
