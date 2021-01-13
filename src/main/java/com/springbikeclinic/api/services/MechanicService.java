package com.springbikeclinic.api.services;

import com.springbikeclinic.api.dto.MechanicDto;
import java.util.List;

public interface MechanicService {

    List<MechanicDto> getAllMechanics();

    MechanicDto getMechanicById(Long id);

    Long saveNewMechanic(MechanicDto mechanic);

    MechanicDto updateMechanic(Long id, MechanicDto mechanic);

}
