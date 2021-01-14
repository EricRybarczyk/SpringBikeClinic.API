package com.springbikeclinic.api.controllers;

import com.springbikeclinic.api.dto.WorkOrderDto;
import com.springbikeclinic.api.services.WorkOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/workorders")
public class WorkOrderController {

    private final WorkOrderService workOrderService;

    public WorkOrderController(WorkOrderService workOrderService) {
        this.workOrderService = workOrderService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkOrderDto> getWorkOrder(@PathVariable("id") Long id) {
        return ResponseEntity.ok(workOrderService.getWorkOrderById(id));
    }

    @PostMapping
    public ResponseEntity<Void> saveNewWorkOrder(@Valid @RequestBody WorkOrderDto workOrder, UriComponentsBuilder uriComponentsBuilder) {
        Long workOrderId = workOrderService.save(workOrder);

        URI uri = uriComponentsBuilder.path("/api/workorders/{workOrderId}").buildAndExpand(workOrderId).toUri();

        return ResponseEntity.created(uri).build();
    }
}
