package com.springbikeclinic.api.services;

import com.springbikeclinic.api.domain.Customer;
import com.springbikeclinic.api.helpers.CustomerTestData;
import com.springbikeclinic.api.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;


    @Test
    void getAllCustomersReturnsTwoCustomers() throws Exception {
        List<Customer> customerList = CustomerTestData.getCustomerList(2);
        when(customerRepository.findAll()).thenReturn(customerList);

        List<Customer> result = customerService.getAllCustomers();

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);

        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void getCustomerWithIdOneReturnsValidCustomer() throws Exception {
        Customer customer = CustomerTestData.generateCustomer();
        customer.setId(1L);
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        Customer result = customerService.getCustomerById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getFirstName()).isEqualTo(customer.getFirstName());

        verify(customerRepository, times(1)).findById(anyLong());
    }

    @Test
    void getCustomerWithInvalidIdShouldThrowCustomerNotFoundException() throws Exception {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> customerService.getCustomerById(999L));
    }

}
