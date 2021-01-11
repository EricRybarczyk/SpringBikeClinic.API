package com.springbikeclinic.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BikeDto {
    private Long id;
    private String description;
    private BikeType bikeType;
    private CustomerDto customer;
    private Integer modelYear;
    private String manufacturerName;
    private String modelName;
}
