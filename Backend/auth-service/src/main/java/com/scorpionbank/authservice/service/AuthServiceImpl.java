package com.scorpionbank.authservice.service;
import com.scorpionbank.authservice.authClient.CustomerServiceClient;
import com.scorpionbank.authservice.dto.AuthResponse;
import com.scorpionbank.authservice.dto.LoginRequest;
import com.scorpionbank.authservice.dto.RegisterRequest;
import com.scorpionbank.authservice.entity.User;
import com.scorpionbank.authservice.enums.Role;
import com.scorpionbank.authservice.enums.UserStatus;
import com.scorpionbank.authservice.exception.ResourceConflictException;
import com.scorpionbank.authservice.exception.ResourceNotFoundException;
import com.scorpionbank.authservice.repository.UserRepository;
import com.scorpionbank.authservice.security.JwtService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomerServiceClient customerServiceClient;

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {

        String email = request.email()
                .trim()
                .toLowerCase(Locale.ROOT);

        if (userRepository.existsByEmailIgnoreCase(email)) {
            throw new ResourceConflictException(
                    "Email is already registered"
            );
        }

        User user = User.builder()
                .email(email)
                .password(passwordEncoder.encode(request.password()))
                .role(Role.CUSTOMER)
                .userStatus(UserStatus.ACTIVE)
                .build();

        user = userRepository.saveAndFlush(user);

        // Create customer profile in Customer Service.

        CustomerServiceClient.CustomerResponse customer =
                customerServiceClient.createCustomer(
                        new CustomerServiceClient.CustomerCreateRequest(
                                user.getId(),
                                request.firstName(),
                                request.lastName(),
                                email,
                                request.phone()
                        )
                );

        if (customer == null || customer.id() == null) {
            throw new IllegalStateException(
                    "Customer Service returned an invalid response"
            );
        }

        user.setCustomerId(customer.id());

        user = userRepository.save(user);

        String token = jwtService.generateToken(user);

        return new AuthResponse(
                token,
                "Bearer",
                jwtService.getExpiration() / 1000,
                user.getId(),
                customer.id(),
                customer.customerNumber(),
                user.getEmail(),
                user.getRole().name()
        );
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        String email = request.email()
                .trim()
                .toLowerCase(Locale.ROOT);

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        email,
                        request.password()
                )
        );

        User user = userRepository
                .findByEmailIgnoreCase(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        ));

        String token = jwtService.generateToken(user);

        return new AuthResponse(
                token,
                "Bearer",
                jwtService.getExpiration() / 1000,
                user.getId(),
                user.getCustomerId(),
                null,
                user.getEmail(),
                user.getRole().name()
        );
    }
}
