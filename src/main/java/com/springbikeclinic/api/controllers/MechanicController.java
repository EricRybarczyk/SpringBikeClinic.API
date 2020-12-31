package com.springbikeclinic.api.controllers;

import com.springbikeclinic.api.domain.Mechanic;
import com.springbikeclinic.api.services.MechanicService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/mechanics")
public class MechanicController {

    private final MechanicService mechanicService;

    public MechanicController(MechanicService mechanicService) {
        this.mechanicService = mechanicService;
    }

    @GetMapping
    public ResponseEntity<List<Mechanic>> getAllMechanics() {
        return ResponseEntity.ok(mechanicService.getAllMechanics());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mechanic> getMechanic(@PathVariable("id") Long id) {
        return ResponseEntity.ok(mechanicService.getMechanicById(id));
    }

    @PostMapping
    public ResponseEntity<Void> createNewMechanic(@Valid @RequestBody Mechanic mechanic, UriComponentsBuilder uriComponentsBuilder) {
        Long mechanicId = mechanicService.saveNewMechanic(mechanic);

        URI uri = uriComponentsBuilder.path("/api/mechanics/{customerId}").buildAndExpand(mechanicId).toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mechanic> updateExistingMechanic(@PathVariable("id") Long id, @Valid @RequestBody Mechanic mechanic) {
        Mechanic updatedMechanic = mechanicService.updateMechanic(id, mechanic);
        return ResponseEntity.ok(updatedMechanic);
    }
}
