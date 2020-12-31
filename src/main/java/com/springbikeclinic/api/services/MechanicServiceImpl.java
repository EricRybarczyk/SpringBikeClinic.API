package com.springbikeclinic.api.services;

import com.springbikeclinic.api.domain.Mechanic;
import com.springbikeclinic.api.repositories.MechanicRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MechanicServiceImpl implements MechanicService {

    private final MechanicRepository mechanicRepository;

    public MechanicServiceImpl(MechanicRepository mechanicRepository) {
        this.mechanicRepository = mechanicRepository;
    }

    @Override
    public List<Mechanic> getAllMechanics() {
        return null;
    }

    @Override
    public Mechanic getMechanicById(Long id) {
        return null;
    }

    @Override
    public Long saveNewMechanic(Mechanic mechanic) {
        return null;
    }

    @Override
    public Mechanic updateMechanic(Long id, Mechanic mechanic) {
        return null;
    }

}
