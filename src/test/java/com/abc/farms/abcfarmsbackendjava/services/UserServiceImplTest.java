package com.abc.farms.abcfarmsbackendjava.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.abc.farms.abcfarmsbackendjava.config.JwtService;
import com.abc.farms.abcfarmsbackendjava.entities.User;
import com.abc.farms.abcfarmsbackendjava.repositories.UserRepository;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.errors.BadRequestError;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.errors.ConflictError;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.requestMappings.users.LoginRequest;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.requestMappings.users.RegisterRequest;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.responseMappings.users.RegisterResponse;
import com.abc.farms.abcfarmsbackendjava.services.implementations.UserServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    private LoginRequest loginRequest;
    private User user;

    @InjectMocks
    private JwtService jwtService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testRegisterUser() throws BadRequestError, ConflictError {
        RegisterRequest registerRequest = new RegisterRequest();

        // Test that bad request error is thrown when request is invalid
        // assertThrows(BadRequestError.class, () ->
        // userServiceImpl.register(registerRequest));

        // Test that conflict error is thrown when user already exists
        User existingUser = new User();
        when(userRepository.findByEmail(registerRequest.getEmail())).thenReturn(Optional.of(existingUser));

        assertThrows(ConflictError.class, () -> userServiceImpl.register(registerRequest));

        // Test that user is registered successfully
        when(userRepository.findByEmail(registerRequest.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(new User());

        RegisterResponse result = userServiceImpl.register(registerRequest);

        assertTrue(result.getVerificationCode() instanceof String);

    }

    @Test
    void testLoginUser() {
        new User();
        user = User.builder()
                .email("test@test.com")
                .password("12345678")
                .build();

        new LoginRequest();
        loginRequest = LoginRequest.builder()
                .email("test@test.com")
                .password("12345678")
                .build();

        // Test that bad request error is thrown when password is incorrect
        when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())).thenReturn(false);

        assertThrows(BadRequestError.class, () -> userServiceImpl.login(loginRequest));

        // Test that bad request error is thrown when user does not exist
        when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.empty());

        assertThrows(BadRequestError.class, () -> userServiceImpl.login(loginRequest));

    }

}
