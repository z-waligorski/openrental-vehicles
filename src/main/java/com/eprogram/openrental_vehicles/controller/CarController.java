package com.eprogram.openrental_vehicles.controller;

import com.eprogram.openrental_vehicles.dto.CarDTO;
import com.eprogram.openrental_vehicles.service.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;

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
        CarDTO savedCarDTO = carService.save(car);
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
}
