package com.example.demo.persistence.dao;

import com.example.demo.persistence.model.VehicleStatusDto;
import com.example.demo.persistence.model.VehicleTypeDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VehicleStatusDtoRepository extends JpaRepository<VehicleStatusDto, String> {


}
