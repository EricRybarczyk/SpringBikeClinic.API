package com.springbikeclinic.api.services;

import com.springbikeclinic.api.domain.Mechanic;
import com.springbikeclinic.api.dto.EmploymentStatus;
import com.springbikeclinic.api.dto.MechanicDto;
import com.springbikeclinic.api.helpers.MechanicTestData;
import com.springbikeclinic.api.mappers.MechanicMapper;
import com.springbikeclinic.api.repositories.MechanicRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MechanicServiceTest {

    @Mock
    private MechanicRepository mechanicRepository;

    @Mock
    private MechanicMapper mechanicMapper;

    @InjectMocks
    private MechanicServiceImpl mechanicService;

    @Test
    void getAllMechanicsReturnsTwoMechanics() throws Exception {
        List<Mechanic> mechanicList = MechanicTestData.getMechanicList(2);
        when(mechanicRepository.findAll()).thenReturn(mechanicList);

        List<MechanicDto> result = mechanicService.getAllMechanics();

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);

        verify(mechanicRepository, times(1)).findAll();
    }

    @Test
    void getAllMechanicsWithEmptyRepositoryReturnsEmptyList() throws Exception {
        when(mechanicRepository.findAll()).thenReturn(new ArrayList<>());

        // purpose is to make sure the mapping logic in the Service method call works even with an empty source list
        List<MechanicDto> result = mechanicService.getAllMechanics();

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(0);

        verify(mechanicRepository, times(1)).findAll();
    }

    @Test
    void getMechanicWithIdOneReturnsValidMechanic() throws Exception {
        Mechanic mechanic = MechanicTestData.generateMechanic();
        mechanic.setId(1L);
        when(mechanicRepository.findById(1L)).thenReturn(Optional.of(mechanic));

        MechanicDto mechanicDto = MechanicTestData.generateMechanicDto();
        mechanicDto.setId(1L);
        when(mechanicMapper.mechanicToMechanicDto(any(Mechanic.class))).thenReturn(mechanicDto);

        MechanicDto result = mechanicService.getMechanicById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getFirstName()).isEqualTo(mechanic.getFirstName());

        verify(mechanicRepository, times(1)).findById(1L);
    }

    @Test
    void getMechanicWithInvalidIdShouldThrowMechanicNotFoundException() throws Exception {
        when(mechanicRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(MechanicNotFoundException.class, () -> mechanicService.getMechanicById(5150L));
    }

    @Test
    void saveValidNewMechanicReturnsMechanicId() throws Exception {
        Mechanic savedMechanic = MechanicTestData.generateMechanic();
        savedMechanic.setId(99L);
        when(mechanicRepository.save(any(Mechanic.class))).thenReturn(savedMechanic);
        when(mechanicMapper.mechanicDtoToMechanic(any(MechanicDto.class))).thenReturn(savedMechanic);

        MechanicDto newMechanic = MechanicTestData.generateMechanicDto();
        Long newMechanicId = mechanicService.saveNewMechanic(newMechanic);

        assertThat(newMechanicId).isNotNull().isEqualTo(99L);
        verify(mechanicRepository, times(1)).save(any(Mechanic.class));
    }

    @Test
    void updateValidExistingMechanicReturnsUpdatedMechanicObject() throws Exception {
        Mechanic mechanic = MechanicTestData.generateMechanic();
        mechanic.setId(1L);
        when(mechanicRepository.findById(1L)).thenReturn(Optional.of(mechanic));

        String modifiedFirstName = "NewFirst";
        String modifiedLastName = "NewLast";
        String modifiedEmail = "NewEmail@domain.com";
        String modifiedPhone = "3334445566";
        LocalDate modifiedHireDate = LocalDate.now().minusMonths(17);

        MechanicDto modifiedMechanic = new MechanicDto();
        modifiedMechanic.setFirstName(modifiedFirstName);
        modifiedMechanic.setLastName(modifiedLastName);
        modifiedMechanic.setEmailAddress(modifiedEmail);
        modifiedMechanic.setPhoneNumber(modifiedPhone);
        modifiedMechanic.setHireDate(modifiedHireDate);
        modifiedMechanic.setEmploymentStatus(EmploymentStatus.ACTIVE);

        // need to mock the MechanicMapper so the Id values match (along with other fields)
        MechanicDto mappedSavedMechanic = new MechanicDto();
        mappedSavedMechanic.setId(1L);
        mappedSavedMechanic.setFirstName(modifiedFirstName);
        mappedSavedMechanic.setLastName(modifiedLastName);
        mappedSavedMechanic.setEmailAddress(modifiedEmail);
        mappedSavedMechanic.setPhoneNumber(modifiedPhone);
        mappedSavedMechanic.setHireDate(modifiedHireDate);
        mappedSavedMechanic.setEmploymentStatus(EmploymentStatus.ACTIVE);
        when(mechanicMapper.mechanicToMechanicDto(any(Mechanic.class))).thenReturn(mappedSavedMechanic);

        MechanicDto updatedMechanic = mechanicService.updateMechanic(1L, modifiedMechanic);

        assertThat(updatedMechanic.getId()).isEqualTo(1L);
        assertThat(updatedMechanic.getFirstName()).isEqualTo(modifiedFirstName);
        assertThat(updatedMechanic.getLastName()).isEqualTo(modifiedLastName);
        assertThat(updatedMechanic.getEmailAddress()).isEqualTo(modifiedEmail);
        assertThat(updatedMechanic.getPhoneNumber()).isEqualTo(modifiedPhone);

        verify(mechanicRepository, times(1)).findById(mechanic.getId());
        verify(mechanicRepository, times(1)).save(any(Mechanic.class));
    }

}
