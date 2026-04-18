package com.eprogram.openrental_vehicles.fixtures;

import com.eprogram.openrental_vehicles.dto.MotorcycleDTO;

public class MotorcycleFixtures {

    public static MotorcycleDTO getMotorcycleDTO() {
        return new MotorcycleDTO(IdUtils.getUUID(IdUtils.ID_STRING_A),
                "Honda",
                "CBR 600",
                2023,
                600f);
    }

    public static MotorcycleDTO getMotorcycleDTOWithoutId() {
        return new MotorcycleDTO(null,
                "Honda",
                "CBR 600",
                2023,
                600f);
    }

    public static MotorcycleDTO getInvalidMotorcycleDTO() {
        return new MotorcycleDTO(IdUtils.getUUID(IdUtils.ID_STRING_A),
                "Honda",
                "",
                1883,
                0f);
    }

    public static MotorcycleDTO getUpdatedMotorcycleDTO() {
        return new MotorcycleDTO(IdUtils.getUUID(IdUtils.ID_STRING_A),
                "Yamaha",
                "R125",
                2023,
                600f);
    }
}
