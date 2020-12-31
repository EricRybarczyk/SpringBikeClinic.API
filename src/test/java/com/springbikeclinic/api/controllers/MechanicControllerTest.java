package com.springbikeclinic.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbikeclinic.api.domain.Mechanic;
import com.springbikeclinic.api.helpers.MechanicTestData;
import com.springbikeclinic.api.services.MechanicNotFoundException;
import com.springbikeclinic.api.services.MechanicService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MechanicController.class)
class MechanicControllerTest {

    private static final String API_BASE_PATH = "/api/mechanics/";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Captor
    private ArgumentCaptor<Mechanic> argumentCaptor;

    @MockBean
    private MechanicService mechanicService;


    @Test
    void allMechanicsEndpointShouldReturnTwoMechanics() throws Exception {
        List<Mechanic> mechanicList = MechanicTestData.getMechanicList(2);
        when(mechanicService.getAllMechanics()).thenReturn(mechanicList);

        mockMvc.perform(get(API_BASE_PATH))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName", is(mechanicList.get(0).getFirstName())))
                .andExpect(jsonPath("$[0].lastName", is(mechanicList.get(0).getLastName())))
                .andExpect(jsonPath("$[0].phoneNumber", is(mechanicList.get(0).getPhoneNumber())))
                .andExpect(jsonPath("$[0].emailAddress", is(mechanicList.get(0).getEmailAddress())));

        verify(mechanicService, times(1)).getAllMechanics();
    }

    @Test
    void getMechanicWithIdOneShouldReturnValidMechanic() throws Exception {
        Mechanic mechanic = MechanicTestData.generateMechanic();
        mechanic.setId(1L);
        when(mechanicService.getMechanicById(1L)).thenReturn(mechanic);

        mockMvc.perform(get(API_BASE_PATH + "1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(mechanic.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(mechanic.getLastName())))
                .andExpect(jsonPath("$.phoneNumber", is(mechanic.getPhoneNumber())))
                .andExpect(jsonPath("$.emailAddress", is(mechanic.getEmailAddress())));

        verify(mechanicService, times(1)).getMechanicById(1L);
    }

    @Test
    void getMechanicWithInvalidIdShouldReturn404() throws Exception {
        when(mechanicService.getMechanicById(anyLong())).thenThrow(new MechanicNotFoundException("Mechanic not found"));

        mockMvc.perform(get(API_BASE_PATH + "9999"))
                .andExpect(status().isNotFound());

        verify(mechanicService, times(1)).getMechanicById(9999L);
    }

    @Test
    void postNewMechanicShouldCreateNewResource() throws Exception {
        when(mechanicService.saveNewMechanic(any(Mechanic.class))).thenReturn(1L);
        Mechanic newMechanic = MechanicTestData.generateMechanic();

        mockMvc.perform(post(API_BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newMechanic)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", "http://localhost/api/mechanics/1"));

        verify(mechanicService, times(1)).saveNewMechanic(any(Mechanic.class));
    }

    @Test
    void updateMechanicWithValidIdShouldUpdateMechanicDetails() throws Exception {
        Mechanic existingMechanic = MechanicTestData.generateMechanic();
        existingMechanic.setId(1L);

        when(mechanicService.updateMechanic(eq(1L), argumentCaptor.capture()))
                .thenReturn(existingMechanic);

        mockMvc.perform(put(API_BASE_PATH + "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(existingMechanic)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(existingMechanic.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(existingMechanic.getLastName())))
                .andExpect(jsonPath("$.phoneNumber", is(existingMechanic.getPhoneNumber())))
                .andExpect(jsonPath("$.emailAddress", is(existingMechanic.getEmailAddress())))
                .andExpect(jsonPath("$.hireDate", is(existingMechanic.getHireDate().format(DateTimeFormatter.ISO_LOCAL_DATE))));

        assertThat(argumentCaptor.getValue().getId(), is(existingMechanic.getId()));
        assertThat(argumentCaptor.getValue().getFirstName(), is(existingMechanic.getFirstName()));
        assertThat(argumentCaptor.getValue().getLastName(), is(existingMechanic.getLastName()));
        verify(mechanicService, times(1)).updateMechanic(anyLong(), any(Mechanic.class));
    }

}
