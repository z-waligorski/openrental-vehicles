package com.eprogram.openrental_vehicles.model.util;

public interface Updatable<E, D> {
    E update(D dto);
}
