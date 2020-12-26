package com.springbikeclinic.api.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkOrder {

    private Long id;
    private LocalDateTime createdDateTime;
    private LocalDateTime submittedDateTime;
    private WorkOrderStatus status;

    private Customer customer;
    private Bike bike;
    private Mechanic mechanicAssigned;

    private LocalDate estimatedCompletionDate;
    private LocalDate actualCompletionDate;
    private String customerNotes;
    private String mechanicNotes;
    private Set<WorkItem> workItems = new HashSet<>();
}
