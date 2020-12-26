package com.springbikeclinic.api.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends Person {

    private LocalDate createdDate;
    private Set<Bike> bikes = new HashSet<>();
}
