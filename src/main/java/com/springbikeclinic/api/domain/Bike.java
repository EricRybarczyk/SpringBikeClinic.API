package com.springbikeclinic.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Enumerated(value = EnumType.STRING)
    private BikeType bikeType;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer owner;

    @JsonIgnore
    @OneToMany(mappedBy = "bike", fetch = FetchType.LAZY)
    private Set<WorkOrder> workOrders = new HashSet<>();

    // TODO: consider that the remaining fields may need to be optional/nullable or contain default values that would indicate unspecified
    private Integer modelYear;
    private String manufacturerName;
    private String modelName;
}
