package com.patika.paycorecreditsystemproject.controller;

import com.patika.paycorecreditsystemproject.model.Customer;
import com.patika.paycorecreditsystemproject.service.CustomerCreditScoreService;
import com.patika.paycorecreditsystemproject.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;
    private final CustomerCreditScoreService creditScoreService;

    @GetMapping
    public String welcome() {
        return "Welcome to Customer Service!";
    }

    @PostMapping(value = "/create")
    public void createCustomer(@Valid @RequestBody Customer customer) {
        customerService.addCustomer(customer);
    }

    @PutMapping(value = "/update")
    public Customer updateCustomer(@Valid @RequestBody Customer customer) {
        return customerService.updateCustomer(customer);
    }

    @DeleteMapping(value = "/delete")
    public boolean deleteCustomer(@RequestParam @Min(1) Integer id) {
        return customerService.deleteCustomer(id);
    }
}
