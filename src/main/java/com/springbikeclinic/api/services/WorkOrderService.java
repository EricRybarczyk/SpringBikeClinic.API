package com.springbikeclinic.api.services;

import com.springbikeclinic.api.web.model.WorkOrderDto;
import com.springbikeclinic.api.web.model.WorkOrderRequest;
import java.util.List;
import java.util.Map;


public interface WorkOrderService {
    WorkOrderDto getById(Long id);
    List<WorkOrderDto> getByStatus(String status);
    Long createWorkOrder(WorkOrderRequest workOrderRequest);
    WorkOrderDto updateWorkOrder(Long id, WorkOrderDto workOrderDto);
    WorkOrderDto patchWorkOrder(Long id, Map<String, Object> patch);
    void deleteWorkOrder(Long id);
}
