package com.eprogram.openrental_vehicles.mapper;

public interface  BaseVehicleMapper<E, D> {
    D toDTO(E entity);
    E toEntity(D dto);
}
