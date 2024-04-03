package com.bootcampW22.EjercicioGlobal.utils;

import com.bootcampW22.EjercicioGlobal.dto.VehicleDto;
import com.bootcampW22.EjercicioGlobal.entity.Vehicle;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class VehicleGeneratorUtils {
    public static List<Vehicle> getVehiclesByYearAndColor() throws Exception{
        File file;
        ObjectMapper objectMapper = new ObjectMapper();
        List<Vehicle> vehicles ;

        file= ResourceUtils.getFile("classpath:vehicles_orange_1992.json");
        return  objectMapper.readValue(file,new TypeReference<List<Vehicle>>(){});
    }
    public static List<Vehicle> getVehiclesPontiac() throws Exception{
        File file;
        ObjectMapper objectMapper = new ObjectMapper();
        List<Vehicle> vehicles ;

        file= ResourceUtils.getFile("classpath:vehicle_pontiac.json");
        return  objectMapper.readValue(file,new TypeReference<List<Vehicle>>(){});
    }



    public  static List<VehicleDto> getVehiclesDtoByBrandAndYearRange() throws Exception{
        File file;
        ObjectMapper objectMapper = new ObjectMapper();
        List<VehicleDto> vehicles ;

        file= ResourceUtils.getFile("classpath:vehicle_brand_year_range.json");
        return objectMapper.readValue(file,new TypeReference<>(){});
    }

    public  static List<Vehicle> getVehiclesByBrandAndYearRange() throws Exception{
        File file;
        ObjectMapper objectMapper = new ObjectMapper();
        List<Vehicle> vehicles ;

        file= ResourceUtils.getFile("classpath:vehicle_brand_year_range.json");
        return objectMapper.readValue(file,new TypeReference<>(){});
    }

    public static List<Vehicle> getVehiclePontiacBeetween1990And1993() throws IOException {
        File file;
        ObjectMapper objectMapper = new ObjectMapper();
        List<Vehicle> vehicles ;
        file= ResourceUtils.getFile("classpath:vehicles_pontiac_1990_1993.json");
        return objectMapper.readValue(file,new TypeReference<List<Vehicle>>(){});
    }
    public static List<Vehicle> getVehicleWeight10And15() throws IOException {
        File file;
        ObjectMapper objectMapper = new ObjectMapper();
        List<Vehicle> vehicles ;
        file= ResourceUtils.getFile("classpath:vehicles_weight_10_15.json");
        return objectMapper.readValue(file,new TypeReference<List<Vehicle>>(){});
    }
}
