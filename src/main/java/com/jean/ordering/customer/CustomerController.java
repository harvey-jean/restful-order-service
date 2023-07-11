package com.jean.ordering.customer;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Harvey's on 7/6/2023.
 */
@RestController
@RequestMapping(path = "/api/v1/customers")
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public List<CustomerDTO> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public CustomerDTO getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    @GetMapping("/search")
    public List<CustomerDTO> getCustomersByNameOrAddressOrEmail(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String email) {
        return customerService.getCustomersBySearchingParams(name, address, email);
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> addCustomer(@RequestBody Customer customer) {
            return new ResponseEntity(customerService.addCustomer(customer),HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public CustomerDTO updateCustomer(@PathVariable Long id,
                                      @RequestBody Customer updatedCustomer) {
            return customerService.updateCustomer(id, updatedCustomer);
    }

    @PatchMapping("/{id}")
    public CustomerDTO partialUpdateCustomerEmail(@PathVariable Long id,
                                                  @RequestParam(required = true) String email) {
            return customerService.partialUpdateCustomer(id, email);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
            customerService.deleteCustomer(id);
    }

}
