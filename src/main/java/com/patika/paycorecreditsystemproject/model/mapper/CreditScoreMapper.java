package com.patika.paycorecreditsystemproject.model.mapper;

import com.patika.paycorecreditsystemproject.model.Customer;
import com.patika.paycorecreditsystemproject.model.CustomerCreditScore;

public class CreditScoreMapper {
    public static CustomerCreditScore toCreditScore(Customer customer, int score) {
        CustomerCreditScore creditScore= new CustomerCreditScore();
        creditScore.setCustomer(customer);
        creditScore.setCreditScore(score);
        return creditScore;
    }
}
