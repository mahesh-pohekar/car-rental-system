package com.project.car_rental_system.services.customer;

import java.util.List;

import com.project.car_rental_system.dto.BookACarDto;
import com.project.car_rental_system.dto.CarDto;
import com.project.car_rental_system.dto.CarDtoListDto;
import com.project.car_rental_system.dto.PaymentDto;
import com.project.car_rental_system.dto.SearchCarDto;

public interface CustomerService {
	
	List<CarDto> getAllCars();
	
	boolean bookACar(BookACarDto bookACarDto);
	
	CarDto getCarById(Long carId);
	
	List<BookACarDto> getBookingsByUserId(Long userId);
	
	CarDtoListDto searchCar(SearchCarDto searchCarDto);

    boolean makePayment(Long bookingId, PaymentDto paymentDto);

}
