package com.eprogram.openrental_vehicles.service;

import com.eprogram.openrental_vehicles.dto.CarDTO;
import com.eprogram.openrental_vehicles.dto.CarRequestDTO;
import com.eprogram.openrental_vehicles.exception.VehicleNotFoundException;
import com.eprogram.openrental_vehicles.fixtures.IdUtils;
import com.eprogram.openrental_vehicles.mapper.CarMapper;
import com.eprogram.openrental_vehicles.model.Car;
import com.eprogram.openrental_vehicles.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static com.eprogram.openrental_vehicles.fixtures.CarFixtures.*;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @Mock
    CarRepository carRepository;

    @Mock
    CarMapper carMapper;

    @InjectMocks
    CarService carService;

    @Test
    void findCarById_shouldReturnCar_whenCarExists() {
        Car car = getCar();
        CarDTO carDTO = getCarDTO();

        when(carRepository.findById(IdUtils.getUUID(IdUtils.ID_STRING_A)))
                .thenReturn(Optional.of(car));
        when(carMapper.toDTO(car)).thenReturn(carDTO);

        assertEquals(carDTO, carService.findById(IdUtils.getUUID(IdUtils.ID_STRING_A)));
        verify(carRepository).findById(IdUtils.getUUID(IdUtils.ID_STRING_A));
        verify(carMapper).toDTO(car);
    }

    @Test
    void findCarById_shouldThrowException_whenCarDoesNotExist() {
        when(carRepository.findById(IdUtils.getUUID(IdUtils.ID_STRING_A))).thenReturn(Optional.empty());

        assertThrows(VehicleNotFoundException.class,
                () -> carService.findById(IdUtils.getUUID(IdUtils.ID_STRING_A)));
        verify(carRepository).findById(IdUtils.getUUID(IdUtils.ID_STRING_A));
        verifyNoInteractions(carMapper);
    }

    @Test
    void findAllCars_shouldReturnPageOfCars() {
        Car car = getCar();
        CarDTO carDTO = getCarDTO();
        Pageable pageable = PageRequest.of(0, 10);
        Page<Car> carPage = new PageImpl<>(List.of(car));

        when(carRepository.findAll(pageable)).thenReturn(carPage);
        when(carMapper.toDTO(car)).thenReturn(carDTO);

        Page<CarDTO> result = carService.findAll(pageable);
        assertEquals(1, result.getContent().size());
        assertEquals(carDTO, result.getContent().getFirst());
        assertEquals(1, result.getTotalElements());
        verify(carRepository).findAll(pageable);
        verify(carMapper).toDTO(car);
    }

    @Test
    void saveCar_shouldSaveCarAndReturnDTO() {
        Car car = getCar();
        CarDTO carDTO = getCarDTO();

        when(carMapper.toEntity(carDTO)).thenReturn(car);
        when(carMapper.toDTO(car)).thenReturn(carDTO);
        when(carRepository.save(car)).thenReturn(car);

        assertEquals(carDTO, carService.save(carDTO));
        verify(carMapper).toEntity(carDTO);
        verify(carMapper).toDTO(car);
        verify(carRepository).save(car);
    }

    @Test
    void updateCar_shouldReturnUpdatedCarDTO_whenCarExists() {
        CarDTO updatedCarDTO = getUpdatedCarDTO();
        Car updatedCar = getUpdatedCar();

        Car carFromDB = getCar();
        when(carRepository.findById(IdUtils.getUUID(IdUtils.ID_STRING_A)))
                .thenReturn(Optional.of(carFromDB));
        when(carRepository.save(carFromDB)).thenReturn(updatedCar);
        when(carMapper.toDTO(updatedCar)).thenReturn(updatedCarDTO);

        assertEquals(updatedCarDTO, carService.update(updatedCarDTO,
                IdUtils.getUUID(IdUtils.ID_STRING_A)));
        verify(carRepository).findById(IdUtils.getUUID(IdUtils.ID_STRING_A));
        verify(carRepository).save(carFromDB);
        verify(carMapper).toDTO(updatedCar);
    }

    @Test
    void updateCar_shouldThrowException_whenCarDoesNotExist() {
        CarDTO updatedCarDTO = getUpdatedCarDTO();

        when(carRepository.findById(IdUtils.getUUID(IdUtils.ID_STRING_A))).thenReturn(Optional.empty());

        assertThrows(VehicleNotFoundException.class, () -> carService.update(updatedCarDTO,
                IdUtils.getUUID(IdUtils.ID_STRING_A)));
        verify(carRepository).findById(IdUtils.getUUID(IdUtils.ID_STRING_A));
        verifyNoMoreInteractions(carRepository);
    }

    @Test
    void deleteCarById_shouldDeleteCar_whenCarExists() {
        Car car = getCar();

        when(carRepository.findById(IdUtils.getUUID(IdUtils.ID_STRING_A)))
                .thenReturn(Optional.of(car));

        carService.delete(IdUtils.getUUID(IdUtils.ID_STRING_A));
        verify(carRepository).findById(IdUtils.getUUID(IdUtils.ID_STRING_A));
        verify(carRepository).delete(car);
        verifyNoMoreInteractions(carRepository);
    }

    @Test
    void deleteCarById_shouldThrowException_whenCarDoesNotExist() {
        when(carRepository.findById(IdUtils.getUUID(IdUtils.ID_STRING_A)))
                .thenReturn(Optional.empty());

        assertThrows(VehicleNotFoundException.class, () -> carService.delete(IdUtils.getUUID(IdUtils.ID_STRING_A)));
        verify(carRepository).findById(IdUtils.getUUID(IdUtils.ID_STRING_A));
        verify(carRepository, never()).delete(any(Car.class));
    }

    @Test
    void getFilteredCars_shouldReturnListOfCars_whenRequestProvided() {
        CarRequestDTO requestDTO = new CarRequestDTO("Toyota", null, null, 4);
        Car foundCar = getCar();
        CarDTO outputDTO = getCarDTO();

        when(carRepository.findAll(any(Specification.class))).thenReturn(List.of(foundCar));
        when(carMapper.toDTO(foundCar)).thenReturn(outputDTO);

        List<CarDTO> result = carService.getFilteredCars(requestDTO);
        assertThat(result).hasSize(1);
        assertThat(result.getFirst()).isEqualTo(outputDTO);
    }

}
