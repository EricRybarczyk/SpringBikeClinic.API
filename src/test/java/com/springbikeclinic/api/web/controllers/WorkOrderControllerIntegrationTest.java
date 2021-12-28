package com.springbikeclinic.api.web.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WorkOrderControllerIntegrationTest {

    private static final String LOCALHOST = "http://localhost:";
    private static final String WORK_ORDERS_BASE_PATH ="/api/v1/work-orders/";

    @LocalServerPort
    private int serverPort;

    private TestRestTemplate testRestTemplate;

    @BeforeEach
    void setUp() {
        testRestTemplate = new TestRestTemplate();
    }

    @Test
    void deleteWorkOrder_validInputAndThenGet_returns404() throws Exception {
        String url = LOCALHOST + serverPort + WORK_ORDERS_BASE_PATH + "1";

        ResponseEntity<JsonNode> workOrderResponse = testRestTemplate.getForEntity(url, JsonNode.class);

        assertThat(workOrderResponse.getStatusCode()).isEqualTo((HttpStatus.OK));

        // exercise SUT method
        testRestTemplate.delete(url);

        // get the same entity, which should no longer exist
        ResponseEntity<JsonNode> deletedWorkOrderResponse = testRestTemplate.getForEntity(url, JsonNode.class);

        assertThat(deletedWorkOrderResponse.getStatusCode()).isEqualTo((HttpStatus.NOT_FOUND));
    }
}
