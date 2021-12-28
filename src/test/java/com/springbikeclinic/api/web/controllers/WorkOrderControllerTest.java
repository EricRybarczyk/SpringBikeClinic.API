package com.springbikeclinic.api.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbikeclinic.api.services.NotFoundException;
import com.springbikeclinic.api.services.WorkOrderService;
import com.springbikeclinic.api.web.model.WorkOrderDto;
import com.springbikeclinic.api.web.model.WorkOrderRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(WorkOrderController.class)
class WorkOrderControllerTest {

    private static final String WORK_ORDERS_BASE_PATH ="/api/v1/work-orders/";
    private static final String WORK_ORDER_EXISTING_SUB_PATH ="1";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private WorkOrderService workOrderService;

    private WorkOrderDto existingWorkOrderDto;
    private WorkOrderRequest workOrderRequest;

    @BeforeEach
    void setUp() {
        existingWorkOrderDto = WorkOrderDto.builder()
                .id(1L)
                .bikeDescription("Nice Bike")
                .workDescription("Tune-Up")
                .createdDateTime(OffsetDateTime.now().minusDays(3))
                .mechanicNotes("Needs a lot of work")
                .status("open")
                .build();

        workOrderRequest = WorkOrderRequest.builder()
                .bikeDescription("Another Bike")
                .workDescription("New Tires")
                .mechanicNotes("Might also need new tubes")
                .status("open")
                .build();
    }

    @Test
    void getWorkOrder_validWorkOrderId_returnsWorkOrderAndIsOk() throws Exception {
        when(workOrderService.getById(anyLong())).thenReturn(existingWorkOrderDto);

        mockMvc.perform(get(WORK_ORDERS_BASE_PATH + "1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bikeDescription", is("Nice Bike")))
                .andExpect(jsonPath("$.workDescription", is("Tune-Up")))
                .andExpect(jsonPath("$.mechanicNotes", is("Needs a lot of work")))
                .andExpect(jsonPath("$.status", is("open")))
                .andExpect(jsonPath("$.id", is(1)));

    }

    @Test
    void getWorkOrder_invalidWorkOrderId_returns404NotFound() throws Exception {
        when(workOrderService.getById(anyLong())).thenThrow(new NotFoundException("Work Order Not Found"));

        mockMvc.perform(get(WORK_ORDERS_BASE_PATH + "999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void postWorkOrder_validInput_isCreated() throws Exception {
        String jsonWorkOrderRequest = objectMapper.writeValueAsString(workOrderRequest);
        when(workOrderService.createWorkOrder(any(WorkOrderRequest.class))).thenReturn(123L);

        mockMvc.perform(post(WORK_ORDERS_BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonWorkOrderRequest))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));

        verify(workOrderService, times(1)).createWorkOrder(any(WorkOrderRequest.class));
    }

    @Test
    void postWorkOrder_withIncompleteWorkOrderRequest_isBadRequest() throws Exception {
        String jsonWorkOrderRequest = objectMapper.writeValueAsString(
                WorkOrderRequest.builder()
                        .bikeDescription("Incomplete Bike")
                        .build());

        mockMvc.perform(post(WORK_ORDERS_BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonWorkOrderRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].defaultMessage", is("must not be empty")))
                .andExpect(jsonPath("$[1].defaultMessage", is("must not be empty")));

        verifyNoInteractions(workOrderService);
    }

    @Test
    void putWorkOrder_validInput_isUpdatedAndOk() throws Exception {
        String jsonExistingWorkOrderDto = objectMapper.writeValueAsString(existingWorkOrderDto);
        when(workOrderService.updateWorkOrder(anyLong(), any(WorkOrderDto.class))).thenReturn(existingWorkOrderDto);

        mockMvc.perform(put(WORK_ORDERS_BASE_PATH + WORK_ORDER_EXISTING_SUB_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonExistingWorkOrderDto))
                .andExpect(status().isOk());

        verify(workOrderService, times(1)).updateWorkOrder(anyLong(), any(WorkOrderDto.class));
    }

    @Test
    void patchWorkOrder_updateSingleField_isUpdatedAndOk() throws Exception {
        Map<String, Object> patch = new HashMap<>();
        patch.put("mechanicNotes", "Patched up your bike");
        String jsonInput = objectMapper.writeValueAsString(patch);
        when(workOrderService.patchWorkOrder(anyLong(), any())).thenReturn(existingWorkOrderDto);

        mockMvc.perform(patch(WORK_ORDERS_BASE_PATH + WORK_ORDER_EXISTING_SUB_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput))
                .andExpect(status().isOk());

        verify(workOrderService, times(1)).patchWorkOrder(anyLong(), any());
    }

    @Test
    void deleteWorkOrder_validInput_returnsNoContent() throws Exception {
        mockMvc.perform(delete(WORK_ORDERS_BASE_PATH + "1"))
                .andExpect(status().isNoContent());
    }

}
