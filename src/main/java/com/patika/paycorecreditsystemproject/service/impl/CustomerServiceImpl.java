package com.patika.paycorecreditsystemproject.service.impl;

import com.patika.paycorecreditsystemproject.exception.NationalIdAlreadyExistsException;
import com.patika.paycorecreditsystemproject.exception.NotFoundException;
import com.patika.paycorecreditsystemproject.model.Customer;
import com.patika.paycorecreditsystemproject.repository.CustomerRepository;
import com.patika.paycorecreditsystemproject.service.CustomerCreditScoreService;
import com.patika.paycorecreditsystemproject.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerCreditScoreService creditScoreService;

    @Override
    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomer(Integer id) {
        Optional<Customer> byId = customerRepository.findById(id);
        return byId.orElseThrow(() -> new NotFoundException("Customer"));
    }

    @Override
    public void addCustomer(Customer customer) {
        if (customerRepository.findByNationalId(customer.getNationalId())!=null)
            throw new NationalIdAlreadyExistsException();

        customerRepository.save(customer);
        creditScoreService.addScore(customer);
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public boolean deleteCustomer(Integer id) {
        customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer"));
        customerRepository.delete(getCustomer(id));
        return true;
    }
}
