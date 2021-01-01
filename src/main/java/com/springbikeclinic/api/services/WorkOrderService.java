package com.springbikeclinic.api.services;

import com.springbikeclinic.api.domain.WorkOrder;
import org.springframework.stereotype.Service;

@Service
public interface WorkOrderService {

    //List<WorkOrder> getOpenWorkOrders();

    WorkOrder getWorkOrderById(Long id);
}
