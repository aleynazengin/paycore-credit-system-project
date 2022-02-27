package com.patika.paycorecreditsystemproject.service.impl;

import com.patika.paycorecreditsystemproject.exception.NotFoundException;
import com.patika.paycorecreditsystemproject.model.CreditApplication;
import com.patika.paycorecreditsystemproject.model.Customer;
import com.patika.paycorecreditsystemproject.repository.CustomerRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreditApplicationImplTest {
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CreditApplicationImpl creditApplicationService;

    @Test
    void addCreditApplication_not_found() {
        when(customerRepository.findByNationalId("12312340080")).thenReturn(null);

        // then step
        assertThrows(NotFoundException.class,
                () -> {
                    List<CreditApplication> actualCustomer = creditApplicationService.getAllApplicationByCustomer("12312340080");
                }
        );
    }

    @Test
    void getAllApplicationByCustomer_successful() {
        Customer customer = new Customer(11, "12312340080", "Firstname", "Lastname", 7000, "05554443322", new ArrayList<>());
        CreditApplication application1 = new CreditApplication(1,customer,false,0,new Date());

        CreditApplication application2 = new CreditApplication(2,customer,false,0,new Date());
        Customer updatedCustomer = new Customer(11, "12312340080", "Firstname", "Lastname", 7000, "05554443322", Arrays.asList(application1,application2));

        List<CreditApplication> expectedApplications = new ArrayList<>();
        expectedApplications.add(application1);
        expectedApplications.add(application2);

        // stub - when
        when(customerRepository.findByNationalId("12312340080")).thenReturn(updatedCustomer);
        // then
        List<CreditApplication> allApplications = creditApplicationService.getAllApplicationByCustomer(updatedCustomer.getNationalId());

        Assert.assertEquals(expectedApplications.size(), allApplications.size());
    }
    @Test
    void getAllApplicationByCustomer_notfound() {
        when(customerRepository.findByNationalId("12312340080")).thenReturn(null);

        // then step
        assertThrows(NotFoundException.class,
                () -> {
                    List<CreditApplication> actualCustomer = creditApplicationService.getAllApplicationByCustomer("12312340080");
                }
        );
    }


}