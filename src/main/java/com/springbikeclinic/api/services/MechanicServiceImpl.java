package com.springbikeclinic.api.services;

import com.springbikeclinic.api.domain.Mechanic;
import com.springbikeclinic.api.dto.MechanicDto;
import com.springbikeclinic.api.mappers.MechanicMapper;
import com.springbikeclinic.api.repositories.MechanicRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MechanicServiceImpl implements MechanicService {

    private final MechanicRepository mechanicRepository;
    private final MechanicMapper mechanicMapper;

    public MechanicServiceImpl(MechanicRepository mechanicRepository, MechanicMapper mechanicMapper) {
        this.mechanicRepository = mechanicRepository;
        this.mechanicMapper = mechanicMapper;
    }

    @Override
    public List<MechanicDto> getAllMechanics() {
        List<Mechanic> mechanics = mechanicRepository.findAll();
        return mechanics.stream()
                .map(mechanicMapper::mechanicToMechanicDto)
                .collect(Collectors.toList());
    }

    @Override
    public MechanicDto getMechanicById(Long id) {
        Mechanic mechanic = mechanicRepository.findById(id)
                .orElseThrow(() -> new MechanicNotFoundException(String.format("Mechanic not found for ID %s", id)));
        return mechanicMapper.mechanicToMechanicDto(mechanic);
    }

    @Override
    public Long saveNewMechanic(MechanicDto mechanic) {
        Mechanic savedMechanic = mechanicRepository.save(mechanicMapper.mechanicDtoToMechanic(mechanic));
        return savedMechanic.getId();
    }

    @Override
    public MechanicDto updateMechanic(Long id, MechanicDto mechanic) {
        Mechanic existingMechanic = mechanicRepository.findById(id)
                .orElseThrow( () -> new MechanicNotFoundException(String.format("Mechanic not found for ID %s", id)));
        existingMechanic.setFirstName(mechanic.getFirstName());
        existingMechanic.setLastName(mechanic.getLastName());
        existingMechanic.setPhoneNumber(mechanic.getPhoneNumber());
        existingMechanic.setEmailAddress(mechanic.getEmailAddress());
        existingMechanic.setHireDate(mechanic.getHireDate());
        mechanicRepository.save(existingMechanic);
        return mechanicMapper.mechanicToMechanicDto(existingMechanic);
    }

}
