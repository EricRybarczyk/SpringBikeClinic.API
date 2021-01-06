package com.springbikeclinic.api.services;

import com.springbikeclinic.api.domain.WorkOrder;
import com.springbikeclinic.api.repositories.WorkOrderRepository;
import org.springframework.stereotype.Service;

@Service
public class WorkOrderServiceImpl implements WorkOrderService {

    private final WorkOrderRepository workOrderRepository;

    public WorkOrderServiceImpl(WorkOrderRepository workOrderRepository) {
        this.workOrderRepository = workOrderRepository;
    }

    @Override
    public WorkOrder getWorkOrderById(Long id) {
        return workOrderRepository.findById(id)
                .orElseThrow( () -> new WorkOrderNotFoundException(String.format("Work Order %s was not found", id)));
    }

    @Override
    public Long save(WorkOrder workOrder) {
        WorkOrder savedWorkOrder = workOrderRepository.save(workOrder);
        return savedWorkOrder.getId();
    }

}
