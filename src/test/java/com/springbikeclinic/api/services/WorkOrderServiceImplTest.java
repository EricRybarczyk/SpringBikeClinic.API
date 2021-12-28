package com.springbikeclinic.api.services;

import com.springbikeclinic.api.domain.WorkOrder;
import com.springbikeclinic.api.repositories.WorkOrderRepository;
import com.springbikeclinic.api.web.mappers.WorkOrderMapper;
import com.springbikeclinic.api.web.model.WorkOrderDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class WorkOrderServiceImplTest {

    /*
        Use @Mock when unit testing your business logic (only using JUnit and Mockito).
        Use @MockBean when you write a test that is backed by a Spring Test Context
            and you want to add or replace a bean with a mocked version of it.
     */

    @Mock
    private WorkOrderRepository workOrderRepository;

    @Mock
    private WorkOrderMapper workOrderMapper;

    @InjectMocks
    private WorkOrderServiceImpl workOrderService;

    private WorkOrderDto existingWorkOrderDto;
    private WorkOrder existingWorkOrder;


    @BeforeEach
    void setUp() {
        //MockitoAnnotations.openMocks(this);
        workOrderService = new WorkOrderServiceImpl(workOrderRepository, workOrderMapper);

        existingWorkOrderDto = WorkOrderDto.builder()
                .id(1L)
                .bikeDescription("Nice Bike")
                .workDescription("Tune-Up")
                .createdDateTime(OffsetDateTime.now().minusDays(3))
                .mechanicNotes("Needs a lot of work")
                .status("open")
                .build();

        existingWorkOrder = new WorkOrder(
                777L,
                Timestamp.from(Instant.now()),
                "Existing Bike",
                "Fix Things",
                "Open",
                "We can rebuild him, we have the technology.",
                false
        );

    }

    @Test
    void updateWorkOrder_validInput_savesAllValues() throws Exception {
        when(workOrderRepository.findById(anyLong())).thenReturn(Optional.of(existingWorkOrder));

        ArgumentCaptor<WorkOrder> workOrderDtoArgumentCaptor = ArgumentCaptor.forClass(WorkOrder.class);
        ArgumentCaptor<Long> idArgumentCaptor = ArgumentCaptor.forClass(Long.class);

        // set up the WorkOrderDto with alternate values
        final Long existingId = existingWorkOrderDto.getId();
        final String diffBikeDescription = "Altered Bike";
        final String diffWorkDescription = "Altered Work";
        final String diffMechanicNotes = "Altered Notes";
        final String diffStatus = "closed";

        existingWorkOrderDto.setBikeDescription(diffBikeDescription);
        existingWorkOrderDto.setWorkDescription(diffWorkDescription);
        existingWorkOrderDto.setMechanicNotes(diffMechanicNotes);
        existingWorkOrderDto.setStatus(diffStatus);

        // Call the SUT method
        workOrderService.updateWorkOrder(existingId, existingWorkOrderDto);

        // mockito verify and capture arguments
        verify(workOrderRepository, times(1)).findById(idArgumentCaptor.capture());
        verify(workOrderRepository, times(1)).save(workOrderDtoArgumentCaptor.capture());

        // assert that the values on the captured arguments are the expected values
        assertThat(idArgumentCaptor.getValue()).isEqualTo(existingId);
        final WorkOrder capturedWorkOrder = workOrderDtoArgumentCaptor.getValue();
        assertThat(capturedWorkOrder.getBikeDescription()).isEqualTo(diffBikeDescription);
        assertThat(capturedWorkOrder.getWorkDescription()).isEqualTo(diffWorkDescription);
        assertThat(capturedWorkOrder.getMechanicNotes()).isEqualTo(diffMechanicNotes);
        assertThat(capturedWorkOrder.getStatus()).isEqualTo(diffStatus);

    }

    @Test
    void patchWorkOrder_singleField_updatesField() throws Exception {
        when(workOrderRepository.findById(anyLong())).thenReturn(Optional.of(existingWorkOrder));

        Long existingId = existingWorkOrderDto.getId();
        String diffMechanicNotes = "Altered Notes";
        Map<String, Object> patch = new HashMap<>();
        patch.put("mechanicNotes", diffMechanicNotes);

        ArgumentCaptor<WorkOrder> workOrderDtoArgumentCaptor = ArgumentCaptor.forClass(WorkOrder.class);
        ArgumentCaptor<Long> idArgumentCaptor = ArgumentCaptor.forClass(Long.class);

        // Call the SUT method
        workOrderService.patchWorkOrder(existingId, patch);

        // mockito verify and capture arguments
        verify(workOrderRepository, times(1)).findById(idArgumentCaptor.capture());
        verify(workOrderRepository, times(1)).save(workOrderDtoArgumentCaptor.capture());

        // assert that the values on the captured arguments are the expected values
        assertThat(idArgumentCaptor.getValue()).isEqualTo(existingId);
        WorkOrder capturedWorkOrder = workOrderDtoArgumentCaptor.getValue();
        assertThat(capturedWorkOrder.getMechanicNotes()).isEqualTo(diffMechanicNotes);
    }

    @Test
    void deleteWorkOrder_validInput_deletePerformed() throws Exception {
        Long existingId = existingWorkOrderDto.getId();
        ArgumentCaptor<Long> idArgumentCaptor = ArgumentCaptor.forClass(Long.class);

        // Call the SUT method
        workOrderService.deleteWorkOrder(existingId);

        verify(workOrderRepository, times(1)).deleteById(idArgumentCaptor.capture());
        assertThat(idArgumentCaptor.getValue()).isEqualTo(existingId);
    }

}
