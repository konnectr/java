package com.example.demo.persistence.service;

import com.example.demo.persistence.dao.VehicleDtoRepository;
import com.example.demo.persistence.model.VehicleDto;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class VehicleDtoServicelmpl implements VehicleDtoService {


    @Autowired
    private VehicleDtoRepository vehicleRepository;

    @Override
    public List<VehicleDto> listAllVehicles() {
        List<VehicleDto> vehicleList = new ArrayList<>();

        vehicleRepository.findAll().forEach(vehicle -> {
            vehicleList.add(new VehicleDto(vehicle.getGuid(), vehicle.getVehicleType(), vehicle.getMarque() , vehicle.getModel(),vehicle.getEngine(),vehicle.getEnginePowerBhp(),vehicle.getTopSpeedMph(),vehicle.getCostUsd(),vehicle.getPrice(),vehicle.getDataInsert(),vehicle.getDatePurchase(),vehicle.getStatus()));
           // vehicleList.add((vehicleRepository.getOne(vehicleDto.getGuid())));
        });

        return vehicleList;
    }
}
