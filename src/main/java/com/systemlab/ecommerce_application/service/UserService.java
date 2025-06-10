package com.systemlab.ecommerce_application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import com.systemlab.ecommerce_application.dto.AddressDTO;
import com.systemlab.ecommerce_application.dto.UserRequest;
import com.systemlab.ecommerce_application.dto.UserResponse;
import com.systemlab.ecommerce_application.model.Address;
import com.systemlab.ecommerce_application.model.User;
import com.systemlab.ecommerce_application.repository.UserRepository;


@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponse> fetchAllUsers() {

        var userList = userRepository.findAll();
        var resultUserList = userList.stream().map(user -> convertUserToUserResponse(user)).toList();

        return resultUserList;
    }

    public User addUser(UserRequest userRequest) {
        return userRepository.save(updateUserFromRequest(userRequest,null));
    }

    public Optional<UserResponse> fetchUser(Long id) {
        return userRepository.findById(id).map(user -> convertUserToUserResponse(user));
    }

    public boolean updateUser(Long id,UserRequest updateUserRequest) {
        return userRepository.findById(id)
        .map(existingUser -> {
            User updatedUser = updateUserFromRequest(updateUserRequest,existingUser);
            userRepository.save(updatedUser);
            return true;
        }).orElse(false);
       
    }

    private UserResponse convertUserToUserResponse(User user) {
        var address = new AddressDTO();

        if (user.getAddress() != null) {
            address.setStreet(user.getAddress().getStreet());
            address.setCity(user.getAddress().getCity());
            address.setState(user.getAddress().getState());
            address.setCountry(user.getAddress().getCountry());
            address.setZipCode(user.getAddress().getZipCode());
        }

        return new UserResponse(String.valueOf(user.getId()), user.getFirstName(), user.getLastName(), user.getEmail(),
                user.getPhone(), user.getRole(), address);
    }


    private User updateUserFromRequest(UserRequest userRequest, @Nullable User existingUser) {
        User user = existingUser != null ? existingUser : new User();

        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());

        Address address = user.getAddress() != null ? user.getAddress() : new Address();
        if (userRequest.getAddress() != null) {
            address.setStreet(userRequest.getAddress().getStreet());
            address.setCity(userRequest.getAddress().getCity());
            address.setState(userRequest.getAddress().getState());
            address.setZipCode(userRequest.getAddress().getZipCode());
            address.setCountry(userRequest.getAddress().getCountry());
        }
        user.setAddress(address);

        return user;
    }

}
