package com.springbikeclinic.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbikeclinic.api.domain.WorkOrder;
import com.springbikeclinic.api.dto.WorkOrderDto;
import com.springbikeclinic.api.helpers.WorkOrderTestData;
import com.springbikeclinic.api.services.WorkOrderNotFoundException;
import com.springbikeclinic.api.services.WorkOrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WorkOrderController.class)
class WorkOrderControllerTest {

    private static final String API_BASE_PATH = "/api/workorders/";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private WorkOrderService workOrderService;


    @Test
    void getWorkOrderWithValidIdShouldReturnWorkOrder() throws Exception {
        WorkOrderDto workOrder = WorkOrderTestData.generatePendingWorkOrderDto();
        when(workOrderService.getWorkOrderById(anyLong())).thenReturn(workOrder);

        mockMvc.perform(get(API_BASE_PATH + "1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerNotes", is(workOrder.getCustomerNotes())))
                .andExpect(jsonPath("$.status", is(workOrder.getStatus().name())));

        verify(workOrderService, times(1)).getWorkOrderById(1L);
    }

    @Test
    void getWorkOrderWithInvalidIdShouldReturn404() throws Exception {
        when(workOrderService.getWorkOrderById(anyLong())).thenThrow(new WorkOrderNotFoundException("Work order not found"));

        mockMvc.perform(get(API_BASE_PATH + "9999"))
                .andExpect(status().isNotFound());

        verify(workOrderService, times(1)).getWorkOrderById(9999L);
    }

    @Test
    void postValidWorkOrderShouldCreateNewResource() throws Exception {
        when(workOrderService.save(any(WorkOrderDto.class))).thenReturn(1L);
        WorkOrder workOrder = WorkOrderTestData.generatePendingWorkOrder();

        mockMvc.perform(post(API_BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(workOrder)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", "http://localhost/api/workorders/1"));

        verify(workOrderService, times(1)).save(any(WorkOrderDto.class));
    }

}
