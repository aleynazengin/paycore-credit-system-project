package com.patika.paycorecreditsystemproject.service;

import com.patika.paycorecreditsystemproject.model.Customer;

import javax.validation.Valid;
import java.util.List;

public interface CustomerService {
    List<Customer> getAllCustomer();

    Customer getCustomer(Integer id);

    void addCustomer(@Valid Customer customer);

    Customer updateCustomer(Customer customer);

    boolean deleteCustomer(Integer id);
}
