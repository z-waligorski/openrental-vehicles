package com.eprogram.openrental_vehicles.repository;

import com.eprogram.openrental_vehicles.model.Car;
import org.springframework.data.jpa.domain.Specification;

public class CarSpecifications {
    public static Specification<Car> hasBrand(String brand) {
        return (root, query, builder)
                -> brand == null ? null : builder.equal(root.get("brand"), brand);
    }

    public static Specification<Car> hasModel(String model) {
        return (root, query, builder)
                -> model == null ? null : builder.equal(root.get("model"), model);
    }

    public static Specification<Car> hasYearOfProduction(Integer yearOfProduction) {
        return (root, query, builder)
                -> yearOfProduction == null ?
                null : builder.greaterThanOrEqualTo(root.get("yearOfProduction"), yearOfProduction);
    }

    public static Specification<Car> hasSeats(Integer seats) {
        return (root, query, builder)
                -> seats == null ? null : builder.equal(root.get("seats"), seats);
    }
}
