package com.eprogram.openrental_vehicles.model;

import com.eprogram.openrental_vehicles.dto.MotorcycleDTO;
import com.eprogram.openrental_vehicles.model.util.Updatable;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "motorcycles")
public class Motorcycle extends Vehicle implements Updatable<Motorcycle, MotorcycleDTO> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Float engineCapacity;

    public Motorcycle update(MotorcycleDTO motorcycleDTO) {
        this.setBrand(motorcycleDTO.brand());
        this.setModel(motorcycleDTO.model());
        this.setYearOfProduction(motorcycleDTO.yearOfProduction());
        this.setEngineCapacity(motorcycleDTO.engineCapacity());
        return this;
    }
}
