package com.springbikeclinic.api.controllers;

import com.springbikeclinic.api.dto.CustomerDto;
import com.springbikeclinic.api.services.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @PostMapping
    public ResponseEntity<Void> createNewCustomer(@Valid @RequestBody CustomerDto customer, UriComponentsBuilder uriComponentsBuilder) {
        Long customerId = customerService.saveNewCustomer(customer);

        URI uri = uriComponentsBuilder.path("/api/customers/{customerId}").buildAndExpand(customerId).toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDto> updateExistingCustomer(@PathVariable("id") Long id, @Valid @RequestBody CustomerDto customer) {
        CustomerDto updatedCustomer = customerService.updateCustomer(id, customer);
        return ResponseEntity.ok(updatedCustomer);
    }
}
