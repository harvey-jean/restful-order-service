package com.jean.restful.customer;

import com.jean.restful.order.Order;
import com.jean.restful.rating.Rating;
import lombok.AllArgsConstructor;
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

    @GetMapping("/{email}")
    public CustomerDTO getCustomerByEmail(@PathVariable String email) {
        return customerService.getCustomerByEmail(email);
    }

    @GetMapping("/search")
    public List<CustomerDTO> getCustomersByNameAndAddress(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String address) {
        if (name != null && address != null) {
            return customerService.getCustomersByNameAndAddress(name, address);
        } else if (name != null) {
            return customerService.getCustomerByName(name);
        } else if (address != null) {
            return customerService.getCustomerByAddress(address);
        } else {
            return customerService.getAllCustomers();
        }
    }

   /* @GetMapping("/{id}/orders")
    public List<Order> getCustomerOrders(@PathVariable Long id) {
        Customer customer = customerService.getCustomerById(id);
        return customer.getOrders();
    }*/

    /*@GetMapping("/{id}/ratings")
    public List<Rating> getCustomerRatings(@PathVariable Long id) {
        Customer customer = customerService.getCustomerById(id);
        return customer.getRatings();
    }*/

    @PostMapping
    public CustomerDTO addCustomer(@RequestBody Customer customer) {
        return customerService.addCustomer(customer);
    }

    @PutMapping("/{id}")
    public CustomerDTO updateCustomer(@PathVariable Long id, @RequestBody Customer updatedCustomer) {
        updatedCustomer.setId(id);
        return customerService.updateCustomer(id, updatedCustomer);
    }

    @PatchMapping("/{id}")
    public CustomerDTO partialUpdateCustomer(@PathVariable Long id, @RequestBody Customer updatedCustomer) {
        updatedCustomer.setId(id);
        return customerService.partialUpdateCustomer(id, updatedCustomer);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
    }

}
