package com.systemlab.ecommerce_application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.systemlab.ecommerce_application.model.Address;
import com.systemlab.ecommerce_application.model.User;
import com.systemlab.ecommerce_application.model.UserRole;
import com.systemlab.ecommerce_application.repository.UserRepository;
import com.systemlab.ecommerce_application.service.UserService;

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
        List<User> users = List.of(createUser(2L));
        when(userRepository.findAll()).thenReturn(users);
        List<User> result = userService.fetchAllUsers();
        assertEquals(users, result);
    }

    @Test
    void testAddUser() {
        User user = createUser(2L);
        when(userRepository.save(user)).thenReturn(user);
        User result = userService.addUser(user);
        assertEquals(user, result);
    }

    @Test
    void testFetchUser() {
        User user = createUser(2L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Optional<User> result = userService.fetchUser(1L);
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void testUpdateUser() {
        User user = createUser(1L);
        when(userRepository.existsById(1L)).thenReturn(true);
        when(userRepository.save(user)).thenReturn(user);
        User result = userService.updateUser(user);
        assertEquals(user, result);
    }

    private User createUser(Long id) {
        Address address = new Address(); // Assuming a default constructor or appropriate setters are used
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();
        return new User(id, "Jane", "Doe", "jane.doe@example.com", "123456789", UserRole.ADMIN, address, createdAt,
                updatedAt);
    }
}
