package com.example.demo.service;

import com.example.demo.controller.MyRestVehicleController;
import com.example.demo.persistence.dao.VehicleDtoRepository;
import com.example.demo.persistence.model.VehicleDto;
import org.hibernate.annotations.Proxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class ScheduledTasks {
    public static final Logger logger = LoggerFactory.getLogger(MyRestVehicleController.class);
    @Autowired
    private VehicleDtoRepository vehicleDtoRepository;
    @Scheduled(fixedDelay = 50000)
    public void priceNull() {

        VehicleDto vehicleDto = vehicleDtoRepository.getOne(vehicleDtoRepository.getValues());
        vehicleDto.setPrice(0);
        logger.info("Vehicle with Guid={} was CostUsd 0", vehicleDto.getGuid());
        vehicleDtoRepository.save(vehicleDto);
    }
}
