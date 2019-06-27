package com.example.demo.persistence.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;





/**
 * Аккаунт пользователя. Объект-отображение таблицы user_account в базе данных.
 */
@ToString
@Data
@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class VehicleDto {

    /**
     * Аннотация обозначающая первичный ключ.
     */
    @Id
    @GeneratedValue(generator="uuid")
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
    public VehicleDto(){}
    public VehicleDto(String guid,  String vehicleType, String marque, String model,String engine,String enginePowerBhp,int topSpeedMph,int costUsd,int price,String dataInsert,String datePurchase,String status) {
        this.guid=guid;
        this.vehicleType=vehicleType;
        this.marque=marque;
        this.model = model;
        this.engine = engine;
        this.enginePowerBhp=enginePowerBhp;
        this.topSpeedMph=topSpeedMph;
        this.costUsd=costUsd;
        this.price=price;
        this.dataInsert=dataInsert;
        this.datePurchase=datePurchase;
        this.status=status;
    }
}
