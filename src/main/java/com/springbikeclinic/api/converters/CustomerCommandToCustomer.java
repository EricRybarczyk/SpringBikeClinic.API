package com.springbikeclinic.api.converters;

import com.springbikeclinic.api.commands.CustomerCommand;
import com.springbikeclinic.api.domain.Customer;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CustomerCommandToCustomer implements Converter<CustomerCommand, Customer> {

    @Override
    public Customer convert(CustomerCommand source) {
        Customer customer = new Customer();
        customer.setId(source.getId());
        customer.setFirstName(source.getFirstName());
        customer.setLastName(source.getLastName());
        customer.setPhoneNumber(source.getPhoneNumber());
        customer.setEmailAddress(source.getEmailAddress());
        customer.setCreatedDate(source.getCreatedDate());
        return customer;
    }

}
