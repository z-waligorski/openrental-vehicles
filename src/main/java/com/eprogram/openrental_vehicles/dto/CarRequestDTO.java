package com.eprogram.openrental_vehicles.dto;

public record CarRequestDTO(String brand,
                            String model,
                            Integer minYearOfProduction,
                            Integer seats) {
}
