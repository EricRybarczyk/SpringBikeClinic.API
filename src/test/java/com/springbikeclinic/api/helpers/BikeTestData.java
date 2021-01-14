package com.springbikeclinic.api.helpers;

import com.springbikeclinic.api.domain.Bike;
import com.springbikeclinic.api.domain.BikeType;
import com.springbikeclinic.api.dto.BikeDto;
import java.time.LocalDateTime;

public class BikeTestData {

    private static final String MANUFACTURER_NAME = "BIKE_COMPANY";
    private static final String BIKE_MODEL = "BIKE_MODEL";
    private static final int MODEL_YEAR = 2020;

    public static Bike generateBike() {
        Bike bike = new Bike();
        bike.setBikeType(LocalDateTime.now().getSecond() % 2 == 0 ? BikeType.MOUNTAIN : BikeType.ROAD);
        bike.setDescription(bike.getBikeType().toString().toLowerCase() + " bike");
        bike.setManufacturerName(MANUFACTURER_NAME);
        bike.setModelName(BIKE_MODEL);
        bike.setModelYear(MODEL_YEAR);
        return bike;
    }

    public static BikeDto generateBikeDto() {
        BikeDto bike = new BikeDto();
        bike.setBikeType(com.springbikeclinic.api.dto.BikeType.MOUNTAIN);
        bike.setDescription(bike.getBikeType().toString().toLowerCase() + " bike");
        bike.setManufacturerName(MANUFACTURER_NAME);
        bike.setModelName(BIKE_MODEL);
        bike.setModelYear(MODEL_YEAR);
        return bike;
    }
}
