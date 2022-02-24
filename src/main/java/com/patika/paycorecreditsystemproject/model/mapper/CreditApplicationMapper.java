package com.patika.paycorecreditsystemproject.model.mapper;


import com.patika.paycorecreditsystemproject.model.CreditApplication;
import com.patika.paycorecreditsystemproject.model.Customer;

import java.time.LocalDateTime;
import java.util.Calendar;

public class CreditApplicationMapper {
    public static CreditApplication toCreditApplication(Customer customer,int limit,boolean isApplicationApproved)
    {
        CreditApplication creditApplication= new CreditApplication();
        creditApplication.setCreditLimit(limit);
        creditApplication.setApplicationApproved(isApplicationApproved);
        creditApplication.setCustomer(customer);
        creditApplication.setApplyDate(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
        return creditApplication;
    }
}
