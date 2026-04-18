package com.eprogram.openrental_vehicles.mapper;

import com.eprogram.openrental_vehicles.dto.MotorcycleDTO;
import com.eprogram.openrental_vehicles.model.Motorcycle;
import org.springframework.stereotype.Component;

@Component
public class MotorcycleMapper implements BaseVehicleMapper<Motorcycle, MotorcycleDTO> {

    public MotorcycleDTO toDTO(Motorcycle motorcycle) {
        return new MotorcycleDTO(motorcycle.getId(),
                motorcycle.getModel(),
                motorcycle.getBrand(),
                motorcycle.getYearOfProduction(),
                motorcycle.getEngineCapacity());
    }

    public Motorcycle toEntity (MotorcycleDTO motorcycleDTO) {
        Motorcycle motorcycle = new Motorcycle();
        motorcycle.setId(motorcycleDTO.id());
        motorcycle.setModel(motorcycleDTO.model());
        motorcycle.setBrand(motorcycleDTO.brand());
        motorcycle.setYearOfProduction(motorcycleDTO.yearOfProduction());
        motorcycle.setEngineCapacity(motorcycleDTO.engineCapacity());
        return motorcycle;
    }
}
