package com.jean.ordering.customer;

import org.springframework.stereotype.Service;

import java.util.function.Function;

/**
 * Created by Harvey's on 7/8/2023.
 */
@Service
public class CustomerDTOMapper implements Function<Customer, CustomerDTO> {
    @Override
    public CustomerDTO apply(Customer customer) {
        return new CustomerDTO(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getAddress(),
                customer.getAge(),
                customer.getGender(),
                customer.getCreatedAt()
        );
    }
}
