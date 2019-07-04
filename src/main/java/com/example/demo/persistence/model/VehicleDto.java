package com.example.demo.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * VehicleDto . Объект - отображение таблице Vehicle_Dto в базе данных
 */
@ToString
@Data
@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class VehicleDto {
    /**
     * Обозначает первичный ключ бд
     */
    @Id
    /**
     * Генерация первичного ключа при вставке в БД.
     */
    @GeneratedValue(generator="uuid") // Генератор UUID
    @GenericGenerator(name="uuid",strategy = "uuid2")
    String guid;
    /**
     * Поля объекта, соответствующие полям таблицы в БД.
     */
    String vehicleType;
    String marque;
    String model;
    String engine;
    String enginePowerBhp;
    int topSpeedMph;
    int costUsd;
    int price;
    String dataInsert;
    String datePurchase;
    String status;

}
