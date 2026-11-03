package com.scorpionbank.authservice.authClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Component
public class CustomerServiceClient {

        private final RestClient restClient;

        public CustomerServiceClient(
                RestClient.Builder builder,
                @Value("${customer.service.url}") String customerServiceUrl) {

            this.restClient = builder
                    .baseUrl(customerServiceUrl)
                    .build();
        }

        public CustomerResponse createCustomer(
                CustomerCreateRequest request) {

            return restClient.post()
                    .uri("/api/customers")
                    .body(request)
                    .retrieve()
                    .body(CustomerResponse.class);
        }

        public record CustomerCreateRequest(
                UUID authUserId,
                String firstName,
                String lastName,
                String email,
                String phone
        ) {}

        public record CustomerResponse(
                UUID id,
                String customerNumber
        ) {}
    }

