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
        return mechanicRepository.findAll();
    }

    @Override
    public Mechanic getMechanicById(Long id) {
        return mechanicRepository.findById(id).orElseThrow( () -> new MechanicNotFoundException(String.format("Mechanic not found for ID %s", id)));
    }

    @Override
    public Long saveNewMechanic(Mechanic mechanic) {
        Mechanic savedMechanic = mechanicRepository.save(mechanic);
        return savedMechanic.getId();
    }

    @Override
    public Mechanic updateMechanic(Long id, Mechanic mechanic) {
        Mechanic existingMechanic = mechanicRepository.findById(id)
                .orElseThrow( () -> new MechanicNotFoundException(String.format("Mechanic not found for ID %s", id)));

        existingMechanic.setFirstName(mechanic.getFirstName());
        existingMechanic.setLastName(mechanic.getLastName());
        existingMechanic.setPhoneNumber(mechanic.getPhoneNumber());
        existingMechanic.setEmailAddress(mechanic.getEmailAddress());
        existingMechanic.setEmploymentStatus(mechanic.getEmploymentStatus());
        existingMechanic.setHireDate(mechanic.getHireDate());
        mechanicRepository.save(existingMechanic);
        return existingMechanic;
    }

}
