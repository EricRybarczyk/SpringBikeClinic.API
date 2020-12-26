package com.springbikeclinic.api.repositories;

import com.springbikeclinic.api.domain.Mechanic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MechanicRepository extends JpaRepository<Mechanic, Long> {

}
