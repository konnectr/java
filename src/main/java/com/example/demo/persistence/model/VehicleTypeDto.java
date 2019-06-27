package com.example.demo.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Аккаунт пользователя. Объект-отображение таблицы user_account в базе данных.
 */
@ToString
@Data
@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class VehicleTypeDto {
	/**
	 * Аннотация обозначающая первичный ключ.
	 */
	@Id
	/**
	 * Генерация первичного ключа при вставке объекта в БД.
	 */
	String name;
	/**
	 * Поля объекта, соответствующие полям таблицы в БД.
	 */
	long count;


}
