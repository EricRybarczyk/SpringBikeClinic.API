package com.springbikeclinic.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MechanicDto extends PersonDto {
    private LocalDate hireDate;
    private EmploymentStatus employmentStatus;
}
