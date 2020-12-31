package com.springbikeclinic.api.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "mechanics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Mechanic extends Person {

    private LocalDate hireDate;

    @Enumerated(value = EnumType.STRING)
    private EmploymentStatus employmentStatus;

    @OneToMany(mappedBy = "mechanic", fetch = FetchType.LAZY)
    private Set<WorkOrder> workOrders = new HashSet<>();
}
