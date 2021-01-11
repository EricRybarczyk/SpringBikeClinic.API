package com.springbikeclinic.api.services;

import com.springbikeclinic.api.domain.Customer;
import com.springbikeclinic.api.dto.CustomerDto;
import com.springbikeclinic.api.helpers.CustomerTestData;
import com.springbikeclinic.api.mappers.CustomerMapper;
import com.springbikeclinic.api.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerServiceImpl customerService;


    @Test
    void getAllCustomersReturnsTwoCustomers() throws Exception {
        List<Customer> customerList = CustomerTestData.getCustomerList(2);
        when(customerRepository.findAll()).thenReturn(customerList);

        List<CustomerDto> result = customerService.getAllCustomers();

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);

        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void getAllCustomersWithEmptyRepositoryReturnsEmptyList() throws Exception {
        when(customerRepository.findAll()).thenReturn(new ArrayList<>());

        List<CustomerDto> result = customerService.getAllCustomers();

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(0);

        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void getCustomerWithIdOneReturnsValidCustomer() throws Exception {
        Customer customer = CustomerTestData.generateCustomer();
        customer.setId(1L);
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        CustomerDto customerDto = CustomerTestData.generateCustomerDto();
        customerDto.setId(1L);
        when(customerMapper.customerToCustomerDto(any(Customer.class))).thenReturn(customerDto);

        CustomerDto result = customerService.getCustomerById(1L);

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

    @Test
    void saveValidNewCustomerReturnsCustomerId() throws Exception {
        Customer savedCustomer = CustomerTestData.generateCustomer();
        savedCustomer.setId(99L);
        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);
        when(customerMapper.customerDtoToCustomer(any(CustomerDto.class))).thenReturn(savedCustomer);

        CustomerDto newCustomerDto = CustomerTestData.generateCustomerDto();
        Long savedCustomerId = customerService.saveNewCustomer(newCustomerDto);

        assertThat(savedCustomerId).isNotNull().isEqualTo(99L);
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void updateValidExistingCustomerReturnsUpdatedCustomerObject() throws Exception {
        Customer customer = CustomerTestData.generateCustomer();
        customer.setId(1L);
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        String modifiedFirstName = "NewFirst";
        String modifiedLastName = "NewLast";
        String modifiedEmail = "NewEmail@domain.com";
        String modifiedPhone = "3334445566";
        CustomerDto modifiedCustomer = new CustomerDto();
        modifiedCustomer.setFirstName(modifiedFirstName);
        modifiedCustomer.setLastName(modifiedLastName);
        modifiedCustomer.setEmailAddress(modifiedEmail);
        modifiedCustomer.setPhoneNumber(modifiedPhone);

        // need to mock the CustomerMapper so the Id values match (along with other fields)
        CustomerDto mappedSavedCustomer = new CustomerDto();
        mappedSavedCustomer.setId(1L);
        mappedSavedCustomer.setFirstName(modifiedFirstName);
        mappedSavedCustomer.setLastName(modifiedLastName);
        mappedSavedCustomer.setEmailAddress(modifiedEmail);
        mappedSavedCustomer.setPhoneNumber(modifiedPhone);
        when(customerMapper.customerToCustomerDto(any(Customer.class))).thenReturn(mappedSavedCustomer);

        CustomerDto updatedCustomer = customerService.updateCustomer(1L, modifiedCustomer);

        assertThat(updatedCustomer.getId()).isEqualTo(1L);
        assertThat(updatedCustomer.getFirstName()).isEqualTo(modifiedFirstName);
        assertThat(updatedCustomer.getLastName()).isEqualTo(modifiedLastName);
        assertThat(updatedCustomer.getEmailAddress()).isEqualTo(modifiedEmail);
        assertThat(updatedCustomer.getPhoneNumber()).isEqualTo(modifiedPhone);

        verify(customerRepository, times(1)).findById(customer.getId());
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

}
