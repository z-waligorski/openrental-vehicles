package com.eprogram.openrental_vehicles.controller;

import com.eprogram.openrental_vehicles.dto.CarDTO;
import com.eprogram.openrental_vehicles.dto.CarRequestDTO;
import com.eprogram.openrental_vehicles.service.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@PreAuthorize("hasRole('role_admin')")
@RestController
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;

    @PreAuthorize("hasAnyRole('role_admin', 'role_user')")
    @GetMapping("/{id}")
    public ResponseEntity<CarDTO> getCarById(@PathVariable UUID id) {
        return ResponseEntity.ok(carService.findById(id));
    }

    @GetMapping
    public Page<CarDTO> getAllCars(Pageable pageable) {
        return carService.findAll(pageable);
    }

    @PostMapping
    public ResponseEntity<CarDTO> createCar(@Valid @RequestBody CarDTO car) {
        var savedCarDTO = carService.save(car);
        return ResponseEntity.created(URI.create("/cars/" + savedCarDTO.id()))
                .body(savedCarDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarDTO> updateCar(@PathVariable UUID id, @Valid @RequestBody CarDTO car) {
        return ResponseEntity.ok(carService.update(car, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable UUID id) {
        carService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('role_admin', 'role_user')")
    @GetMapping("/filter")
    public ResponseEntity<List<CarDTO>> getFilteredCars(@ModelAttribute CarRequestDTO carRequestDTO) {
        var result = carService.getFilteredCars(carRequestDTO);
        return ResponseEntity.ok(result);
    }
}
