package com.springbikeclinic.api.controllers;

import com.springbikeclinic.api.domain.Customer;
import com.springbikeclinic.api.helpers.CustomerTestData;
import com.springbikeclinic.api.services.CustomerNotFoundException;
import com.springbikeclinic.api.services.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class CustomerControllerTest {

    private static final String API_BASE_PATH = "/api/customers/";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;


    @Test
    void allCustomersEndpointShouldReturnTwoCustomers() throws Exception {
        List<Customer> customerList = CustomerTestData.getCustomerList(2);
        when(customerService.getAllCustomers()).thenReturn(customerList);

        mockMvc.perform(get(API_BASE_PATH))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName", is(customerList.get(0).getFirstName())))
                .andExpect(jsonPath("$[0].lastName", is(customerList.get(0).getLastName())))
                .andExpect(jsonPath("$[0].phoneNumber", is(customerList.get(0).getPhoneNumber())))
                .andExpect(jsonPath("$[0].emailAddress", is(customerList.get(0).getEmailAddress())));

        verify(customerService, times(1)).getAllCustomers();
    }

    @Test
    void getCustomerWithIdOneShouldReturnValidCustomer() throws Exception {
        Customer customer = CustomerTestData.generateCustomer();
        customer.setId(1L);
        when(customerService.getCustomerById(1L)).thenReturn(customer);

        mockMvc.perform(get(API_BASE_PATH + "1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(customer.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(customer.getLastName())))
                .andExpect(jsonPath("$.phoneNumber", is(customer.getPhoneNumber())))
                .andExpect(jsonPath("$.emailAddress", is(customer.getEmailAddress())));

        verify(customerService, times(1)).getCustomerById(1L);
    }

    @Test
    void getCustomerWithInvalidIdShouldReturn404() throws Exception {
        when(customerService.getCustomerById(anyLong())).thenThrow(new CustomerNotFoundException("Customer not found."));

        mockMvc.perform(get(API_BASE_PATH + "9999"))
                .andExpect(status().isNotFound());

        verify(customerService, times(1)).getCustomerById(9999L);
    }

}
