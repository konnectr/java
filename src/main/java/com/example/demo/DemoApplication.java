package com.example.demo;

import com.example.demo.persistence.dao.VehicleDtoRepository;
import com.example.demo.persistence.model.VehicleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	public String getCurrentTimeStamp() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}
	@Component
	class DemoCommandLineRunner implements CommandLineRunner {

		@Autowired
		private VehicleDtoRepository vehicleDtoRepository;

		@Override
		public void run(String... args) throws Exception {
			VehicleDto car = new VehicleDto();
			car.setGuid(UUID.randomUUID().toString());
			car.setVehicleType("supercar");
			car.setMarque("isdera");
			car.setModel("imperator 108i");
			car.setEngine("mercedes V8");
			car.setEnginePowerBhp("390");
			car.setTopSpeedMph(171);
			car.setCostUsd(135000);
			car.setPrice(15000);
			car.setDataInsert(getCurrentTimeStamp());
			car.setStatus("in stock");
			vehicleDtoRepository.save(car);
		}
	}
}
