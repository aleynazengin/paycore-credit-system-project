package com.patika.paycorecreditsystemproject.service;

import com.patika.paycorecreditsystemproject.model.Customer;
import com.patika.paycorecreditsystemproject.model.CustomerCreditScore;

import javax.validation.Valid;

public interface CustomerCreditScoreService {
    void addScore(@Valid Customer customer);
}
