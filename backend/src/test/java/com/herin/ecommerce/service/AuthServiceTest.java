package com.herin.ecommerce.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.herin.ecommerce.dto.UserDTO.UserRequestDTO;
import com.herin.ecommerce.dto.UserDTO.UserResponseDTO;
import com.herin.ecommerce.model.UserEntity;
import com.herin.ecommerce.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JWTService jwtService; // Mocking this since it's in your constructor

    @InjectMocks
    private AuthService authService;

    private UserRequestDTO userRequest;

    @BeforeEach
    void setUp() {
        userRequest = new UserRequestDTO();
        userRequest.setUsername("testuser");
        userRequest.setEmail("test@example.com");
        userRequest.setPassword("StrongPass1!");
    }

    @Test
    void testRegisterUser_Success() {
        // Arrange (Prepare expectations)
        when(userRepository.existsByUsername(userRequest.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(userRequest.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(userRequest.getPassword())).thenReturn("encodedPassword");
        
        UserEntity savedUser = new UserEntity();
        savedUser.setId(1L);
        savedUser.setUsername(userRequest.getUsername());
        savedUser.setEmail(userRequest.getEmail());
        
        when(userRepository.save(any(UserEntity.class))).thenReturn(savedUser);

        // Act (Call the method)
        UserResponseDTO response = authService.register(userRequest);

        // Assert (Verify results)
        assertNotNull(response);
        assertEquals("testuser", response.getUsername());
        assertEquals("test@example.com", response.getEmail());
        
        // Verify that save was called exactly once
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }
}