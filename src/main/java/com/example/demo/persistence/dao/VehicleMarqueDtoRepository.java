package com.example.demo.persistence.dao;

import com.example.demo.persistence.model.VehicleMarqueDto;
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
public interface VehicleMarqueDtoRepository extends JpaRepository<VehicleMarqueDto, String> {
 

}
