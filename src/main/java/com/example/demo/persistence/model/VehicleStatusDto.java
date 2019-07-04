package com.example.demo.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * VehicleStatusDto . Объект - отображение таблице vehicle_status_dto в базе данных
 */
@ToString
@Data
@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class VehicleStatusDto {
	/**
	 * Аннотация обозначающая первичный ключ
	 */
	@Id
	String name;
	/**
	 * Поле объекта , соответствующие полям таблицы в БД
	 */
	long count;
	/**
	 * Конструктор по умолчанию
	 */
	VehicleStatusDto(){}
	public VehicleStatusDto(String name,long count)
	{
		this.name=name;
		this.count=count;
	}
}
