package com.springbikeclinic.api.services;

import com.springbikeclinic.api.domain.Mechanic;
import java.util.List;

public interface MechanicService {

    List<Mechanic> getAllMechanics();

    Mechanic getMechanicById(Long id);

    Long saveNewMechanic(Mechanic mechanic);

    Mechanic updateMechanic(Long id, Mechanic mechanic);

}
