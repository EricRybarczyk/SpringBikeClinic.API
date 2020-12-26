package com.springbikeclinic.api.services;

import com.springbikeclinic.api.domain.Customer;
import java.util.List;

public interface CustomerService {

    List<Customer> getAllCustomers();

    Customer getCustomerById(Long id);

}
