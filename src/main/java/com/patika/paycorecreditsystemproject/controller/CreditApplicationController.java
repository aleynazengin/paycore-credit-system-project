package com.patika.paycorecreditsystemproject.controller;

import com.patika.paycorecreditsystemproject.model.CreditApplication;
import com.patika.paycorecreditsystemproject.model.dto.CreditApplicationDTO;
import com.patika.paycorecreditsystemproject.service.CreditApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/creditApplication")
public class CreditApplicationController {
    private final CreditApplicationService creditApplicationService;

    @GetMapping
    public String welcome() {
        return "Welcome to Credit Application Service!";
    }

    @PostMapping(value = "/applyForCredit")
    public String createCreditApplication(@Valid @RequestBody CreditApplicationDTO creditApplicationDTO) {
        creditApplicationService.addCreditApplication(creditApplicationDTO);
        return creditApplicationService.getResult(creditApplicationDTO.getNationalId());
    }

    @GetMapping("/by-customer/{nationalId}")
    public List<CreditApplication> getAllApplicationByCustomer(@PathVariable String nationalId) {
        return creditApplicationService.getAllApplicationByCustomer(nationalId);
    }
}
