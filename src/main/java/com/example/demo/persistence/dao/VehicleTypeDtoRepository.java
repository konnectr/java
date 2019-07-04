package com.example.demo.persistence.dao;

import com.example.demo.persistence.model.VehicleTypeDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Интерфейс - репозитория
 *
 * @author Smirnov
 *
 *
 */
@Repository
public interface VehicleTypeDtoRepository extends JpaRepository<VehicleTypeDto, String> {


}
