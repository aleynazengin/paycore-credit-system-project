package com.patika.paycorecreditsystemproject.service;

import com.patika.paycorecreditsystemproject.model.CreditApplication;
import com.patika.paycorecreditsystemproject.model.dto.CreditApplicationDTO;

import javax.validation.Valid;
import java.util.List;

public interface CreditApplicationService {

    void addCreditApplication(@Valid CreditApplicationDTO creditApplicationDTO);

    List<CreditApplication> getAllApplicationByCustomer(String nationalId);

    String getResult(String nationalId);

    int getLimit(CreditApplication creditApplication);
}
