package com.scorpionbank.authservice.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record RegisterRequest(
       @NotBlank(message = "first name is required ")
       @Size(max = 100)
       String firstName,
       @NotBlank(message = "last name is required ")
       @Size(max = 100)
       String lastName,
       @NotBlank(message = "email is required ")
       @Email(message = "invalid email")
       String email,

       @NotBlank(message = "mobile is required ")
       @Pattern(
               regexp = "^[0-9]{10}$",
               message = "mobile must contains 10 digits"
       )
       String phone,

       @NotNull
       LocalDate dateOfBirth,

       @NotBlank
       String address,

       @NotBlank(message = "password is required ")
       @Size(min=8,max = 16)
       String password





) {
}
