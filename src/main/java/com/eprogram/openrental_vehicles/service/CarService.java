package com.eprogram.openrental_vehicles.service;

import com.eprogram.openrental_vehicles.dto.CarDTO;
import com.eprogram.openrental_vehicles.mapper.CarMapper;
import com.eprogram.openrental_vehicles.model.Car;
import com.eprogram.openrental_vehicles.repository.CarRepository;
import org.springframework.stereotype.Service;

@Service
public class CarService extends AbstractVehicleService<Car, CarDTO, CarRepository, CarMapper> {

    public CarService(CarRepository repository, CarMapper mapper) {
        super(repository, mapper);
    }
}
