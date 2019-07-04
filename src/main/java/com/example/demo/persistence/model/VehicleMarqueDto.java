package com.example.demo.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * VehicleMarqueDto . Объект - отображение таблице vehicle_marque_dto в базе данных
 */
@ToString
@Data
@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class VehicleMarqueDto {
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
	VehicleMarqueDto(){}
	public VehicleMarqueDto(String name,long count)
	{
		this.name=name;
		this.count=count;
	}
}
