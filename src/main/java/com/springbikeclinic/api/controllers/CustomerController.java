package com.springbikeclinic.api.controllers;

import com.springbikeclinic.api.commands.CustomerCommand;
import com.springbikeclinic.api.converters.CustomerCommandToCustomer;
import com.springbikeclinic.api.domain.Customer;
import com.springbikeclinic.api.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerCommandToCustomer customerCommandToCustomer;

    public CustomerController(CustomerService customerService, CustomerCommandToCustomer customerCommandToCustomer) {
        this.customerService = customerService;
        this.customerCommandToCustomer = customerCommandToCustomer;
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @PostMapping
    public ResponseEntity<Void> createNewCustomer(@Valid @RequestBody CustomerCommand customerCommand, UriComponentsBuilder uriComponentsBuilder) {
        Long customerId = customerService.saveNewCustomer(customerCommandToCustomer.convert(customerCommand));

        URI uri = uriComponentsBuilder.path("/api/customers/{customerId}").buildAndExpand(customerId).toUri();

        return ResponseEntity.created(uri).build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach( (error) -> {
            String field = ((FieldError) error).getField();
            String validationMessage = error.getDefaultMessage();
            errors.put(field, validationMessage);
        });
        return errors;
    }
}
