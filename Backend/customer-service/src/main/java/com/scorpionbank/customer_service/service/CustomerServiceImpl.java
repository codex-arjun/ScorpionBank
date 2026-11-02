package com.scorpionbank.customer_service.service;

import com.scorpionbank.customer_service.Entity.Customer;
import com.scorpionbank.customer_service.dto.request.CustomerCreateRequest;
import com.scorpionbank.customer_service.dto.request.CustomerUpdateRequest;
import com.scorpionbank.customer_service.dto.response.CustomerResponse;
import com.scorpionbank.customer_service.enums.CustomerStatus;
import com.scorpionbank.customer_service.exception.CustomerNotFoundException;
import com.scorpionbank.customer_service.exception.DuplicateCustomerExcetion;
import com.scorpionbank.customer_service.mappers.CustomerMapper;
import com.scorpionbank.customer_service.repository.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


    @Service
    @Transactional
    public class CustomerServiceImpl implements CustomerService {

        private final CustomerRepository customerRepository;
        private final CustomerMapper customerMapper;

        public CustomerServiceImpl(
                CustomerRepository customerRepository,
                CustomerMapper customerMapper) {

            this.customerRepository = customerRepository;
            this.customerMapper = customerMapper;
        }

        @Override
        public CustomerResponse createCustomer(
                CustomerCreateRequest request) {

            String email = request.email().trim().toLowerCase();

            if (customerRepository.existsByEmail(email)) {
                throw new DuplicateCustomerExcetion(email);
            }

            Customer customer = customerMapper.toEntity(request);

            Customer savedCustomer = customerRepository.save(customer);

            return customerMapper.toResponse(savedCustomer);
        }

        @Override
        @Transactional(readOnly = true)
        public CustomerResponse getCustomerById(UUID id) {

            Customer customer = findCustomer(id);

            return customerMapper.toResponse(customer);
        }

        @Override
        @Transactional(readOnly = true)
        public Page<CustomerResponse> getAllCustomers(Pageable pageable) {

            return customerRepository.findAll(pageable)
                    .map(customerMapper::toResponse);
        }

        @Override
        public CustomerResponse updateCustomer(
                UUID id,
                CustomerUpdateRequest request) {

            Customer customer = findCustomer(id);

            customerMapper.updateEntity(customer, request);

            Customer updatedCustomer = customerRepository.save(customer);

            return customerMapper.toResponse(updatedCustomer);
        }

        @Override
        public CustomerResponse updateCustomerStatus(
                UUID id,
                CustomerStatus status) {

            Customer customer = findCustomer(id);

            customer.setStatus(status);

            Customer updatedCustomer = customerRepository.save(customer);

            return customerMapper.toResponse(updatedCustomer);
        }

        @Override
        @Transactional(readOnly = true)
        public Page<CustomerResponse> getCustomersByStatus(
                CustomerStatus status,
                Pageable pageable) {

            return customerRepository.findByStatus(status, pageable)
                    .map(customerMapper::toResponse);
        }

        private Customer findCustomer(UUID id) {

            return customerRepository.findById(id)
                    .orElseThrow(() -> new CustomerNotFoundException(id));
        }
    }
