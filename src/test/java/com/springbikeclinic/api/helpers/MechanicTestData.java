package com.springbikeclinic.api.helpers;

import com.github.javafaker.Faker;
import com.springbikeclinic.api.domain.EmploymentStatus;
import com.springbikeclinic.api.domain.Mechanic;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MechanicTestData {

    private static final Faker faker = new Faker();

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
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        int daysBack = ThreadLocalRandom.current().nextInt(90, 730);
        mechanic.setFirstName(firstName);
        mechanic.setLastName(lastName);
        mechanic.setEmailAddress(String.format("%s.%s@domain.com", firstName, lastName));
        mechanic.setPhoneNumber(faker.phoneNumber().cellPhone());
        mechanic.setHireDate(LocalDate.now().minusDays(daysBack));
        mechanic.setEmploymentStatus(EmploymentStatus.ACTIVE);
        return mechanic;
    }
}
