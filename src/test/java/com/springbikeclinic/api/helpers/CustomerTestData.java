package com.springbikeclinic.api.helpers;

import com.springbikeclinic.api.domain.Customer;
import com.springbikeclinic.api.dto.CustomerDto;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CustomerTestData {

    public static final String FIRST_NAME = "Firsty";
    public static final String LAST_NAME = "Lasty";
    public static final String PHONE_NUMBER = "6165551212";

    public static List<Customer> getCustomerList(int size) {
        if (size < 1) {
            throw new IllegalArgumentException("size must be positive");
        }
        List<Customer> customers = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            customers.add(generateCustomer());
        }
        return customers;
    }

    public static Customer generateCustomer() {
        Customer customer = new Customer();
        String firstName = FIRST_NAME;
        String lastName = LAST_NAME;
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmailAddress(String.format("%s.%s@domain.com", firstName, lastName));
        customer.setPhoneNumber(PHONE_NUMBER);
        customer.setCreatedDate(LocalDate.now().minusDays(425));
        return customer;
    }

    public static CustomerDto generateCustomerDto() {
        CustomerDto customer = new CustomerDto();
        String firstName = FIRST_NAME;
        String lastName = LAST_NAME;
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmailAddress(String.format("%s.%s@domain.com", firstName, lastName));
        customer.setPhoneNumber(PHONE_NUMBER);
        return customer;
    }

    public static List<CustomerDto> getCustomerDtoList(int size) {
        if (size < 1) {
            throw new IllegalArgumentException("size must be positive");
        }
        List<CustomerDto> customers = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            customers.add(generateCustomerDto());
        }
        return customers;
    }

}
