package com.eprogram.openrental_vehicles.service;

import com.eprogram.openrental_vehicles.exception.VehicleNotFoundException;
import com.eprogram.openrental_vehicles.mapper.BaseVehicleMapper;
import com.eprogram.openrental_vehicles.model.util.Updatable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractVehicleService<
        E extends Updatable<E, D>, // entity
        D, // dto
        R extends JpaRepository<E, UUID>,
        M extends BaseVehicleMapper<E, D>> {

    protected final R repository;
    protected final M mapper;

    public D findById(UUID id) {
        E entity = repository.findById(id).orElseThrow(() -> new VehicleNotFoundException(id));
        return mapper.toDTO(entity);
    }

    public Page<D> findAll(Pageable pageable) {
        Page<E> entities = repository.findAll(pageable);
        return entities.map(mapper::toDTO);
    }

    public D save(D dto) {
        log.info("Saving " + dto.getClass() + ": {}", dto);
        E saved = repository.save(mapper.toEntity(dto));
        return mapper.toDTO(saved);
    }

    public D update(D dto, UUID id) {
        log.info("Updating " + dto.getClass() + " with ID: {}", id);
        E existing = repository.findById(id).orElseThrow(() -> new VehicleNotFoundException(id));
        existing.update(dto);
        E updated = repository.save(existing);
        return mapper.toDTO(updated);
    }

    public void delete(UUID id) {
        log.info("Deleting vehicle with ID: {}", id);
        E entity = repository.findById(id).orElseThrow(() -> new VehicleNotFoundException(id));
        repository.delete(entity);
    }
}
