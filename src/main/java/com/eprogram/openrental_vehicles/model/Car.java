package com.eprogram.openrental_vehicles.model;

import com.eprogram.openrental_vehicles.dto.CarDTO;
import com.eprogram.openrental_vehicles.model.util.Updatable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.UUID;

@Getter
@Setter
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        Car other = (Car) o;
        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return Hibernate.getClass(this).hashCode();
    }
}
