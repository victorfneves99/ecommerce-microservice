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

import ch.qos.logback.core.util.StringUtil;

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
        AddressDTO address = new AddressDTO(user.getAddress().getStreet(), user.getAddress().getCity(),
                user.getAddress().getState(), user.getAddress().getZipCode(), user.getAddress().getCountry());

        return new UserResponse(String.valueOf(user.getId()), user.getFirstName(), user.getLastName(), user.getEmail(),
                user.getPhone(), user.getRole(), address);
    }


    private User updateUserFromRequest(UserRequest userRequest, @Nullable User existingUser) {
    var user = existingUser != null ? existingUser : new User();

    user.setFirstName(userRequest.firstName());
    user.setLastName(userRequest.lastName());
    user.setEmail(userRequest.email());
    user.setPhone(userRequest.phone());

    if (userRequest.address() != null) {
        var address = user.getAddress() != null ? user.getAddress() : new Address();
        address.setStreet(userRequest.address().street());
        address.setCity(userRequest.address().city());
        address.setState(userRequest.address().state());
        address.setZipCode(userRequest.address().zipCode());
        address.setCountry(userRequest.address().country());
        user.setAddress(address);
    }

    return user;
}

}
