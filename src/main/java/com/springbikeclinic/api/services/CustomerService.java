package com.springbikeclinic.api.services;

import com.springbikeclinic.api.dto.CustomerDto;
import java.util.List;

public interface CustomerService {

    List<CustomerDto> getAllCustomers();

    CustomerDto getCustomerById(Long id);

    Long saveNewCustomer(CustomerDto customer);

    CustomerDto updateCustomer(Long id, CustomerDto customer);

}
