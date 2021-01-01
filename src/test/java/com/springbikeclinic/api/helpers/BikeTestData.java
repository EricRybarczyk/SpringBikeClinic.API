package com.springbikeclinic.api.helpers;

import com.github.javafaker.Faker;
import com.springbikeclinic.api.domain.Bike;
import com.springbikeclinic.api.domain.BikeType;
import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

public class BikeTestData {

    private static final Faker faker = new Faker();

    public static Bike generateBike() {
        Bike bike = new Bike();
        bike.setBikeType(LocalDateTime.now().getSecond() % 2 == 0 ? BikeType.MOUNTAIN : BikeType.ROAD);
        bike.setDescription(bike.getBikeType().toString().toLowerCase() + " bike");
        bike.setManufacturerName(faker.starTrek().specie());
        bike.setModelName(faker.space().constellation());
        bike.setModelYear(ThreadLocalRandom.current().nextInt(2003, 2021));
        return bike;
    }
}
