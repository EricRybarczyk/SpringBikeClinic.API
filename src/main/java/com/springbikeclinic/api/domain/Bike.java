package com.springbikeclinic.api.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bike {

    private Long id;
    private String description;
    private BikeType bikeType;
    private Customer owner;

    // TODO: consider that the remaining fields may need to be optional/nullable or contain default values that would indicate unspecified
    private Integer modelYear;
    private String manufacturerName;
    private String modelName;
}
