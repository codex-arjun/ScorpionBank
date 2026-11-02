package com.scorpionbank.customer_service.service;

import com.scorpionbank.customer_service.dto.request.CustomerCreateRequest;
import com.scorpionbank.customer_service.dto.request.CustomerUpdateRequest;
import com.scorpionbank.customer_service.dto.response.CustomerResponse;
import com.scorpionbank.customer_service.enums.CustomerStatus;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;
@Service
public interface CustomerService {

    CustomerResponse createCustomer(
            CustomerCreateRequest request
    );

    CustomerResponse getCustomerById(UUID id);

    Page<CustomerResponse> getAllCustomers(Pageable pageable);

    CustomerResponse updateCustomer(
            UUID id,
            CustomerUpdateRequest request
    );

    CustomerResponse updateCustomerStatus(
            UUID id,
            CustomerStatus status
    );

    Page<CustomerResponse> getCustomersByStatus(
            CustomerStatus status,
            Pageable pageable
    );
    CustomerResponse  getCustomerByCustomerNumber(String customerNumber);
}

