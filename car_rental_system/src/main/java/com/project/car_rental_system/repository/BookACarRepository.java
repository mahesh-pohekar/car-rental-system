package com.project.car_rental_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.car_rental_system.entity.BookACar;


@Repository
public interface BookACarRepository extends JpaRepository<BookACar, Long>{

	List<BookACar> findAllByUserId(Long userId);
	
}
