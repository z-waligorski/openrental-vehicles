package com.eprogram.openrental_vehicles.mapper;

import com.eprogram.openrental_vehicles.dto.CarDTO;
import com.eprogram.openrental_vehicles.model.Car;
import org.springframework.stereotype.Component;

@Component
public class CarMapper implements BaseVehicleMapper<Car, CarDTO> {

    @Override
    public CarDTO toDTO(Car car) {
        if (car == null) {
            return null;
        }

        return new CarDTO(car.getId(),
                car.getBrand(),
                car.getModel(),
                car.getYearOfProduction(),
                car.getSeats(),
                car.getFuelConsumption());
    }

    @Override
    public Car toEntity(CarDTO carDTO) {
        if (carDTO == null) {
            return null;
        }

        Car car = new Car();
        car.setId(carDTO.id());
        car.setBrand(carDTO.brand());
        car.setModel(carDTO.model());
        car.setYearOfProduction(carDTO.yearOfProduction());
        car.setSeats(carDTO.seats());
        car.setFuelConsumption(carDTO.fuelConsumption());
        return car;
    }
}
