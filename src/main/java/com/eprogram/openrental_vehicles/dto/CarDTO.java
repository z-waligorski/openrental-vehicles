package com.eprogram.openrental_vehicles.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record CarDTO(
        UUID id,
        String brand,
        @NotBlank
        String model,
        @Min(1885)
        @NotNull
        Integer yearOfProduction,
        @Positive
        Integer seats,
        @Positive
        Float fuelConsumption
) {}
