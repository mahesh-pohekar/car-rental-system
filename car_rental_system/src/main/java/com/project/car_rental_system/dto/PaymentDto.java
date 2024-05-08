package com.project.car_rental_system.dto;


import lombok.Data;

@Data
public class PaymentDto {
    private String cardNumber;
    private String expiryDate;
    private int cvv;
    private String status; // Added status field

    
}

