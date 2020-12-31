package com.springbikeclinic.api.services;

import com.springbikeclinic.api.domain.Mechanic;
import com.springbikeclinic.api.helpers.MechanicTestData;
import com.springbikeclinic.api.repositories.MechanicRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MechanicServiceTest {

    @Mock
    private MechanicRepository mechanicRepository;

    @InjectMocks
    private MechanicServiceImpl mechanicService;

    @Test
    void getAllMechanicsReturnsTwoMechanics() throws Exception {
        List<Mechanic> mechanicList = MechanicTestData.getMechanicList(2);
        when(mechanicRepository.findAll()).thenReturn(mechanicList);

        List<Mechanic> result = mechanicService.getAllMechanics();

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);

        verify(mechanicRepository, times(1)).findAll();
    }

    @Test
    void getMechanicWithIdOneReturnsValidMechanic() throws Exception {
        Mechanic mechanic = MechanicTestData.generateMechanic();
        mechanic.setId(1L);
        when(mechanicRepository.findById(1L)).thenReturn(Optional.of(mechanic));

        Mechanic result = mechanicService.getMechanicById(1L);

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

        Mechanic newMechanic = MechanicTestData.generateMechanic();
        Long newMechanicId = mechanicService.saveNewMechanic(newMechanic);

        assertThat(newMechanicId).isNotNull().isEqualTo(99L);
        verify(mechanicRepository, times(1)).save(newMechanic);
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

        Mechanic modifiedMechanic = new Mechanic();
        modifiedMechanic.setFirstName(modifiedFirstName);
        modifiedMechanic.setLastName(modifiedLastName);
        modifiedMechanic.setEmailAddress(modifiedEmail);
        modifiedMechanic.setPhoneNumber(modifiedPhone);
        modifiedMechanic.setHireDate(modifiedHireDate);

        Mechanic updatedMechanic = mechanicService.updateMechanic(1L, modifiedMechanic);

        assertThat(updatedMechanic.getId()).isEqualTo(1L);
        assertThat(updatedMechanic.getFirstName()).isEqualTo(modifiedFirstName);
        assertThat(updatedMechanic.getLastName()).isEqualTo(modifiedLastName);
        assertThat(updatedMechanic.getEmailAddress()).isEqualTo(modifiedEmail);
        assertThat(updatedMechanic.getPhoneNumber()).isEqualTo(modifiedPhone);

        verify(mechanicRepository, times(1)).findById(mechanic.getId());
        verify(mechanicRepository, times(1)).save(any(Mechanic.class));
    }

}
