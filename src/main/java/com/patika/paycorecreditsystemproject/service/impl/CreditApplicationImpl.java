package com.patika.paycorecreditsystemproject.service.impl;

import com.patika.paycorecreditsystemproject.exception.NotFoundException;
import com.patika.paycorecreditsystemproject.model.CreditApplication;
import com.patika.paycorecreditsystemproject.model.dto.CreditApplicationDTO;
import com.patika.paycorecreditsystemproject.model.Customer;
import com.patika.paycorecreditsystemproject.model.CustomerCreditScore;
import com.patika.paycorecreditsystemproject.repository.CreditApplicationRepository;
import com.patika.paycorecreditsystemproject.repository.CustomerCreditScoreRepository;
import com.patika.paycorecreditsystemproject.repository.CustomerRepository;
import com.patika.paycorecreditsystemproject.service.CreditApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

import static com.patika.paycorecreditsystemproject.model.mapper.CreditApplicationMapper.toCreditApplication;

@RequiredArgsConstructor
@Service
public class CreditApplicationImpl implements CreditApplicationService {
    @Autowired
    private CreditApplicationRepository creditApplicationRepository;
    @Autowired
    private CustomerCreditScoreRepository creditScoreRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Value("${sms.info.message}")
    private String message;

    private final int CREDIT_LIMIT_MULTIPLIER=4;
    int creditScore,monthlyIncome,limit=0;
    boolean isVerified;
    int checkNumber1=500,checkNumber2=1000;

    @Override
    public void addCreditApplication(CreditApplicationDTO creditApplicationDTO) {
        Customer customer = customerRepository.findByNationalId(creditApplicationDTO.getNationalId());
        if(customer==null)
            throw new NotFoundException("customer");
        monthlyIncome= customer.getMonthlyIncome();

        CustomerCreditScore score= creditScoreRepository.findByCustomerNationalId(creditApplicationDTO.getNationalId());
        creditScore=score.getCreditScore();
        isVerified=  checkIfAplicationIsApproved(creditScore,monthlyIncome);
        creditApplicationRepository.save(toCreditApplication(customer,limit, isVerified));
    }

    private boolean checkIfAplicationIsApproved(int creditScore,int monthlyIncome) {
        isVerified=true;

        if(creditScore<checkNumber1)
        {
            isVerified=false;
        }
        else if (creditScore>checkNumber1 && creditScore<checkNumber2 &&monthlyIncome<5000)
        {
            limit=10000;
        }
        else if (creditScore>checkNumber1 && creditScore<checkNumber2 &&monthlyIncome>5000)
        {
            limit=20000;
        }
        else if (creditScore>=checkNumber2)
        {
            limit= monthlyIncome*CREDIT_LIMIT_MULTIPLIER;
        }
        return isVerified;
    }

    @Override
    public List<CreditApplication> getAllApplicationByCustomer(String nationalId) {
        Customer customer = customerRepository.findByNationalId(nationalId);
        if(customer==null)
          throw new NotFoundException("Customer of this national Id");
        return customer.getCreditApplications();
    }

    @Override
    public String getResult(String nationalId) {
        Customer customer = customerRepository.findByNationalId(nationalId);
        Stream<CreditApplication> stream = customer.getCreditApplications().stream();
        long count = customer.getCreditApplications().stream().count();
        CreditApplication creditApplication =stream.skip(count - 1).findFirst()
                .orElseThrow(() -> new NotFoundException("Credit Application"));

        if(creditApplication.isApplicationApproved())
            return "Your credit application has been approved.Your credit limit is : " + getLimit(creditApplication)
                    + "." + message;
        else
            return "Your credit application has not been approved." + message;
    }

    @Override
    public int getLimit(CreditApplication creditApplication) {
        return creditApplication.getCreditLimit();
    }

}
