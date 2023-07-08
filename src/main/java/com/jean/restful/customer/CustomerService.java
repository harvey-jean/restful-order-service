package com.jean.restful.customer;

import com.jean.restful.shared.AgeValidator;
import com.jean.restful.shared.BadRequestException;
import com.jean.restful.shared.PhoneNumberValidator;
import com.jean.restful.shared.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Harvey's on 7/6/2023.
 */
@AllArgsConstructor
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerDTOMapper customerDTOMapper;
    private final PhoneNumberValidator phoneNumberValidator;
    private final AgeValidator ageValidator;

    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerDTOMapper)
                .collect(Collectors.toList());
    }

    public CustomerDTO getCustomerById(Long id) {
        return customerRepository.findById(id)
                .map(customerDTOMapper)
                .orElseThrow(() -> new ResourceNotFoundException("Customer id:"+ id +" does not exist"));
    }

    public CustomerDTO getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email)
                .map(customerDTOMapper)
                .orElseThrow(() -> new ResourceNotFoundException("Customer email:"+ email +" does not exist"));
    }

    public List<CustomerDTO> getCustomerByName(String name) {

        List<CustomerDTO> customers = customerRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(customerDTOMapper)
                .collect(Collectors.toList());

        if(customers.isEmpty()){
            throw new ResourceNotFoundException("No Customer with name:"+ name + " is found");
        }

        return customers;
    }

    public List<CustomerDTO> getCustomerByAddress(String address) {

        List<CustomerDTO> customers = customerRepository.findByAddressContainingIgnoreCase(address)
                .stream()
                .map(customerDTOMapper)
                .collect(Collectors.toList());

        if(customers.isEmpty()){
            throw new ResourceNotFoundException("No Customer with address:"+ address + " is found");
        }

        return customers;
    }

    public List<CustomerDTO> getCustomersByNameAndAddress(String name, String address) {
        List<CustomerDTO> customers = customerRepository.
                findByNameContainingIgnoreCaseAndAddressContainingIgnoreCase(name, address)
                .stream()
                .map(customerDTOMapper)
                .collect(Collectors.toList());

        if(customers.isEmpty()){
            throw new ResourceNotFoundException(
                    "Customer with name:"+ name + " with address: "+ address +" does not exist"
            );
        }

        return customers;
    }

    public CustomerDTO addCustomer(Customer customerRequest) {

        customerRequest.setCreatedAt(LocalDate.now());
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
        //Check if a customer is an adult (18 years old)
        if(!ageValidator.test(customerRequest.getAge())){
            throw new BadRequestException("Customers under 18 are not allowed to be using this system");
        }

        customerRepository.save(customerRequest);

        return customerDTOMapper.apply(customerRequest);
    }

    public CustomerDTO updateCustomer(Long id, Customer updatedCustomer) {

        CustomerDTO existingCustomer = getCustomerById(id);

        existingCustomer.setName(updatedCustomer.getName());
        existingCustomer.setEmail(updatedCustomer.getEmail());
        existingCustomer.setPhone(updatedCustomer.getPhone());
        existingCustomer.setAddress(updatedCustomer.getAddress());
        existingCustomer.setGender(updatedCustomer.getGender());
        existingCustomer.setAge(updatedCustomer.getAge());

        customerRepository.save(updatedCustomer);

        return customerDTOMapper.apply(updatedCustomer);
    }

    public CustomerDTO partialUpdateCustomer(Long id, Customer updatedCustomer) {

        CustomerDTO existingCustomer = getCustomerById(id);

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

        if(updatedCustomer.getAge() != 0) {
            existingCustomer.setAge(updatedCustomer.getAge());
        }

        customerRepository.save(updatedCustomer);

        return customerDTOMapper.apply(updatedCustomer);
    }

    public void deleteCustomer(Long id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Customer id:"+ id +" does not exist");
        }
    }

}
