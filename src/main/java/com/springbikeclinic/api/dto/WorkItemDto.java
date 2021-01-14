package com.springbikeclinic.api.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class WorkItemDto {
    private Long id;
    private LocalDateTime createdDateTime;
    private LocalDateTime completedDateTime;
    private WorkItemStatus status;
    private String description;
    private String mechanicNotes;
}
