package com.example.demo.persistence.dao;

import com.example.demo.persistence.model.VehicleMarqueDto;
import com.example.demo.persistence.model.VehicleTypeDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface VehicleMarqueDtoRepository extends JpaRepository<VehicleMarqueDto, String> {
 

}
