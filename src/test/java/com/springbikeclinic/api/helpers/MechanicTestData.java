package com.springbikeclinic.api.helpers;

import com.springbikeclinic.api.domain.EmploymentStatus;
import com.springbikeclinic.api.domain.Mechanic;
import com.springbikeclinic.api.dto.MechanicDto;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MechanicTestData {

    public static final String FIRST_NAME = "Tooley";
    public static final String LAST_NAME = "McToolface";
    public static final String PHONE_NUMBER = "6165551212";

    public static List<Mechanic> getMechanicList(int size) {
        if (size < 1) {
            throw new IllegalArgumentException("size must be positive");
        }
        List<Mechanic> result = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            result.add(generateMechanic());
        }
        return result;
    }

    public static Mechanic generateMechanic() {
        Mechanic mechanic = new Mechanic();
        String firstName = FIRST_NAME;
        String lastName = LAST_NAME;
        int daysBack = ThreadLocalRandom.current().nextInt(90, 730);
        mechanic.setFirstName(firstName);
        mechanic.setLastName(lastName);
        mechanic.setEmailAddress(String.format("%s.%s@domain.com", firstName, lastName));
        mechanic.setPhoneNumber(PHONE_NUMBER);
        mechanic.setHireDate(LocalDate.now().minusDays(daysBack));
        mechanic.setEmploymentStatus(EmploymentStatus.ACTIVE);
        return mechanic;
    }

    public static MechanicDto generateMechanicDto() {
        MechanicDto mechanic = new MechanicDto();
        String firstName = FIRST_NAME;
        String lastName = LAST_NAME;
        int daysBack = ThreadLocalRandom.current().nextInt(90, 730);
        mechanic.setFirstName(firstName);
        mechanic.setLastName(lastName);
        mechanic.setEmailAddress(String.format("%s.%s@domain.com", firstName, lastName));
        mechanic.setPhoneNumber(PHONE_NUMBER);
        mechanic.setHireDate(LocalDate.now().minusDays(daysBack));
        mechanic.setEmploymentStatus(com.springbikeclinic.api.dto.EmploymentStatus.ACTIVE);
        return mechanic;
    }

    public static List<MechanicDto> getMechanicDtoList(int size) {
        if (size < 1) {
            throw new IllegalArgumentException("size must be positive");
        }
        List<MechanicDto> result = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            result.add(generateMechanicDto());
        }
        return result;
    }
}
