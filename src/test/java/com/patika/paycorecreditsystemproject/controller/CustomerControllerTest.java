package com.patika.paycorecreditsystemproject.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.patika.paycorecreditsystemproject.exception.handler.GenericExceptionHandler;
import com.patika.paycorecreditsystemproject.model.CreditApplication;
import com.patika.paycorecreditsystemproject.model.Customer;
import com.patika.paycorecreditsystemproject.service.impl.CustomerServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {
    private MockMvc mvc;

    @Mock
    private CustomerServiceImpl customerService;

    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    public void setup() {
        // We would need this line if we would not use the MockitoExtension
        // MockitoAnnotations.initMocks(this);
        // Here we can't use @AutoConfigureJsonTesters because there isn't a Spring context
        JacksonTester.initFields(this, new ObjectMapper());
        // MockMvc standalone approach
        mvc = MockMvcBuilders.standaloneSetup(customerController)
                .setControllerAdvice(new GenericExceptionHandler())
                .build();
    }

    @Test
    void welcome() throws Exception{
        // init
        String expectedWelcomeMessage = "Welcome to Customer Service!";
        // when
        MockHttpServletResponse response = mvc.perform(get("/customer")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString().equals(expectedWelcomeMessage));
    }

    @Test
    void createCustomer() throws Exception {
        // init test values
        List<Customer> expectedCustomers= getCustomers();
        Customer expectedCustomer = new Customer();
        expectedCustomer.setFirstname("SampleCustomer");
        expectedCustomer.setLastname("lastname");
        expectedCustomer.setPhone("5345456678");
        expectedCustomer.setMonthlyIncome(9000);
        expectedCustomer.setNationalId("45643456578");
        expectedCustomers.add(expectedCustomer);

        // stub - given
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String expectedCustomerJsonStr = ow.writeValueAsString(expectedCustomer);
        Mockito.doNothing().when(customerService).addCustomer(expectedCustomer);

        MockHttpServletResponse response = mvc.perform(post("/customer/create")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expectedCustomerJsonStr))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        Mockito.verify(customerService, Mockito.times(1)).addCustomer(any());
    }

    private List<Customer> getCustomers() {
        // init test values
        List<Customer> customers = new ArrayList<>();
        Customer customer = new Customer(11, "12312340080", "Firstname", "Lastname", 7000, "05554443322", new ArrayList<>());
        CreditApplication expectedApplication = new CreditApplication(1,customer,false,0,new Date());
        Customer updatedCustomer = new Customer(11, "12312340080", "Firstname", "Lastname", 7000, "05554443322", Arrays.asList(expectedApplication));

        Customer customer2= new Customer(12,"12312340084", "Aleyna", "Zengin", 7000, "05554443322",null);
        CreditApplication expectedApplication2 = new CreditApplication(2,customer2,false,0,new Date());
        Customer updatedCustomer2 = new Customer(12, "12312340084", "Aleyna", "Zengin", 7000, "05554443322", Arrays.asList(expectedApplication2));

        Customer customer3= new Customer(13,"12312340086", "Elif", "Yılmaz", 7000, "05554443322",null);
        CreditApplication expectedApplication3 = new CreditApplication(3,customer3,false,0,new Date());
        Customer updatedCustomer3 = new Customer(13, "12312340086", "Aleyna", "Zengin", 7000, "05554443322", Arrays.asList(expectedApplication3));

        customers.add(customer);
        customers.add(customer2);
        customers.add(customer3);
        return customers;
    }

    @Test
    void updateCustomer() throws Exception {
        List<Customer> expectedCustomers= getCustomers();
        Customer expectedCustomer = new Customer();
        expectedCustomer.setFirstname("SampleCustomer");
        expectedCustomer.setLastname("lastname");
        expectedCustomer.setPhone("5345456678");
        expectedCustomer.setMonthlyIncome(9000);
        expectedCustomer.setNationalId("45643456578");
        expectedCustomers.add(expectedCustomer);

        // stub - given
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String expectedCustomerJsonStr = ow.writeValueAsString(expectedCustomer);
        when(customerService.updateCustomer(expectedCustomer)).thenReturn(expectedCustomer);

        MockHttpServletResponse response = mvc.perform(put("/customer/update")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expectedCustomerJsonStr))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        Mockito.verify(customerService, Mockito.times(1)).updateCustomer(any());
    }

    @Test
    void deleteCustomer() throws Exception {
        when(customerService.deleteCustomer(any())).thenReturn(true);

        MockHttpServletResponse response = mvc.perform(delete("/customer/delete/?id=5")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        String actualResponseStr = response.getContentAsString();
        Assert.assertEquals("true", actualResponseStr);
    }
}