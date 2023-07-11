package com.jean.ordering.customer;

import com.jean.ordering.shared.exceptions.BadRequestException;
import com.jean.ordering.validations.AgeValidator;
import com.jean.ordering.validations.PhoneNumberValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

/**
 * Created by Harvey's on 7/11/2023.
 */
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private CustomerDTOMapper customerDTOMapper;
    @Mock
    private PhoneNumberValidator phoneNumberValidator;
    @Mock
    private AgeValidator ageValidator;
    @Captor
    private ArgumentCaptor<Customer> customerArgumentCaptor;

    private CustomerService underTest;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        underTest = new CustomerService(customerRepository,
                customerDTOMapper,
                phoneNumberValidator,
                ageValidator);
    }

    @Test
    void itShouldAddNewCustomer() {
        // Given a email, a polish phone number and a customer
        String email = "jean@yahoo.com";
        String phoneNumber = "+48452758965";
        int age = 30;
        Customer customer = new Customer(null, "Jean",
                email,
                phoneNumber,
                "Warsaw",age, Gender.MALE, null, null,null);

        // If No customer with same email passed
        given(customerRepository.findByEmail(phoneNumber))
                .willReturn(Optional.empty());

        // If No customer with same phone number passed
        given(customerRepository.findByPhone(phoneNumber))
                .willReturn(Optional.empty());

        // Valid Polish Phone number
        given(phoneNumberValidator.test(phoneNumber)).willReturn(true);

        // Customer is an adult having 18 years old
        given(ageValidator.test(age)).willReturn(true);

        // When
        underTest.addCustomer(customer);

        // Then
        then(customerRepository).should().save(customerArgumentCaptor.capture());
        Customer customerArgumentCaptorValue = customerArgumentCaptor.getValue();
        assertThat(customerArgumentCaptorValue).isEqualTo(customer);
    }

    @Test
    void itShouldNotAddNewCustomerIfNotAValidPolishPhonenumber() {
        // Given a email, a polish phone number and a customer
        String email = "jean@yahoo.com";
        //Not A valid Polish number
        String phoneNumber = "+0144452758965";
        int age = 30;
        Customer customer = new Customer(null, "Jean",
                email,
                phoneNumber,
                "Warsaw",age, Gender.MALE, null, null,null);

        // Valid Polish Phone number
        given(phoneNumberValidator.test(phoneNumber)).willReturn(false);

        // When
        assertThatThrownBy(() -> underTest.addCustomer(customer))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Phone [%s] is not a Polish valid phone number"
                        .formatted(phoneNumber));

        // Then
        then(customerRepository).should(never()).save(any(Customer.class));
    }

}