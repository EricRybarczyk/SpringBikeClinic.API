package com.springbikeclinic.api.services;

import com.springbikeclinic.api.domain.Customer;
import com.springbikeclinic.api.repositories.CustomerRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElseThrow( () -> new CustomerNotFoundException(String.format("Customer not found for ID %s", id)));
    }

    @Override
    public Long saveNewCustomer(Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);
        return savedCustomer.getId();
    }

}
