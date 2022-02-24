package com.patika.paycorecreditsystemproject.repository;

import com.patika.paycorecreditsystemproject.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Customer findByNationalId(String nationalId);
}
