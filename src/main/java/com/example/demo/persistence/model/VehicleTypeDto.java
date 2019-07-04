package com.example.demo.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@ToString
@Data
@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class VehicleTypeDto {
	@Id
	String name;
	long count;
	VehicleTypeDto(){}
	public VehicleTypeDto(String name,long count)
    {
        this.name=name;
        this.count=count;
    }

}
