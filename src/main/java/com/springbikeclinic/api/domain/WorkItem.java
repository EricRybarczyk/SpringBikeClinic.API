package com.springbikeclinic.api.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkItem {

    private Long id;
    private WorkOrder workOrder;
    private LocalDateTime createdDateTime;
    private LocalDateTime completedDateTime;
    private WorkItemStatus workItemStatus;
    private String mechanicNotes;

}
