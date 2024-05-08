package com.project.car_rental_system.services.admin;

import java.io.IOException;
import java.util.List;

import com.project.car_rental_system.dto.BookACarDto;
import com.project.car_rental_system.dto.CarDto;
import com.project.car_rental_system.dto.CarDtoListDto;
import com.project.car_rental_system.dto.SearchCarDto;

public interface AdminService {

		boolean postCar(CarDto carDto) throws IOException;
		
		List<CarDto> getAllCars();
		
		void deleteCar(Long id);
		
		CarDto getCarById(Long id);
		
		boolean updateCar(Long carId, CarDto carDto) throws IOException;
		
		List<BookACarDto> getBookings();
		
		boolean changeBookingStatus(Long bookingId,String status);
		
		CarDtoListDto searchCar(SearchCarDto searchCarDto);
}
