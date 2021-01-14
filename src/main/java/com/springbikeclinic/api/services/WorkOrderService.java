package com.springbikeclinic.api.services;

import com.springbikeclinic.api.dto.WorkOrderDto;
import org.springframework.stereotype.Service;

@Service
public interface WorkOrderService {

    //List<WorkOrder> getOpenWorkOrders();

    WorkOrderDto getWorkOrderById(Long id);

    Long save(WorkOrderDto workOrder);
}
