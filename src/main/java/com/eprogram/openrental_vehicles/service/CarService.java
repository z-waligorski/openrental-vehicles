package com.eprogram.openrental_vehicles.service;

import com.eprogram.openrental_vehicles.dto.CarDTO;
import com.eprogram.openrental_vehicles.dto.CarRequestDTO;
import com.eprogram.openrental_vehicles.mapper.CarMapper;
import com.eprogram.openrental_vehicles.model.Car;
import com.eprogram.openrental_vehicles.repository.CarRepository;
import com.eprogram.openrental_vehicles.repository.CarSpecifications;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarService extends AbstractVehicleService<Car, CarDTO, CarRepository, CarMapper> {

    public CarService(CarRepository repository, CarMapper mapper) {
        super(repository, mapper);
    }

    public List<CarDTO> getFilteredCars(CarRequestDTO carDTO) {
        Specification<Car> spec = Specification
                .where(CarSpecifications.hasBrand(carDTO.brand()))
                .and(CarSpecifications.hasModel(carDTO.model()))
                .and(CarSpecifications.hasYearOfProduction(carDTO.minYearOfProduction()))
                .and(CarSpecifications.hasSeats(carDTO.seats()));

        List<Car> availableCars = repository.findAll(spec);

        return availableCars.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }
}
