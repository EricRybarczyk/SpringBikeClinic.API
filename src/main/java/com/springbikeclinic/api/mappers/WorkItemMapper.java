package com.springbikeclinic.api.mappers;

import com.springbikeclinic.api.domain.WorkItem;
import com.springbikeclinic.api.dto.WorkItemDto;
import org.mapstruct.Mapper;


@Mapper
public interface WorkItemMapper {

    WorkItemDto workItemToWorkItemDto(WorkItem workItem);

    WorkItem workItemDtoToWorkItem(WorkItemDto workItemDto);
}
