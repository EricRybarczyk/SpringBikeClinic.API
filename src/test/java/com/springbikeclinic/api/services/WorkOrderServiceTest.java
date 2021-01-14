package com.springbikeclinic.api.services;

import com.springbikeclinic.api.domain.WorkOrder;
import com.springbikeclinic.api.dto.WorkOrderDto;
import com.springbikeclinic.api.helpers.WorkOrderTestData;
import com.springbikeclinic.api.mappers.WorkOrderMapper;
import com.springbikeclinic.api.repositories.WorkOrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WorkOrderServiceTest {

    @Mock
    private WorkOrderRepository workOrderRepository;

    @Mock
    private WorkOrderMapper workOrderMapper;

    @InjectMocks
    private WorkOrderServiceImpl workOrderService;

    @Test
    void getWorkOrderWithValidIdReturnsWorkOrderObject() throws Exception {
        WorkOrder workOrder = WorkOrderTestData.generatePendingWorkOrder();
        workOrder.setId(1L);
        when(workOrderRepository.findById(anyLong())).thenReturn(Optional.of(workOrder));

        WorkOrderDto workOrderDto = WorkOrderTestData.generatePendingWorkOrderDto();
        workOrderDto.setId(1L);
        when(workOrderMapper.workOrderToWorkOrderDto(any(WorkOrder.class))).thenReturn(workOrderDto);

        WorkOrderDto result = workOrderService.getWorkOrderById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getCustomerNotes()).isEqualTo(workOrder.getCustomerNotes());

        verify(workOrderRepository, times(1)).findById(1L);
    }

    @Test
    void getWorkOrderWithInvalidIdShouldThrowWorkOrderNotFoundException() throws Exception {
        when(workOrderRepository.findById(anyLong())).thenReturn(Optional.empty());

        Assertions.assertThrows(WorkOrderNotFoundException.class, () -> workOrderService.getWorkOrderById(9999L));
    }

    @Test
    void saveValidWorkOrderReturnsWorkOrderId() throws Exception {
        WorkOrder savedWorkOrder = WorkOrderTestData.generatePendingWorkOrder();
        savedWorkOrder.setId(99L);
        when(workOrderRepository.save(any(WorkOrder.class))).thenReturn(savedWorkOrder);
        when(workOrderMapper.workOrderDtoToWorkOrder(any(WorkOrderDto.class))).thenReturn(savedWorkOrder);

        WorkOrderDto newWorkOrder = WorkOrderTestData.generatePendingWorkOrderDto();
        Long workOrderId = workOrderService.save(newWorkOrder);

        assertThat(workOrderId).isNotNull().isEqualTo(99L);
        verify(workOrderRepository, times(1)).save(any(WorkOrder.class));
    }

}
