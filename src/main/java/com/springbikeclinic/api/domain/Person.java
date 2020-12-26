package com.springbikeclinic.api.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Person {private String firstName;

    private Long id;
    private String lastName;
    private String emailAddress;
    private String phoneNumber;

    public boolean isNew() {
        return this.id == null;
    }

}
