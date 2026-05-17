package com.eprogram.openrental_vehicles.controller;

import com.eprogram.openrental_vehicles.config.SecurityConfig;
import com.eprogram.openrental_vehicles.dto.MotorcycleDTO;
import com.eprogram.openrental_vehicles.exception.VehicleNotFoundException;
import com.eprogram.openrental_vehicles.fixtures.IdUtils;
import com.eprogram.openrental_vehicles.service.MotorcycleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.UUID;

import static com.eprogram.openrental_vehicles.fixtures.MotorcycleFixtures.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WithMockUser(roles = "role_admin")
@Import(SecurityConfig.class)
@WebMvcTest(MotorcycleController.class)
public class MotorcycleControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    MotorcycleService motorcycleService;

    @Test
    void getMotorcycleById_shouldReturnMotorcycleDTO_whenMotorcycleExists() throws Exception {
        MotorcycleDTO motorcycleDTO = getMotorcycleDTO();

        when(motorcycleService.findById(IdUtils.getUUID(IdUtils.ID_STRING_A))).thenReturn(motorcycleDTO);

        mockMvc.perform(get("/motorcycles/" + IdUtils.ID_STRING_A))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brand").value("Honda"))
                .andExpect(jsonPath("$.model").value("CBR 600"))
                .andExpect(jsonPath("$.id").value(IdUtils.ID_STRING_A));
    }

    @Test
    void getMotorcycleById_shouldReturnVehicleNotFound_whenMotorcycleNotExisting() throws Exception {
        when(motorcycleService.findById(IdUtils.getUUID(IdUtils.ID_STRING_A)))
                .thenThrow(new VehicleNotFoundException(IdUtils.getUUID(IdUtils.ID_STRING_A)));

        mockMvc.perform(get("/motorcycles/" + IdUtils.ID_STRING_A))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Vehicle with id " + IdUtils.ID_STRING_A + " not found"));
    }

    @Test
    void getAllMotorcycles_shouldReturnPageOfMotorcycles() throws Exception {
        MotorcycleDTO dto = getMotorcycleDTO();
        Page<MotorcycleDTO> pageOfMotorcycles = new PageImpl<>(List.of(dto),
                PageRequest.of(1, 10), 1);

        when(motorcycleService.findAll(any(Pageable.class))).thenReturn(pageOfMotorcycles);

        mockMvc.perform(get("/motorcycles")
                        .param("page", "1")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(IdUtils.ID_STRING_A))
                .andExpect(jsonPath("$.content[0].brand").value("Honda"))
                .andExpect(jsonPath("$.content[0].model").value("CBR 600"))
                .andExpect(jsonPath("$.content[0].engineCapacity").value(600f));
    }

    @Test
    void createMotorcycle_shouldReturnMotorcycleDTO_whenInputIsValid() throws Exception {
        MotorcycleDTO inputMotorcycleDTO = getMotorcycleDTOWithoutId();
        MotorcycleDTO savedMotorcycleDTO = getMotorcycleDTO();

        when(motorcycleService.save(inputMotorcycleDTO)).thenReturn(savedMotorcycleDTO);

        mockMvc.perform(post("/motorcycles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputMotorcycleDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(IdUtils.ID_STRING_A))
                .andExpect(jsonPath("$.brand").value("Honda"))
                .andExpect(jsonPath("$.model").value("CBR 600"))
                .andExpect(jsonPath("$.engineCapacity").value(600f))
                .andExpect(jsonPath("$.yearOfProduction").value(2023));
    }

    @Test
    void createMotorcycle_shouldReturnValidationErrors_whenInputNotValid() throws Exception {
        MotorcycleDTO invalidDTO = getInvalidMotorcycleDTO();

        mockMvc.perform(post("/motorcycles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.fields.model").value("must not be blank"))
                .andExpect(jsonPath("$.fields.yearOfProduction").value("must be greater than or equal to 1885"))
                .andExpect(jsonPath("$.fields.engineCapacity").value("must be greater than 0"));
    }

    @Test
    void updateMotorcycle_shouldReturnUpdatedMotorcycleDTO_whenMotorcycleExists() throws Exception {
        MotorcycleDTO requestMotorcycleDTO = getUpdatedMotorcycleDTO();
        MotorcycleDTO responseMotorcycleDTO = getUpdatedMotorcycleDTO();

        when(motorcycleService.update(requestMotorcycleDTO, IdUtils.getUUID(IdUtils.ID_STRING_A)))
                .thenReturn(responseMotorcycleDTO);

        mockMvc.perform(put("/motorcycles/" + IdUtils.ID_STRING_A)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestMotorcycleDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brand").value("Yamaha"))
                .andExpect(jsonPath("$.model").value("R125"));
    }

    @Test
    void updateMotorcycle_shouldReturnNotFound_whenMotorcycleNotExists() throws Exception {
        MotorcycleDTO requestMotorcycleDTO = getMotorcycleDTO();
        when(motorcycleService.update(any(MotorcycleDTO.class), any(UUID.class)))
                .thenThrow(new VehicleNotFoundException(IdUtils.getUUID(IdUtils.ID_STRING_A)));

        mockMvc.perform(put("/motorcycles/" + IdUtils.ID_STRING_A)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestMotorcycleDTO)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Vehicle with id " + IdUtils.ID_STRING_A + " not found"));
    }

    @Test
    void deleteMotorcycle_shouldReturnNoContent_whenMotorcycleExists() throws Exception {
        mockMvc.perform(delete("/motorcycles/" + IdUtils.ID_STRING_A))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteMotorcycle_shouldReturnBadRequest_whenMotorcycleNotExists() throws Exception {
        doThrow(new VehicleNotFoundException(IdUtils.getUUID(IdUtils.ID_STRING_A)))
                .when(motorcycleService).delete(IdUtils.getUUID(IdUtils.ID_STRING_A));

        mockMvc.perform(delete("/motorcycles/" + IdUtils.ID_STRING_A))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Vehicle with id " + IdUtils.ID_STRING_A + " not found"));
    }
}
