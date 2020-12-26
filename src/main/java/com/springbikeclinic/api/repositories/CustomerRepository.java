package com.springbikeclinic.api.repositories;

import com.springbikeclinic.api.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
