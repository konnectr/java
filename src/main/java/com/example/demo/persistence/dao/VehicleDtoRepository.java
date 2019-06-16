package com.example.demo.persistence.dao;

import com.example.demo.persistence.model.VehicleDto;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Интерфейс - репозиторий
 * 
 * @author rybakov
 *
 */
public interface VehicleDtoRepository extends JpaRepository<VehicleDto, String> {

}
