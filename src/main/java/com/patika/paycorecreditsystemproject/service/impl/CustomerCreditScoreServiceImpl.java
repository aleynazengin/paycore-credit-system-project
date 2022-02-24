package com.patika.paycorecreditsystemproject.service.impl;

import com.patika.paycorecreditsystemproject.exception.NotFoundException;
import com.patika.paycorecreditsystemproject.model.Customer;
import com.patika.paycorecreditsystemproject.model.CustomerCreditScore;
import com.patika.paycorecreditsystemproject.repository.CustomerCreditScoreRepository;
import com.patika.paycorecreditsystemproject.service.CustomerCreditScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.patika.paycorecreditsystemproject.model.mapper.CreditScoreMapper.toCreditScore;

@RequiredArgsConstructor
@Service
public class CustomerCreditScoreServiceImpl implements CustomerCreditScoreService {
    @Autowired
    private CustomerCreditScoreRepository creditScoreRepository;

    @Override
    public void addScore(Customer customer) {
        int score= generateNumberBetween(1,1500);
        creditScoreRepository.save(toCreditScore(customer,score));
    }

    private int generateNumberBetween(int min, int max) {
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }
}
