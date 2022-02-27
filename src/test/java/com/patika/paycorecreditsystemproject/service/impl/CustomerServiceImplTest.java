package com.patika.paycorecreditsystemproject.service.impl;

import com.patika.paycorecreditsystemproject.exception.NationalIdAlreadyExistsException;
import com.patika.paycorecreditsystemproject.exception.NotFoundException;
import com.patika.paycorecreditsystemproject.model.CreditApplication;
import com.patika.paycorecreditsystemproject.model.Customer;
import com.patika.paycorecreditsystemproject.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {
    private static Customer updatedCustomer;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @BeforeAll
    public static void init() {
        Customer customer = new Customer(11, "12312340080", "Firstname", "Lastname", 7000, "05554443322", new ArrayList<>());
        CreditApplication expectedApplication = new CreditApplication(1,customer,false,0,new Date());
        updatedCustomer = new Customer(11, "12312340080", "Firstname", "Lastname", 7000, "05554443322", Arrays.asList(expectedApplication));
    }
    @Test
    void getPassenger_successful() {
        // stub - when step
        Optional<Customer> expectedOptCustomer = Optional.of(updatedCustomer);
        when(customerRepository.findById(11)).thenReturn(expectedOptCustomer);

        // then step
        Customer actualCustomer = customerService.getCustomer(11);

        // valid step
        assertEquals(updatedCustomer, actualCustomer);
    }

    @Test
    void getPassenger_not_found() {
        // stub - when step
        when(customerRepository.findById(11)).thenReturn(Optional.empty());

        // then step
        assertThrows(NotFoundException.class,
                () -> {
                    Customer actualCustomer = customerService.getCustomer(11);
                }
        );
    }

    @Test
    void addCustomer_successful() {
        // stub - when
        when(customerRepository.save(updatedCustomer)).thenReturn(updatedCustomer);

        // then
        customerService.addCustomer(updatedCustomer);

        verify(customerRepository, times(1)).save(updatedCustomer);
    }
    @Test
    void addCustomer_national_id_already_exists() {

        when(customerRepository.findByNationalId(any())).thenReturn(updatedCustomer);

        assertThrows(NationalIdAlreadyExistsException.class,
                () -> {
                customerService.addCustomer(updatedCustomer);
                }
        );
    }

    @Test
    void updateCustomer() {
        Customer customer2 = new Customer(11, "12312340080", "Aleyna", "Zengin", 7000, "05554443322", new ArrayList<>());
        CreditApplication expectedApplication2 = new CreditApplication(1,customer2,false,0,new Date());
        Customer updatedCustomer2 = new Customer(11, "12312340080", "Aleyna", "Zengin", 7000, "05554443322", Arrays.asList(expectedApplication2));

        // stub - when
        when(customerRepository.save(updatedCustomer2)).thenReturn(updatedCustomer2);

        // then
        Customer actualCustomer= customerService.updateCustomer(updatedCustomer2);
        assertEquals(updatedCustomer2, actualCustomer);

    }

    @Test
    void deleteCustomer_successful() {

        Optional<Customer> expectedOptCustomer = Optional.of(updatedCustomer);
        when(customerRepository.findById(11)).thenReturn(expectedOptCustomer);

        // then step
        Boolean actualCustomer=customerService.deleteCustomer (11);

        assertTrue(actualCustomer);
    }
    @Test
    void deleteCustomer_not_found() {
        when(customerRepository.findById(11)).thenReturn(Optional.empty());

        // then step
        assertThrows(NotFoundException.class,
                () -> {
                    Customer actualCustomer = customerService.getCustomer(11);
                }
        );
    }
}