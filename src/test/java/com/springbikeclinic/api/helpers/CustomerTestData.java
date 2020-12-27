package com.springbikeclinic.api.helpers;

import com.github.javafaker.Faker;
import com.springbikeclinic.api.commands.CustomerCommand;
import com.springbikeclinic.api.domain.Customer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class CustomerTestData {

    private static final Faker faker = new Faker();

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
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        int daysBack = ThreadLocalRandom.current().nextInt(30, 180);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmailAddress(String.format("%s.%s@domain.com", firstName, lastName));
        customer.setPhoneNumber(faker.phoneNumber().cellPhone());
        customer.setCreatedDate(LocalDate.now().minusDays(daysBack));
        return customer;
    }

    public static CustomerCommand getNewCustomerCommand() {
        CustomerCommand customer = new CustomerCommand();
        customer.setFirstName("Firsty");
        customer.setLastName("Lasty");
        customer.setEmailAddress("FirstyLasty@domain.com");
        customer.setPhoneNumber("4045551212");
        customer.setCreatedDate(LocalDate.now().minusDays(1));
        return customer;
    }
}
