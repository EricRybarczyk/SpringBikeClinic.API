package com.springbikeclinic.api.services;

import com.springbikeclinic.api.domain.Customer;
import com.springbikeclinic.api.dto.CustomerDto;
import com.springbikeclinic.api.mappers.CustomerMapper;
import com.springbikeclinic.api.repositories.CustomerRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        List<Customer> customerList = customerRepository.findAll();
        return customerList.stream()
                .map(customerMapper::customerToCustomerDto)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDto getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(String.format("Customer not found for ID %s", id)));
        return customerMapper.customerToCustomerDto(customer);
    }

    @Override
    public Long saveNewCustomer(CustomerDto customer) {
        Customer savedCustomer = customerRepository.save(customerMapper.customerDtoToCustomer(customer));
        return savedCustomer.getId();
    }

    @Override
    public CustomerDto updateCustomer(Long id, CustomerDto customer) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(String.format("Customer not found for ID %s", id)));
        existingCustomer.setFirstName(customer.getFirstName());
        existingCustomer.setLastName(customer.getLastName());
        existingCustomer.setEmailAddress(customer.getEmailAddress());
        existingCustomer.setPhoneNumber(customer.getPhoneNumber());
        customerRepository.save(existingCustomer);
        return customerMapper.customerToCustomerDto(existingCustomer);
    }

}
