package com.project.car_rental_system.services.jwt;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
	UserDetailsService userDetailsService();
}
