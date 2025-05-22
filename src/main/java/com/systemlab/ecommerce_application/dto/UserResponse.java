package com.systemlab.ecommerce_application.dto;

import com.systemlab.ecommerce_application.model.UserRole;

public record UserResponse(String id,
        String firstName,
        String lastName,
        String email,
        String phone,
        UserRole role,
        AddressDTO address) {

}
