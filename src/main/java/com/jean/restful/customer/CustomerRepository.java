package com.jean.restful.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Harvey's on 7/6/2023.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);
    Optional<Customer> findByPhone(String phone);
    List<Customer> findByNameContainingIgnoreCase(String name);
    List<Customer> findByAddressContainingIgnoreCase(String address);
    List<Customer> findByNameContainingIgnoreCaseAndAddressContainingIgnoreCase(String name, String address);

}
