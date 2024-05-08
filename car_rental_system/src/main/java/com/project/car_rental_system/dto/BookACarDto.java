package com.project.car_rental_system.dto;

import java.util.Date;

import com.project.car_rental_system.enums.BookCarStatus;

import lombok.Data;

@Data
public class BookACarDto {
	private Long id;
	private Date fromDate;
	private Date toDate;
	private Long days;
	private Long price;
	private BookCarStatus bookCarStatus;
	private Long carId;
	private Long userId;
	private String username;
	private String email;
	private String paymentStatus;
}
