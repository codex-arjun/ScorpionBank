package com.scorpionbank.authservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

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

       @NotBlank(message = "password is required ")
       @Size(min=8,max = 16)
       String password





) {
}
