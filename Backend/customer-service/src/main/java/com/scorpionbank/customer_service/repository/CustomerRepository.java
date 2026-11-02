package com.scorpionbank.customer_service.repository;

import com.scorpionbank.customer_service.Entity.Customer;
import com.scorpionbank.customer_service.enums.CustomerStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    Optional<Customer> findByEmail(String email);
    boolean existsByEmail(String email);
    Page<Customer> findByStatus(CustomerStatus status, Pageable pageable);
    boolean existsByCustomerNumber(String customerNumber);
}
