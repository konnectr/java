package com.example.demo.persistence.dao;
import java.util.List;

import com.example.demo.persistence.model.VehicleDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface VehicleDtoRepository extends JpaRepository<VehicleDto, String> {
    long countByVehicleType(String vehicleType);
    long countByMarque(String vehicleMarque);
    List<VehicleDto> findByVehicleTypeAndMarqueAndModelAndEngineAndStatus(@Param("vehicleType") String vehicleType,@Param("marque") String Marque,@Param("model") String Model,@Param("engine") String engine,@Param("status") String Status);
    List<VehicleDto> findByVehicleTypeAndMarqueAndModelAndEngine(@Param("vehicleType") String vehicleType,@Param("marque") String Marque,@Param("model") String Model,@Param("engine") String Engine);
}

