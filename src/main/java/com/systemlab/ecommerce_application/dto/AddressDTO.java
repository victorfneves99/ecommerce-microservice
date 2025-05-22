package com.systemlab.ecommerce_application.dto;

public record AddressDTO(
        String street,
        String city,
        String state,
        String zipCode,
        String country) {

}
