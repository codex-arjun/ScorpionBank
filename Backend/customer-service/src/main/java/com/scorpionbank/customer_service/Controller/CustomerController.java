package com.scorpionbank.customer_service.Controller;
import com.scorpionbank.customer_service.dto.request.CustomerCreateRequest;
import com.scorpionbank.customer_service.dto.request.CustomerStatusRequest;
import com.scorpionbank.customer_service.dto.request.CustomerUpdateRequest;
import com.scorpionbank.customer_service.dto.response.CustomerResponse;
import com.scorpionbank.customer_service.service.CustomerService;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Sort;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // Create customer
    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(
            @Valid @RequestBody CustomerCreateRequest request) {

        CustomerResponse response =
                customerService.createCustomer(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // Get customer by ID
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(
            @PathVariable UUID id) {

        return ResponseEntity.ok(
                customerService.getCustomerById(id)
        );
    }

    // Get all customers, optionally filter by status
    @GetMapping
    public ResponseEntity<Page<CustomerResponse>> getAllCustomers(
            @RequestParam(required = false)
            com.scorpionbank.customer_service.enums.CustomerStatus status,

            @PageableDefault(
                    size = 10,
                    sort = "createdAt",
                    direction = Sort.Direction.DESC
            ) Pageable pageable) {

        if (status != null) {
            return ResponseEntity.ok(
                    customerService.getCustomersByStatus(status, pageable)
            );
        }

        return ResponseEntity.ok(
                customerService.getAllCustomers(pageable)
        );
    }

    // Update customer profile
    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(
            @PathVariable UUID id,
            @Valid @RequestBody CustomerUpdateRequest request) {

        return ResponseEntity.ok(
                customerService.updateCustomer(id, request)
        );
    }

    // Update customer status
    @PatchMapping("/{id}/status")
    public ResponseEntity<CustomerResponse> updateCustomerStatus(
            @PathVariable UUID id,
            @Valid @RequestBody CustomerStatusRequest request) {

        return ResponseEntity.ok(
                customerService.updateCustomerStatus(
                        id,
                        request.status()
                )
        );
    }
}
