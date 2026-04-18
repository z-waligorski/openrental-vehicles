package com.eprogram.openrental_vehicles.repository;

import com.eprogram.openrental_vehicles.model.Motorcycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MotorcycleRepository extends JpaRepository<Motorcycle, UUID> {
}
