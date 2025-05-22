package com.systemlab.ecommerce_application.dto;


public record UserRequest(
        String firstName,
        String lastName,
        String email,
        String phone,
        AddressDTO address) {

}
