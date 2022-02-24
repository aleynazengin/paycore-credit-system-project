package com.patika.paycorecreditsystemproject.repository;

import com.patika.paycorecreditsystemproject.model.Customer;
import com.patika.paycorecreditsystemproject.model.CustomerCreditScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerCreditScoreRepository extends JpaRepository<CustomerCreditScore, Integer> {
    CustomerCreditScore findByCustomerNationalId(String tckn);
}
