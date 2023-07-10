package com.jean.ordering.customer;

import com.jean.ordering.shared.exceptions.BadRequestException;
import com.jean.ordering.shared.exceptions.ResourceNotFoundException;
import com.jean.ordering.validations.AgeValidator;
import com.jean.ordering.validations.PhoneNumberValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public CustomerDTO getCustomerById(final Long id) {
        return customerRepository.findById(id)
                .map(customerDTOMapper)
                .orElseThrow(() -> new ResourceNotFoundException("Customer with id:[%s] does not exist"
                        .formatted(id)));
    }

    public CustomerDTO getCustomerByEmail(final String email) {
        return customerRepository.findByEmail(email)
                .map(customerDTOMapper)
                .orElseThrow(() -> new ResourceNotFoundException("Customer with email:[%s] does not exist"
                        .formatted(email)));
    }

    public List<CustomerDTO> getCustomerByName(final String name) {

        List<CustomerDTO> customers = customerRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(customerDTOMapper)
                .collect(Collectors.toList());

        if(customers.isEmpty()){
            throw new ResourceNotFoundException("Customer with name:[%s] does not exist"
                    .formatted(name));
        }

        return customers;
    }

    public List<CustomerDTO> getCustomerByAddress(final String address) {

        List<CustomerDTO> customers = customerRepository.findByAddressContainingIgnoreCase(address)
                .stream()
                .map(customerDTOMapper)
                .collect(Collectors.toList());

        if(customers.isEmpty()){
            throw new ResourceNotFoundException("Customer with address:[%s] does not exist"
                    .formatted(address));
        }

        return customers;
    }

    public List<CustomerDTO> getCustomersByNameAndAddress(final String name, final String address) {
        List<CustomerDTO> customers = customerRepository.
                findByNameContainingIgnoreCaseAndAddressContainingIgnoreCase(name, address)
                .stream()
                .map(customerDTOMapper)
                .collect(Collectors.toList());

        if(customers.isEmpty()){
            throw new ResourceNotFoundException(
                    "Customer with name:[%s] and address:[%s] does not exist"
                            .formatted(name, address)
            );
        }

        return customers;
    }

    public List<CustomerDTO> getCustomersBySearchingParams(final String name,
                                                           final String address,
                                                           final String email) {
        if (name != null && address != null) {
            return getCustomersByNameAndAddress(name, address);
        } else if (name != null) {
            return getCustomerByName(name);
        } else if (address != null) {
            return getCustomerByAddress(address);
        } else if (email != null) {
            return List.of(getCustomerByEmail(email));
        } else {
            return getAllCustomers();
        }
    }

    public CustomerDTO addCustomer(final Customer customerRequest) {
        //Setting the date of registration
        customerRequest.setCreatedAt(LocalDateTime.now());

        int numberOfEmailAlreadyExists = customerRepository
                .numberOfEmailExistsSelection(customerRequest.getEmail());
        Optional<Customer> customerWithTheSamePhone = customerRepository
                .findByPhone((customerRequest.getPhone()));
        //Cehck if the email is already taken
        if(numberOfEmailAlreadyExists > 0){
            throw new BadRequestException("Email [%s] is already taken".formatted(customerRequest.getEmail()));
        }
        //Cehck if the phone is already registered
        if(customerWithTheSamePhone.isPresent()){
            throw new BadRequestException("Phone [%s] is already exist".formatted(customerRequest.getPhone()));
        }
        //Check if the phone is a Polish valid phone number
        if(!phoneNumberValidator.test(customerRequest.getPhone())){
            throw new BadRequestException("Phone [%s] is not a Polish valid phone number".formatted(customerRequest.getPhone()));
        }
        //Check if a customer is an adult (18 years old)
        if(!ageValidator.test(customerRequest.getAge())){
            throw new BadRequestException("Customers under 18 are not allowed to be using this system");
        }

        customerRepository.save(customerRequest);

        return customerDTOMapper.apply(customerRequest);
    }

    public CustomerDTO updateCustomer(final Long id, final Customer updatedCustomer) {

        CustomerDTO existingCustomer = getCustomerById(id);

        int numberOfEmailAlreadyExists = customerRepository
                .numberOfEmailExistsSelection(updatedCustomer.getEmail());
        Optional<Customer> customerWithTheSamePhone = customerRepository
                .findByPhone((updatedCustomer.getPhone()));
        Optional<Customer> customerWithTheSameEmail = customerRepository
                .findByEmail((updatedCustomer.getEmail()));

        //Check if the email is already taken by another customer with a different Id
        if(numberOfEmailAlreadyExists > 0
                && id != customerWithTheSameEmail.get().getId()){
            throw new BadRequestException("Email [%s] is already taken".formatted(updatedCustomer.getEmail()));
        }
        //Cehck if the phone is already registered under another Customer with a different Id
        if(customerWithTheSamePhone.isPresent()
                && id != customerWithTheSamePhone.get().getId()){
            throw new BadRequestException("Phone [%s] is already exist".formatted(updatedCustomer.getPhone()));
        }
        //Check if the phone is a Polish valid phone number
        if(!phoneNumberValidator.test(updatedCustomer.getPhone())){
            throw new BadRequestException("Phone [%s] is not a Polish valid phone number".formatted(updatedCustomer.getPhone()));
        }
        //Check if a customer is an adult (18 years old)
        if(!ageValidator.test(updatedCustomer.getAge())){
            throw new BadRequestException("Customers under 18 are not allowed to be using this system");
        }

        updatedCustomer.setCreatedAt(existingCustomer.getCreatedAt());
        customerRepository.save(updatedCustomer);

        return customerDTOMapper.apply(updatedCustomer);
    }

    public CustomerDTO partialUpdateCustomer(final Long id,
                                             final String email) {

        Optional<Customer> existingCustomer = customerRepository.findById(id);
        if(existingCustomer.isPresent()) {
                int numberOfEmailAlreadyExists = customerRepository
                        .numberOfEmailExistsSelection(email);
                Optional<Customer> customerWithTheSameEmail = customerRepository
                        .findByEmail((email));

                //Check if the email is already taken by another customer with a different Id
                if (numberOfEmailAlreadyExists > 0
                        && id != customerWithTheSameEmail.get().getId()) {
                    throw new BadRequestException("Email [%s] is already taken".formatted(email));
                }
                existingCustomer.get().setEmail(email);
                customerRepository.save(existingCustomer.get());
        }else{
            throw new ResourceNotFoundException("Customer Id [%s] does not exist".formatted(id));
        }

        return customerDTOMapper.apply(existingCustomer.get());
    }

    public void deleteCustomer(final Long id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Customer id:[%s] does not exist".formatted(id));
        }
    }

}
