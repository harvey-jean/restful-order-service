package com.jean.restful.customer;

import com.jean.restful.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Harvey's on 7/6/2023.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
