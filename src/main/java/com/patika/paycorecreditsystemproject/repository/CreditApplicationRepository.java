package com.patika.paycorecreditsystemproject.repository;

import com.patika.paycorecreditsystemproject.model.CreditApplication;
import com.patika.paycorecreditsystemproject.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CreditApplicationRepository extends JpaRepository<CreditApplication, Integer> {

}
