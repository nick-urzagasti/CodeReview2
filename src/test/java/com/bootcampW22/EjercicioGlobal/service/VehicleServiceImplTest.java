package com.bootcampW22.EjercicioGlobal.service;

import com.bootcampW22.EjercicioGlobal.dto.VehicleAvgSpeedByBrandDto;
import com.bootcampW22.EjercicioGlobal.dto.VehicleDto;
import com.bootcampW22.EjercicioGlobal.entity.Vehicle;
import com.bootcampW22.EjercicioGlobal.exception.NotFoundException;
import com.bootcampW22.EjercicioGlobal.repository.VehicleRepositoryImpl;
import com.bootcampW22.EjercicioGlobal.utils.VehicleGeneratorUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class VehicleServiceImplTest {
    @Mock
    VehicleRepositoryImpl vehicleRepository;
    @InjectMocks
    VehicleServiceImpl vehicleService;

    @Test
    void searchAllVehicles() {
    }

    @Test
    void searchVehiclesByYearAndColor() throws Exception{
        //Arrange
        List<Vehicle> expectedVehicles = VehicleGeneratorUtils.getVehiclesByYearAndColor();
        ObjectMapper objectMapper = new ObjectMapper();
        List<VehicleDto> expectedVehicleDTO = expectedVehicles.stream().map(vehicle -> objectMapper.convertValue(vehicle, VehicleDto.class)).toList();
        String color = "orange";
        int year = 1992;
        when(vehicleRepository.findVehiclesByYearAndColor(color, year)).thenReturn(expectedVehicles);

        //Act
        List<VehicleDto> actualVehicleDTO = vehicleService.searchVehiclesByYearAndColor(color, year);
        //assert
        assertEquals(expectedVehicleDTO, actualVehicleDTO);
        verify(vehicleRepository, atLeastOnce()).findVehiclesByYearAndColor(color, year);
    }

    @Test
    void searchVehiclesByBrandAndRangeOfYear_OK() throws Exception{
        //Arrange
        String brand = "Honda";
        int yearFrom = 1998;
        int yearTo = 2000;
        List<VehicleDto> expected = VehicleGeneratorUtils.getVehiclesDtoByBrandAndYearRange();
        List<Vehicle> carsList = VehicleGeneratorUtils.getVehiclesByBrandAndYearRange();

        when(vehicleRepository.findVehiclesByBrandAndRangeOfYear(brand, yearFrom, yearTo)).thenReturn(carsList);

        // Act
        List<VehicleDto> result = vehicleService.searchVehiclesByBrandAndRangeOfYear(brand, yearFrom, yearTo);

        // Assert
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Should throw NotFoundException if no vehicle is found")
    public void searchVehiclesByYearAndColorNotFound() {
        // ARR
        String color = "Brown";
        int year = 2015;

        when(vehicleRepository.findVehiclesByYearAndColor("Brown", 2015)).thenReturn(
                new ArrayList<>()
        );

        // Act and Assert

        Assertions.assertThrows(
                NotFoundException.class,
                () -> vehicleService.searchVehiclesByYearAndColor(color, year)
        );
        verify(vehicleRepository, atLeast(1)).findVehiclesByYearAndColor(color, year);
    }

    @Test
    void searchVehiclesByBrandAndRangeOfYear_NOT_FOUND() {
        // Arrange
        String brand = "Honda";
        int yearFrom = 0;
        int yearTo = 0;
        List<Vehicle> carsList = new ArrayList<>();
        when(vehicleRepository.findVehiclesByBrandAndRangeOfYear(brand, yearFrom, yearTo)).thenReturn(carsList);

        // Act + Assert
        assertThrows(NotFoundException.class, () -> {
            vehicleService.searchVehiclesByBrandAndRangeOfYear(brand, yearFrom, yearTo);
        });
    }

    @Test
    void averageSpeedVechicle () throws Exception{
        //Assert
        List<Vehicle> vehicles = VehicleGeneratorUtils.getVehiclesPontiac();
        String brand = "Honda";
        when(vehicleRepository.findVehiclesByBrand(brand)).thenReturn(vehicles);
        VehicleAvgSpeedByBrandDto excpectedAverageSpeed = new VehicleAvgSpeedByBrandDto(150.0);
        //act
        VehicleAvgSpeedByBrandDto actualAverageSpeed =  vehicleService.calculateAvgSpeedByBrand(brand);
        //Assert
        assertEquals(excpectedAverageSpeed, actualAverageSpeed);
        verify(vehicleRepository, atLeastOnce()).findVehiclesByBrand(brand);
    }

    @Test
    @DisplayName("Should throw NotFoundException if no vehicle is found")
    void averageSpeedVechicle_NOT_FOUND(){
       //Arr
        String brand = "Honda";
        when(vehicleRepository.findVehiclesByBrand(brand)).thenReturn(
                new ArrayList<>()
        );
        // Act + Assert
        assertThrows(NotFoundException.class, () -> {
            vehicleService.calculateAvgSpeedByBrand(brand);
        });

    }
}


