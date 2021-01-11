package com.springbikeclinic.api.mappers;

import com.springbikeclinic.api.domain.Customer;
import com.springbikeclinic.api.dto.CustomerDto;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {

    Customer customerDtoToCustomer(CustomerDto customerDto);

    CustomerDto customerToCustomerDto(Customer customer);
}
