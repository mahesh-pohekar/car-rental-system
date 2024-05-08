package com.project.car_rental_system.dto;
import com.project.car_rental_system.enums.UserRole;
import lombok.Data;
@Data
public class UserDto {
	private long id;
	private String name;
	private String email;
	private UserRole userRole;
}
