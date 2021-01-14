package com.springbikeclinic.api.mappers;

import com.springbikeclinic.api.domain.WorkOrder;
import com.springbikeclinic.api.dto.WorkOrderDto;
import org.mapstruct.Mapper;


@Mapper
public interface WorkOrderMapper {

    WorkOrder workOrderDtoToWorkOrder(WorkOrderDto workOrderDto);

    WorkOrderDto workOrderToWorkOrderDto(WorkOrder workOrder);

}
