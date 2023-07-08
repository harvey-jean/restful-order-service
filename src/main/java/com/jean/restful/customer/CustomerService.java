package com.jean.restful.customer;

import com.jean.restful.shared.BadRequestException;
import com.jean.restful.shared.PhoneNumberValidator;
import com.jean.restful.shared.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by Harvey's on 7/6/2023.
 */
@AllArgsConstructor
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PhoneNumberValidator phoneNumberValidator;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer id:"+ id +" does not exist"));
    }

    public Customer getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Customer email:"+ email +" does not exist"));
    }

    public List<Customer> getCustomerByName(String name) {

        List<Customer> customers = customerRepository.findByNameContainingIgnoreCase(name);

        if(customers.isEmpty()){
            throw new ResourceNotFoundException("No Customer with name:"+ name + " is found");
        }

        return customers;
    }

    public List<Customer> getCustomerByAddress(String address) {

        List<Customer> customers = customerRepository.findByAddressContainingIgnoreCase(address);

        if(customers.isEmpty()){
            throw new ResourceNotFoundException("No Customer with address:"+ address + " is found");
        }

        return customers;
    }

    public List<Customer> getCustomersByNameAndAddress(String name, String address) {
        List<Customer> customers = customerRepository.
                findByNameContainingIgnoreCaseAndAddressContainingIgnoreCase(name, address);

        if(customers.isEmpty()){
            throw new ResourceNotFoundException(
                    "Customer with name:"+ name + " with address: "+ address +" does not exist"
            );
        }

        return customers;
    }

    public Customer addCustomer(Customer customerRequest) {

        Optional<Customer> customerWithTheSameEmail = customerRepository.findByEmail(customerRequest.getEmail());
        Optional<Customer> customerWithTheSamePhone = customerRepository.findByPhone((customerRequest.getPhone()));
        //Cehck if the email is not already taken
        if(customerWithTheSameEmail.isPresent()){
            throw new BadRequestException("Email " + customerRequest.getEmail() + " is already taken");
        }
        //Cehck if the phone is not already registered
        if(customerWithTheSamePhone.isPresent()){
            throw new BadRequestException("Phone " + customerRequest.getPhone() + " is already exist");
        }
        //Check if the phone is a Polish valid phone number
        if(!phoneNumberValidator.test(customerRequest.getPhone())){
            throw new BadRequestException("Phone " + customerRequest.getPhone() + " is not a Polish valid phone number");
        }

        return customerRepository.save(customerRequest);
    }

    public Customer updateCustomer(Long id, Customer updatedCustomer) {

        Customer existingCustomer = getCustomerById(id);

        existingCustomer.setName(updatedCustomer.getName());
        existingCustomer.setEmail(updatedCustomer.getEmail());
        existingCustomer.setPhone(updatedCustomer.getPhone());
        existingCustomer.setAddress(updatedCustomer.getAddress());
        existingCustomer.setGender(updatedCustomer.getGender());

        return customerRepository.save(updatedCustomer);
    }

    public Customer partialUpdateCustomer(Long id, Customer updatedCustomer) {

        Customer existingCustomer = getCustomerById(id);

        if(updatedCustomer.getName() != null) {
            existingCustomer.setName(updatedCustomer.getName());
        }

        if (updatedCustomer.getEmail() != null) {
            existingCustomer.setEmail(updatedCustomer.getEmail());
        }

        if(updatedCustomer.getPhone() != null) {
            existingCustomer.setPhone(updatedCustomer.getPhone());
        }

        if(updatedCustomer.getAddress() != null) {
            existingCustomer.setAddress(updatedCustomer.getAddress());
        }

        if(updatedCustomer.getGender() != null) {
            existingCustomer.setGender(updatedCustomer.getGender());
        }

        return customerRepository.save(updatedCustomer);
    }

    public void deleteCustomer(Long id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Customer id:"+ id +" does not exist");
        }
    }

}
