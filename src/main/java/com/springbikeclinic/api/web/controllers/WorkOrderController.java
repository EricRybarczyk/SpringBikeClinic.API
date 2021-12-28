package com.springbikeclinic.api.web.controllers;

import com.springbikeclinic.api.services.WorkOrderService;
import com.springbikeclinic.api.web.model.WorkOrderDto;
import com.springbikeclinic.api.web.model.WorkOrderRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/work-orders")
@RequiredArgsConstructor
@Slf4j
public class WorkOrderController {

    private final WorkOrderService workOrderService;

    @GetMapping("/{id}")
    public ResponseEntity<WorkOrderDto> getWorkOrder(@PathVariable("id") Long id) {
        return ResponseEntity.ok(workOrderService.getById(id));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<WorkOrderDto>> getWorkOrders(@PathVariable("status") String status) {
        return ResponseEntity.ok(workOrderService.getByStatus(status));
    }

    @GetMapping("/query")
    public ResponseEntity<String> handleQueryString(@RequestParam("q") String query) {
        return ResponseEntity.ok("You sent: " + query);
    }

    @PostMapping
    public ResponseEntity<Void> saveWorkOrder(@Valid @RequestBody WorkOrderRequest workOrderRequest, UriComponentsBuilder uriComponentsBuilder) {
        final Long workOrderId = workOrderService.createWorkOrder(workOrderRequest);

        UriComponents uriComponents = uriComponentsBuilder.path("/api/v1/work-orders/{id}").buildAndExpand(workOrderId);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponents.toUri());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping("/{id}") // PUT replaces the entire resource
    public ResponseEntity<WorkOrderDto> putWorkOrder(@PathVariable("id") Long id, @RequestBody WorkOrderDto workOrderDto) { // TODO:  @Valid  was I going to use this?
        return ResponseEntity.ok(workOrderService.updateWorkOrder(id, workOrderDto));
    }

    @PatchMapping("/{id}") // PATCH updates whichever fields are included
    public ResponseEntity<WorkOrderDto> patchWorkOrder(@PathVariable("id") Long id, @RequestBody Map<String, Object> patch) {
        return ResponseEntity.ok(workOrderService.patchWorkOrder(id, patch));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkOrder(@PathVariable("id") Long id) {
        workOrderService.deleteWorkOrder(id);
        return ResponseEntity.noContent().build();
    }

}

