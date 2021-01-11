package com.springbikeclinic.api.mappers;

import com.springbikeclinic.api.domain.Bike;
import com.springbikeclinic.api.dto.BikeDto;
import org.mapstruct.Mapper;

@Mapper
public interface BikeMapper {

    BikeDto bikeToBikeDto(Bike bike);

    Bike bikeDtoToBike(BikeDto bikeDto);

}
