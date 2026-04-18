package com.eprogram.openrental_vehicles.model;

import com.eprogram.openrental_vehicles.dto.CarDTO;
import com.eprogram.openrental_vehicles.model.util.Updatable;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "cars")
public class Car extends Vehicle implements Updatable<Car, CarDTO> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Integer seats;
    private Float fuelConsumption;

    public Car update(CarDTO carDTO) {
        this.setBrand(carDTO.brand());
        this.setModel(carDTO.model());
        this.setYearOfProduction(carDTO.yearOfProduction());
        this.setSeats(carDTO.seats());
        this.setFuelConsumption(carDTO.fuelConsumption());
        return this;
    }
}
