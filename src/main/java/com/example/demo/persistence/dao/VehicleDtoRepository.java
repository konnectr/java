package com.example.demo.persistence.dao;

import com.example.demo.persistence.model.VehicleDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Интерфейс - репозиторий
 * 
 * @author rybakov
 *
 */
@Repository
public interface VehicleDtoRepository extends JpaRepository<VehicleDto, String> {

}
