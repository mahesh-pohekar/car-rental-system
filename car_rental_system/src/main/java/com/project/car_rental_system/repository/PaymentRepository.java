package com.project.car_rental_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.car_rental_system.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {

	Payment findByBookingId(Long id);

}
