package com.scorpionbank.customer_service.dto.request;

import com.scorpionbank.customer_service.enums.CustomerStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CustomerStatusRequest(
        @NotNull(message = "Customer status is required")
        CustomerStatus status

) {
}
