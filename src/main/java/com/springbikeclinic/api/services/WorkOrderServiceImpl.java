package com.springbikeclinic.api.services;

import com.springbikeclinic.api.domain.WorkOrder;
import com.springbikeclinic.api.dto.WorkOrderDto;
import com.springbikeclinic.api.mappers.WorkOrderMapper;
import com.springbikeclinic.api.repositories.WorkOrderRepository;
import org.springframework.stereotype.Service;

@Service
public class WorkOrderServiceImpl implements WorkOrderService {

    private final WorkOrderRepository workOrderRepository;
    private final WorkOrderMapper workOrderMapper;

    public WorkOrderServiceImpl(WorkOrderRepository workOrderRepository, WorkOrderMapper workOrderMapper) {
        this.workOrderRepository = workOrderRepository;
        this.workOrderMapper = workOrderMapper;
    }

    @Override
    public WorkOrderDto getWorkOrderById(Long id) {
        WorkOrder workOrder = workOrderRepository.findById(id)
                .orElseThrow( () -> new WorkOrderNotFoundException(String.format("Work Order %s was not found", id)));
        return workOrderMapper.workOrderToWorkOrderDto(workOrder);
    }

    @Override
    public Long save(WorkOrderDto workOrder) {
        WorkOrder savedWorkOrder = workOrderRepository.save(workOrderMapper.workOrderDtoToWorkOrder(workOrder));
        return savedWorkOrder.getId();
    }

}
