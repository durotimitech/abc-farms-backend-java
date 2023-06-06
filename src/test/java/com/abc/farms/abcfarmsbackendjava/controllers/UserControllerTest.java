package com.abc.farms.abcfarmsbackendjava.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.abc.farms.abcfarmsbackendjava.services.httpServices.requestMappings.users.RegisterRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private RegisterRequest registerRequest;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testRegisterUser() throws Exception {
        new RegisterRequest();
        registerRequest = RegisterRequest.builder()
                .email("test@test.com")
                .password("12345678")
                .firstName("John")
                .lastName("Doe")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/users/register")
                .content(objectMapper.writeValueAsString(registerRequest))
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    // @Test
    // void register_ValidRequest_ReturnsCreatedResponse() throws ConflictError {
    // // Arrange
    // RegisterRequest request = new RegisterRequest();
    // // Set up the request object

    // // Act
    // ResponseEntity<ApiResponse> response = userController.register(request);

    // // Assert
    // assertEquals(HttpStatus.CREATED, response.getStatusCode());
    // assertEquals("success", response.getBody().getMessage());
    // // Add more assertions if needed
    // }

    // @Test
    // void register_ConflictError_ThrowsConflictError() throws ConflictError {
    // // Arrange
    // RegisterRequest request = new RegisterRequest();
    // // Set up the request object

    // // Mock the userService.register() method to throw a ConflictError
    // when(userService.register(request)).thenThrow(new ConflictError("Conflict
    // error"));

    // // Act and Assert
    // assertThrows(ConflictError.class, () -> userController.register(request));
    // }

    // @Test
    // void register_InvalidRequest_ThrowsConstraintViolationException() {
    // // Arrange
    // RegisterRequest request = new RegisterRequest();
    // // Set up the request object with invalid data

    // // Act and Assert
    // assertThrows(ConstraintViolationException.class, () ->
    // userController.register(request));
    // // Add more assertions if needed
    // }

    // // Add more test cases for other scenarios if needed

}
