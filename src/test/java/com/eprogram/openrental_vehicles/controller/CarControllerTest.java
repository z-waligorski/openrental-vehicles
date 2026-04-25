package com.eprogram.openrental_vehicles.controller;

import com.eprogram.openrental_vehicles.dto.CarDTO;
import com.eprogram.openrental_vehicles.dto.CarRequestDTO;
import com.eprogram.openrental_vehicles.exception.VehicleNotFoundException;
import com.eprogram.openrental_vehicles.fixtures.IdUtils;
import com.eprogram.openrental_vehicles.service.CarService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.UUID;

import static com.eprogram.openrental_vehicles.fixtures.CarFixtures.*;
import static com.eprogram.openrental_vehicles.fixtures.IdUtils.ID_STRING_A;
import static org.hamcrest.Matchers.aMapWithSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CarController.class)
class CarControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    CarService carService;

    @Test
    void getCarById_shouldReturnCar_whenCarExists() throws Exception {
        CarDTO carDTO = getCarDTO();

        when(carService.findById(IdUtils.getUUID(ID_STRING_A))).thenReturn(carDTO);

        mockMvc.perform(get("/cars/" + ID_STRING_A))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID_STRING_A))
                .andExpect(jsonPath("$.brand").value("Toyota"))
                .andExpect(jsonPath("$.model").value("Corolla"));
    }

    @Test
    void getCarById_shouldReturnNotFound_whenCarDoesNotExist() throws Exception {
        when(carService.findById(IdUtils.getUUID(ID_STRING_A)))
                .thenThrow(new VehicleNotFoundException(IdUtils.getUUID(ID_STRING_A)));

        mockMvc.perform(get("/cars/" + ID_STRING_A))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Vehicle with id " + ID_STRING_A + " not found"));
    }

    @Test
    void getAllCars_shouldReturnPageOfCars() throws Exception {
        CarDTO carDTO = getCarDTO();
        Page<CarDTO> page = new PageImpl<>(List.of(carDTO),
                PageRequest.of(0, 10), 1L);

        when(carService.findAll(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/cars")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(ID_STRING_A))
                .andExpect(jsonPath("$.content[0].brand").value("Toyota"))
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.size").value(10));
    }

    @Test
    void createCar_shouldReturnSavedCarDTO() throws Exception {
        CarDTO carDTO = getCarDTO();

        when(carService.save(any(CarDTO.class))).thenReturn(carDTO);

        mockMvc.perform(post("/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/cars/" + ID_STRING_A))
                .andExpect(jsonPath("$.id").value(ID_STRING_A))
                .andExpect(jsonPath("$.brand").value("Toyota"))
                .andExpect(jsonPath("$.model").value("Corolla"));
    }

    @Test
    void createCar_shouldReturnException_whenInvalidCarDTO() throws Exception {
        CarDTO invalidCarDTO = getInvalidCarDTO();

        mockMvc.perform(post("/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidCarDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.fields.model").value("must not be blank"))
                .andExpect(jsonPath("$.fields.yearOfProduction").value("must be greater than or equal to 1885"))
                .andExpect(jsonPath("$.fields.seats").value("must be greater than 0"))
                .andExpect(jsonPath("$.fields.fuelConsumption").value("must be greater than 0"))
                .andExpect(jsonPath("$.fields", aMapWithSize(4)));
    }

    @Test
    void updateCar_shouldReturnUpdatedCar_whenCarExists() throws Exception {
        CarDTO requestCarDTO = getUpdatedCarDTO();
        CarDTO responseCarDTO = getUpdatedCarDTO();

        when(carService.update(any(CarDTO.class), any(UUID.class))).thenReturn(responseCarDTO);

        mockMvc.perform(put("/cars/" + ID_STRING_A)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestCarDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID_STRING_A))
                .andExpect(jsonPath("$.brand").value("Honda"));
    }

    @Test
    void updateCar_shouldThrowException_whenCarDoesNotExist() throws Exception {
        CarDTO requestCarDTO = getCarDTO();

        when(carService.update(any(CarDTO.class), any(UUID.class)))
                .thenThrow(new VehicleNotFoundException(IdUtils.getUUID(ID_STRING_A)));

        mockMvc.perform(put("/cars/" + ID_STRING_A)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestCarDTO)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Vehicle with id " + ID_STRING_A + " not found"));
    }

    @Test
    void deleteCar_shouldDeleteCar_whenCarExists() throws Exception {
        mockMvc.perform(delete("/cars/" + ID_STRING_A))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteCar_shouldThrowException_whenCarDoesNotExist() throws Exception {
        doThrow(new VehicleNotFoundException(IdUtils.getUUID(ID_STRING_A)))
                .when(carService).delete(IdUtils.getUUID(ID_STRING_A));

        mockMvc.perform(delete("/cars/" + ID_STRING_A))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Vehicle with id " + ID_STRING_A + " not found"));
    }

    @Test
    void getFilteredCars_shouldReturnListOfCars_whenFiltersProvided() throws Exception {
        CarRequestDTO inputDTO = new CarRequestDTO("Toyota", null, 2020, null);

        when(carService.getFilteredCars(inputDTO)).thenReturn(List.of(getCarDTO()));

        mockMvc.perform(get("/cars/filter")
                        .param("brand", "Toyota")
                        .param("minYearOfProduction", "2020"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(ID_STRING_A))
                .andExpect(jsonPath("$[0].brand").value("Toyota"))
                .andExpect(jsonPath("$[0].model").value("Corolla"))
                .andExpect(jsonPath("$[0].yearOfProduction").value(2020))
                .andExpect(jsonPath("$[0].seats").value(4))
                .andExpect(jsonPath("$[0].fuelConsumption").value(10.0));
    }

    @Test
    void getFilteredCars_shouldReturnListOfCars_whenNoFiltersProvided() throws Exception {
        CarRequestDTO inputDTO = new CarRequestDTO(null, null, null, null);

        when(carService.getFilteredCars(inputDTO)).thenReturn(List.of(getCarDTO()));

        mockMvc.perform(get("/cars/filter"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(ID_STRING_A))
                .andExpect(jsonPath("$[0].brand").value("Toyota"))
                .andExpect(jsonPath("$[0].model").value("Corolla"))
                .andExpect(jsonPath("$[0].yearOfProduction").value(2020))
                .andExpect(jsonPath("$[0].seats").value(4))
                .andExpect(jsonPath("$[0].fuelConsumption").value(10.0));
    }
}
