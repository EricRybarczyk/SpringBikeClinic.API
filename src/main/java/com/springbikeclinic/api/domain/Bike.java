package com.springbikeclinic.api.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "bikes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bike {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String description;
    private BikeType bikeType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer owner;

    @OneToMany(mappedBy = "bike")
    private Set<WorkOrder> workOrders = new HashSet<>();

    // TODO: consider that the remaining fields may need to be optional/nullable or contain default values that would indicate unspecified
    private Integer modelYear;
    private String manufacturerName;
    private String modelName;
}
