package com.eprogram.openrental_vehicles.repository;

import com.eprogram.openrental_vehicles.dto.CarRequestDTO;
import com.eprogram.openrental_vehicles.model.Car;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface CarRepository extends JpaRepository<Car, UUID>,
        JpaSpecificationExecutor<Car> {
}
