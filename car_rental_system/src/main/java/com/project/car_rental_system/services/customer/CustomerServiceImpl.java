package com.project.car_rental_system.services.customer;

import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.project.car_rental_system.dto.BookACarDto;
import com.project.car_rental_system.dto.CarDto;
import com.project.car_rental_system.dto.CarDtoListDto;
import com.project.car_rental_system.dto.PaymentDto;
import com.project.car_rental_system.dto.SearchCarDto;
import com.project.car_rental_system.entity.BookACar;
import com.project.car_rental_system.entity.Car;
import com.project.car_rental_system.entity.Payment;
import com.project.car_rental_system.entity.User;
import com.project.car_rental_system.enums.BookCarStatus;
import com.project.car_rental_system.repository.BookACarRepository;
import com.project.car_rental_system.repository.CarRepository;
import com.project.car_rental_system.repository.PaymentRepository;
import com.project.car_rental_system.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService{
	
	private final CarRepository carRepository;
	
	private final UserRepository userRepository;
	
	private final BookACarRepository bookACarRepository;
	
	private final PaymentRepository paymentRepository;
	
	@Override
	public List<CarDto> getAllCars() {
		return carRepository.findAll().stream().map(Car::getCarDto).collect(Collectors.toList());
	}

	@Override
	public boolean bookACar(BookACarDto bookACarDto) {
		Optional<Car> optionalCar = carRepository.findById(bookACarDto.getCarId());
		Optional<User> optionalUser = userRepository.findById(bookACarDto.getUserId());
		if (optionalCar.isPresent() && optionalUser.isPresent()){
			Car existingCar = optionalCar.get();
			BookACar bookACar = new BookACar();
			bookACar.setUser (optionalUser.get());
			bookACar.setCar(existingCar);
			bookACar.setBookCarStatus (BookCarStatus. PENDING);
			System.out.println(bookACarDto);
			System.out.println("next: ");
			System.out.println(bookACarDto.getToDate());
			System.out.println("******************************");
			// Convert from java.util.Date to java.sql.Date for database storage
			java.sql.Date sqlFromDate = new java.sql.Date(bookACarDto.getFromDate().getTime());
			java.sql.Date sqlToDate = new java.sql.Date(bookACarDto.getToDate().getTime());

			bookACar.setFromDate(sqlFromDate);
			bookACar.setToDate(sqlToDate);
			System.out.println(bookACarDto.getToDate().getTime());
			long diffInMilliSeconds = bookACarDto.getToDate().getTime() - bookACarDto.getFromDate().getTime();
			System.out.println("milisec: "+diffInMilliSeconds);

			long days = TimeUnit.MILLISECONDS.toDays(diffInMilliSeconds);
			System.out.println("days:"+days);
			bookACar.setDays(days);
			bookACar.setPrice(existingCar.getPrice() * days);
			bookACarRepository.save(bookACar);
			return true;
		}
		return false;
	}


	@Override
	public CarDto getCarById(Long carId) {
		Optional<Car> optionalCar = carRepository.findById(carId);
		return optionalCar.map(Car::getCarDto).orElse(null);
	}

	@Override
	public List<BookACarDto> getBookingsByUserId(Long userId) {
	    List<BookACar> bookings = bookACarRepository.findAllByUserId(userId);
	    List<BookACarDto> bookingDtos = new ArrayList<>();

	    for (BookACar booking : bookings) {
	        // Fetch payment status for each booking
	        Payment payment = paymentRepository.findByBookingId(booking.getId());
	        String paymentStatus = payment != null ? payment.getStatus() : "PENDING"; // Assuming status is stored as a string in Payment entity

	        BookACarDto bookingDto = booking.getBookACarDto();
	        bookingDto.setPaymentStatus(paymentStatus);
	        bookingDtos.add(bookingDto);
	    }

	    return bookingDtos;	}

	@Override
	public CarDtoListDto searchCar(SearchCarDto searchCarDto) {
		Car car = new Car();
		car.setBrand(searchCarDto.getBrand());
		car.setType(searchCarDto.getType());
		car.setTransmission(searchCarDto.getTransmission());
		car.setColor(searchCarDto.getColor());
		ExampleMatcher exampleMatcher =
				ExampleMatcher.matchingAll()
						.withMatcher("brand", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
						.withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
						.withMatcher("transmission", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
						.withMatcher("color", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
		Example<Car> carExample = Example.of(car, exampleMatcher);
		List<Car> carList = carRepository.findAll(carExample);
		CarDtoListDto carDtoListDto = new CarDtoListDto();
		carDtoListDto.setCarDtoList(carList.stream() .map(Car::getCarDto).collect(Collectors.toList()));
		return carDtoListDto;
	}


	@Override
	public boolean makePayment(Long bookingId, PaymentDto paymentDto) {
		BookACar booking = bookACarRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        // Create a new Payment entity
        Payment payment = new Payment();
        payment.setCardNumber(paymentDto.getCardNumber());
        payment.setExpiryDate(paymentDto.getExpiryDate());
        payment.setCvv(paymentDto.getCvv());
        payment.setBooking(booking);
        payment.setStatus(paymentDto.getStatus());
        payment.setStatus("PAID"); // Set the status to PAID

        // Save the payment
        paymentRepository.save(payment);

        // You can implement additional payment processing logic here
        
        return true;
	}

}
