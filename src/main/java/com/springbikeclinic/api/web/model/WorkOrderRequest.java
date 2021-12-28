package com.springbikeclinic.api.web.model;

import lombok.Builder;
import lombok.Data;
import javax.validation.constraints.NotEmpty;


@Data
@Builder
public class WorkOrderRequest {

    @NotEmpty
    private String bikeDescription;

    @NotEmpty
    private String workDescription;

    @NotEmpty
    private String status;

    private String mechanicNotes;
}
