package com.bootcampW22.EjercicioGlobal.controller;

import com.bootcampW22.EjercicioGlobal.entity.Vehicle;
import com.bootcampW22.EjercicioGlobal.utils.VehicleGeneratorUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class VehicleControllerIntegrationTest {
    @Autowired
    MockMvc mockMvc;
    ObjectWriter objectWriter = new ObjectMapper().configure(SerializationFeature.WRAP_ROOT_VALUE, false).writer();
    List<Vehicle> listOfVehicles;

    @BeforeEach
    public void loadContent() throws IOException {
        File file;
        ObjectMapper objectMapper = new ObjectMapper();
        List<Vehicle> vehicles ;
        file= ResourceUtils.getFile("classpath:vehicles_100_test.json");
        vehicles = objectMapper.readValue(file,new TypeReference<List<Vehicle>>(){});
        listOfVehicles = vehicles;
    }

    @Test
    void getVehicles() throws Exception {
        //Arrange
        List<Vehicle> expectedVehicles = this.listOfVehicles;
        //Act
        MvcResult actualVehicles = this.mockMvc
                .perform(get("/vehicles"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        //assert
        assertEquals(objectWriter.writeValueAsString(expectedVehicles), actualVehicles.getResponse().getContentAsString(StandardCharsets.UTF_8));

    }

    @Test
    void getVehiclesByColorAndYear() throws Exception {
        //Arrange
        List<Vehicle> expectedResponse = VehicleGeneratorUtils.getVehiclesByYearAndColor();
        String color = "orange";
        String year = "1992";
        //Act
        MvcResult actualVehicles = this.mockMvc
                .perform(get("/vehicles/color/{color}/year/{year}", color, year))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        //Assert
        assertEquals(objectWriter.writeValueAsString(expectedResponse), actualVehicles.getResponse().getContentAsString(StandardCharsets.UTF_8));
    }

    @Test
    void getVehiclesByColorAndRangeOfYear() throws Exception {
        //Arrange
        List<Vehicle> expectedVehicles = VehicleGeneratorUtils.getVehiclePontiacBeetween1990And1993();
        String brand = "Pontiac";
        String startingYear = "1990";
        String endingYear = "1993";
        //Act
        MvcResult actualVehicles = mockMvc.perform(get("/vehicles/brand/{brand}/between/{start_year}/{end_year}", brand, startingYear, endingYear))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        //Assert
        assertEquals(objectWriter.writeValueAsString(expectedVehicles), actualVehicles.getResponse().getContentAsString(StandardCharsets.UTF_8));

    }

    @Test
    void getAverageSpeedByBrand() throws Exception {
        //Arrange
        String expectedSpeed = "168.11";
        String brand = "pontiac";
        //Act and assert
        mockMvc.perform(get("/vehicles/average_speed/brand/{brand}", brand))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.average_speed").value(expectedSpeed));

    }

    @Test
    void getAverageCapacityByBrand()  throws Exception{
        //Arrange
        String expectedAverageCapacity = "3.84";
        String brand = "pontiac";
        //Act and Assert
        mockMvc.perform(get("/vehicles/average_capacity/brand/{branc}",brand ))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.average_capacity").value(expectedAverageCapacity));
    }

    @Test
    void getVehiclesByRangeOfWeight() throws Exception {
        //arrange
        String minWeight = "10";
        String maxWeight = "15";
        List<Vehicle> expectedResponse = VehicleGeneratorUtils.getVehicleWeight10And15();
        //Act and
        MvcResult actualResponse = mockMvc.perform(get("/vehicles/weight").queryParam("min", minWeight).param("max", maxWeight))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        //Assert
        assertEquals(objectWriter.writeValueAsString(expectedResponse), actualResponse.getResponse().getContentAsString(StandardCharsets.UTF_8));
    }
}