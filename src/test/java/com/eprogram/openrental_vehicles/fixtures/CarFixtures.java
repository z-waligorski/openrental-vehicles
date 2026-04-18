package com.eprogram.openrental_vehicles.fixtures;

import com.eprogram.openrental_vehicles.dto.CarDTO;
import com.eprogram.openrental_vehicles.model.Car;

public class CarFixtures {
    public static CarDTO getCarDTO() {
        return new CarDTO(IdUtils.getUUID(IdUtils.ID_STRING_A),
                "Toyota",
                "Corolla",
                2020,
                4,
                10.0f);
    }


    public static CarDTO getUpdatedCarDTO() {
        return new CarDTO(IdUtils.getUUID(IdUtils.ID_STRING_A),
                "Honda",
                "Corolla",
                2020,
                4,
                10.0f);
    }


    public static CarDTO getInvalidCarDTO() {
        return new CarDTO(IdUtils.getUUID(IdUtils.ID_STRING_A),
                "Toyota",
                null,
                1025,
                0,
                -2f);
    }

    public static Car getCar() {
        Car car = new Car();
        car.setId(IdUtils.getUUID(IdUtils.ID_STRING_A));
        car.setBrand("Toyota");
        car.setModel("Corolla");
        car.setYearOfProduction(2020);
        car.setSeats(4);
        car.setFuelConsumption(10.0f);
        return car;
    }

    public static Car getUpdatedCar() {
        Car car = getCar();
        car.setBrand("Honda");
        return car;
    }

}
