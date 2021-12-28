package com.springbikeclinic.api.web.mappers;

import com.springbikeclinic.api.domain.WorkOrder;
import com.springbikeclinic.api.web.model.WorkOrderDto;
import com.springbikeclinic.api.web.model.WorkOrderRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper(uses = {DateMapper.class})
public interface WorkOrderMapper {

    // By convention, the interface declares a member INSTANCE, providing clients access to the mapper implementation.
    WorkOrderMapper INSTANCE = Mappers.getMapper(WorkOrderMapper.class);

    WorkOrderDto workOrderToWorkOrderDto(WorkOrder workOrder);
    WorkOrder workOrderDtoToWorkOrder(WorkOrderDto workOrderDto);
    WorkOrder workOrderRequestToWorkOrder(WorkOrderRequest workOrderRequest);
}
