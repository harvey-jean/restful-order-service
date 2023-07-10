package com.jean.ordering.customer;

import com.jean.ordering.shared.exceptions.BadRequestException;
import com.jean.ordering.shared.exceptions.ResourceNotFoundException;
import com.jean.ordering.shared.responses.MessageResponse;
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
    public ResponseEntity<?> addCustomer(@RequestBody Customer customer) {
        try {
            CustomerDTO customerDTO = customerService.addCustomer(customer);
            return ResponseEntity.status(HttpStatus.CREATED).body(customerDTO);
        }catch (BadRequestException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id, @RequestBody Customer updatedCustomer) {
        try {
            updatedCustomer.setId(id);
            CustomerDTO customerDTOUpdate = customerService.updateCustomer(id, updatedCustomer);
            return ResponseEntity.ok(customerDTOUpdate);
        }catch (BadRequestException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage()));
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> partialUpdateCustomer(@PathVariable Long id,
                                                             @RequestParam(required = true) String email) {
        try {
            CustomerDTO customerDTOPartialUpdate = customerService.partialUpdateCustomer(id, email);
            return ResponseEntity.ok(customerDTOPartialUpdate);
        }catch (ResourceNotFoundException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage()));
        }catch (BadRequestException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
    }

}
