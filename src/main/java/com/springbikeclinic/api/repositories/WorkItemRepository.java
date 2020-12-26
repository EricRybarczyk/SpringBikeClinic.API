package com.springbikeclinic.api.repositories;

import com.springbikeclinic.api.domain.WorkItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkItemRepository extends JpaRepository<WorkItem, Long> {

}
