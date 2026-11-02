package com.scorpionbank.customer_service.exception;

public class DuplicateCustomerExcetion extends RuntimeException{
    public DuplicateCustomerExcetion
            (String email){
        super("customer with this email already exist:"+email);
    }
}
