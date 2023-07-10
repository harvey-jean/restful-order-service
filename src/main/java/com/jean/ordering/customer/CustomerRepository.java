package com.jean.ordering.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Harvey's on 7/6/2023.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query(value = "SELECT * FROM customers cust WHERE cust.email = :cust_email", nativeQuery = true)
    Optional<Customer> findByEmail(@Param("cust_email") String email);
    Optional<Customer> findByPhone(String phone);
    @Query(value = "SELECT " +
                        "CASE " +
                            "WHEN COUNT(*) > 0 THEN TRUE " +
                            "ELSE FALSE " +
                        "END " +
                    "FROM customers cust " +
                    "WHERE cust.email = :cust_email", nativeQuery = true)
    int numberOfEmailExistsSelection(@Param("cust_email") String email);
    @Query(value = "SELECT cust FROM Customer cust WHERE lower(cust.name) = lower(?1)")
    List<Customer> findByNameContainingIgnoreCase(String name);
    List<Customer> findByAddressContainingIgnoreCase(String address);
    @Query(value = "SELECT * FROM customers cust " +
                    "WHERE lower(cust.name) = lower(:cust_name) " +
                    "AND lower(cust.address) = lower(:cust_address)", nativeQuery = true)
    List<Customer> findByNameContainingIgnoreCaseAndAddressContainingIgnoreCase(@Param("cust_name") String name,
                                                                                @Param("cust_address") String address);

}
