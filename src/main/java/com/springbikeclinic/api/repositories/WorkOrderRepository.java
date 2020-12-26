package com.springbikeclinic.api.repositories;

import com.springbikeclinic.api.domain.WorkOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkOrderRepository extends JpaRepository<WorkOrder, Long> {

}
