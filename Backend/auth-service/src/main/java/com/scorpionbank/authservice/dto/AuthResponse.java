package com.scorpionbank.authservice.dto;

import java.util.UUID;

public record AuthResponse(

 String accessToken,

 String tokenType,

 long expiresln,

 UUID userId,


 UUID customerId,

 String customerNumber,

 String email,

 String role

) {
}
