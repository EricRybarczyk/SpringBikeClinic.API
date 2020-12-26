package com.springbikeclinic.api.repositories;

import com.springbikeclinic.api.domain.Bike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BikeRepository extends JpaRepository<Bike, Long> {

}
