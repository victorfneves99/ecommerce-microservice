package com.systemlab.ecommerce_application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class UserServiceTest {

    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    void testFetchAllUsers() {
        List<User> users = List
                .of(new User(2L, "Jane Doe", "Neves", "jane.doe@example.com", "123456789", UserRole.ADMIN));
        when(userRepository.findAll()).thenReturn(users);
        List<User> result = userService.fetchAllUsers();
        assertEquals(users, result);
    }

    @Test
    void testAddUser() {
        User user = new User(1L, "John", "Doe", "john.doe@example.com", "123456789", UserRole.CUSTOMER);
        when(userRepository.save(user)).thenReturn(user);
        User result = userService.addUser(user);
        assertEquals(user, result);
    }

    @Test
    void testFetchUser() {
        User user = new User(1L, "John", "Doe", "john.doe@example.com", "123456789", UserRole.CUSTOMER);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Optional<User> result = userService.fetchUser(1L);
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void testUpdateUser() {
        User user = new User(1L, "Jane", "Doe", "jane.doe@example.com", "123456789", UserRole.CUSTOMER);
        when(userRepository.existsById(1L)).thenReturn(true);
        when(userRepository.save(user)).thenReturn(user);
        User result = userService.updateUser(user);
        assertEquals(user, result);
    }
}