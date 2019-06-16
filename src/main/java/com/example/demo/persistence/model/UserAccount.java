package com.example.demo.persistence.model;

import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.ToString;

/**
 * Аккаунт пользователя. Объект-отображение таблицы user_account в базе данных.
 */
@ToString
@Data
@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class UserAccount {
	/**
	 * Аннотация обозначающая первичный ключ.
	 */
	@Id
	/**
	 * Генерация первичного ключа при вставке объекта в БД.
	 */
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	/**
	 * Поля объекта, соответствующие полям таблицы в БД.
	 */
	String username;
	String password;
	String email;
}
