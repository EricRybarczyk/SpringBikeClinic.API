package com.springbikeclinic.api.controllers;

import com.springbikeclinic.api.domain.WorkOrder;
import com.springbikeclinic.api.services.WorkOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/workorders")
public class WorkOrderController {

    private final WorkOrderService workOrderService;

    public WorkOrderController(WorkOrderService workOrderService) {
        this.workOrderService = workOrderService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkOrder> getWorkOrder(@PathVariable("id") Long id) {
        return ResponseEntity.ok(workOrderService.getWorkOrderById(id));
    }
}
