package com.scorpionbank.customer_service.mappers;


import com.scorpionbank.customer_service.Entity.Customer;
import com.scorpionbank.customer_service.dto.request.CustomerCreateRequest;
import com.scorpionbank.customer_service.dto.request.CustomerUpdateRequest;
import com.scorpionbank.customer_service.dto.response.CustomerResponse;
import com.scorpionbank.customer_service.enums.CustomerStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Component
public class CustomerMapper {

    public Customer toEntity(CustomerCreateRequest request) {

        Customer customer = new Customer();

        customer.setFirstName(request.firstName().trim());
        customer.setLastName(request.lastName().trim());
        customer.setEmail(request.email().trim().toLowerCase());
        customer.setPhone(request.phone().trim());
        customer.setDateOfBirth(request.dateOfBirth());
        customer.setAddress(request.address());

        customer.setStatus(CustomerStatus.PENDING_VERIFICATION);

        return customer;
    }

    public void updateEntity(
            Customer customer,
            CustomerUpdateRequest request) {

        customer.setFirstName(request.firstName().trim());
        customer.setLastName(request.lastName().trim());
        customer.setPhone(request.phone().trim());
        customer.setDateOfBirth(LocalDate.parse(request.dateOfBirth()));
        customer.setAddress(request.address());
    }

    public CustomerResponse toResponse(Customer customer) {

        return new CustomerResponse(
                customer.getId(),
                customer.getAuthUserId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getCustomerNumber(),
                customer.getDateOfBirth(),
                customer.getAddress(),
                customer.getStatus(),
                customer.getCreatedAt(),
                customer.getUpdatedAt()
        );
    }
}