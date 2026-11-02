package com.scorpionbank.customer_service.exception;

import java.util.UUID;

public class CustomerNotFoundException extends RuntimeException{
    public CustomerNotFoundException(UUID id){
        super("customer not found with id:"+id);
    }
}
