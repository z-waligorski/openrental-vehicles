package com.eprogram.openrental_vehicles.service;

import com.eprogram.openrental_vehicles.dto.MotorcycleDTO;
import com.eprogram.openrental_vehicles.mapper.MotorcycleMapper;
import com.eprogram.openrental_vehicles.model.Motorcycle;
import com.eprogram.openrental_vehicles.repository.MotorcycleRepository;
import org.springframework.stereotype.Service;

@Service
public class MotorcycleService extends AbstractVehicleService<
        Motorcycle,
        MotorcycleDTO,
        MotorcycleRepository,
        MotorcycleMapper> {

    public MotorcycleService(MotorcycleRepository repository, MotorcycleMapper mapper) {
        super(repository, mapper);
    }
}
