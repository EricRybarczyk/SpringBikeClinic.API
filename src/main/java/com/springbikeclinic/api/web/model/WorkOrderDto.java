package com.springbikeclinic.api.web.model;

import lombok.Builder;
import lombok.Data;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;


@Data
@Builder
public class WorkOrderDto {

    @NotNull
    private Long id;

    @NotNull
    private OffsetDateTime createdDateTime;

    @NotEmpty
    private String bikeDescription;

    @NotEmpty
    private String workDescription;

    @NotEmpty
    private String status;

    private String mechanicNotes;

}
