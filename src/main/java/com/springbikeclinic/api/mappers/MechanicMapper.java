package com.springbikeclinic.api.mappers;

import com.springbikeclinic.api.domain.Mechanic;
import com.springbikeclinic.api.dto.MechanicDto;
import org.mapstruct.Mapper;

@Mapper
public interface MechanicMapper {

    Mechanic mechanicDtoToMechanic(MechanicDto mechanicDto);

    MechanicDto mechanicToMechanicDto(Mechanic mechanic);
}
