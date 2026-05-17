package com.eprogram.openrental_vehicles.controller;

import com.eprogram.openrental_vehicles.dto.MotorcycleDTO;
import com.eprogram.openrental_vehicles.service.MotorcycleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RequiredArgsConstructor
@PreAuthorize("hasRole('role_admin')")
@RestController
@RequestMapping("/motorcycles")
public class MotorcycleController {

    private final MotorcycleService motorcycleService;

    @PreAuthorize("hasAnyRole('role_admin', 'role_user')")
    @GetMapping("/{id}")
    public ResponseEntity<MotorcycleDTO> getMotorcycleById(@PathVariable UUID id) {
        return ResponseEntity.ok(motorcycleService.findById(id));
    }

    @GetMapping
    public Page<MotorcycleDTO> getAllMotorcycles(Pageable pageable) {
        return motorcycleService.findAll(pageable);
    }

    @PostMapping
    public ResponseEntity<MotorcycleDTO> createMotorcycle(@Valid @RequestBody MotorcycleDTO motorcycleDTO) {
        MotorcycleDTO savedMotorcycleDTO = motorcycleService.save(motorcycleDTO);
        return ResponseEntity.created(URI.create("/motorcycles/" + savedMotorcycleDTO.id()))
                .body(savedMotorcycleDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MotorcycleDTO> updateMotorcycle(@PathVariable UUID id,
                                                          @Valid @RequestBody MotorcycleDTO motorcycleDTO) {
        return ResponseEntity.ok(motorcycleService.update(motorcycleDTO, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMotorcycle(@PathVariable UUID id) {
        motorcycleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
