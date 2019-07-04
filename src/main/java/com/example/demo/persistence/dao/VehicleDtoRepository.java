package com.example.demo.persistence.dao;
import java.util.List;

import com.example.demo.persistence.model.VehicleDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Интерфейс - репозитория
 *
 * @author Smirnov
 *
 *
 */
@Repository
public interface VehicleDtoRepository extends JpaRepository<VehicleDto, String> {
    /**
     * @param vehicleType принимаем объект который будем считать
     * @return количество элементов vehicleType в столбце VehicleType
     */
    long countByVehicleType(String vehicleType);

    /**
     * Marque
     * @param vehicleMarque принимаем объект который будем считать
     * @return количество элеметов vehicleMarque в столбце Marque
     */
    long countByMarque(String vehicleMarque);

    /**
     * Status
     * @param vehicleStatus принимаем объект который будем считать
     * @return количество элементов vehicleStatus в столбце Status
     */
    long countByStatus(String vehicleStatus);

    /**
     * Запрос к БД в котором выбирается случайный элемент
     * @return
     */
    @Query(value = "SELECT guid FROM vehicle_dto ORDER BY RANDOM() limit 1",nativeQuery=true)
    /**
     * Запрос к БД в котором выбирается случайный элемент
     */
    String getRandomGuid();

    /**
     * Функция для поиска
     * @param vehicleType параметр для поиска
     * @param Marque параметр для поиска
     * @param Model параметр для поиска
     * @param engine параметр для поиска
     * @param Status параметр для поиска
     * @return найденные объекты по параметрам
     */
    List<VehicleDto> findByVehicleTypeAndMarqueAndModelAndEngineAndStatus(@Param("vehicleType") String vehicleType,@Param("marque") String Marque,@Param("model") String Model,@Param("engine") String engine,@Param("status") String Status); //Функция для поиска со статусом

    /**
     * Функция для поиска
     * @param vehicleType параметр для поиска
     * @param Marque параметр для поиска
     * @param Model параметр для поиска
     * @param Engine параметр для поиска
     * @return найденные объекты по параметрам
     */
    List<VehicleDto> findByVehicleTypeAndMarqueAndModelAndEngine(@Param("vehicleType") String vehicleType,@Param("marque") String Marque,@Param("model") String Model,@Param("engine") String Engine); // функция без статуса
}

