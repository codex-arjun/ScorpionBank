package com.scorpionbank.customer_service.dto.request;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record CustomerCreateRequest (
    @NotBlank(message = "first name is required")
    @Size(max = 100)
    String firstName,

    @NotBlank(message = "last name is required ")
    @Size(max = 100)
    String lastName,

    @NotBlank(message = "phone number is required")
    @Pattern(
            regexp = "^[+]?[0-9]{10,15}$",
            message = "invalid phone number"
    )

    String phone,
    @NotBlank(message = "last email is required ")
    @Size(max = 100)
    @Email(message = "Invalid email format")
    String email,

    @NotNull(message = "date of birth required ")
    @Past(message = "date of birth should be past")
    LocalDate dateOfBirth,

    @Size(max = 500)
    String address
){
}
