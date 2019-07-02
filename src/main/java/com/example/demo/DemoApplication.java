package com.example.demo;

import com.example.demo.controller.MyRestVehicleController;
import com.example.demo.persistence.dao.VehicleDtoRepository;
import com.example.demo.persistence.dao.VehicleMarqueDtoRepository;
import com.example.demo.persistence.dao.VehicleStatusDtoRepository;
import com.example.demo.persistence.dao.VehicleTypeDtoRepository;
import com.example.demo.persistence.model.VehicleDto;
import com.example.demo.persistence.model.VehicleMarqueDto;
import com.example.demo.persistence.model.VehicleStatusDto;
import com.example.demo.persistence.model.VehicleTypeDto;
import org.hibernate.annotations.Proxy;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@SpringBootApplication
@EnableScheduling
public class DemoApplication {
	@Autowired
	private VehicleDtoRepository vehicleDtoRepository;
	@Autowired
	private VehicleTypeDtoRepository vehicleTypeDtoRepository;
	@Autowired
	private VehicleMarqueDtoRepository vehicleMarqueDtoRepository;
	@Autowired
	private VehicleStatusDtoRepository vehicleStatusDtoRepository;
	public static final org.slf4j.Logger logger = LoggerFactory.getLogger(MyRestVehicleController.class);
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	public String getCurrentTimeStamp() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}
	@Component
	class DemoCommandLineRunner implements CommandLineRunner {


		@Override
		public void run(String... args) throws Exception {
			if (vehicleDtoRepository.findAll().isEmpty())
			{
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
			VehicleDto car1 = new VehicleDto();
			car1.setGuid(UUID.randomUUID().toString());
			car1.setVehicleType("jet");
			car1.setMarque("isdera");
			car1.setModel("imperator 108i");
			car1.setEngine("mercedes V8");
			car1.setEnginePowerBhp("390");
			car1.setTopSpeedMph(171);
			car1.setCostUsd(135000);
			car1.setPrice(15000);
			car1.setDataInsert(getCurrentTimeStamp());
			car1.setStatus("sold");
			vehicleDtoRepository.save(car1);
			VehicleDto car2 = new VehicleDto();
			car2.setGuid(UUID.randomUUID().toString());
			car2.setVehicleType("ship");
			car2.setMarque("isdera");
			car2.setModel("imperator 108i");
			car2.setEngine("mercedes V8");
			car2.setEnginePowerBhp("390");
			car2.setTopSpeedMph(171);
			car2.setCostUsd(135000);
			car2.setPrice(15000);
			car2.setDataInsert(getCurrentTimeStamp());
			car2.setStatus("sold");
			vehicleDtoRepository.save(car2);
			VehicleDto car3 = new VehicleDto();
			car3.setGuid(UUID.randomUUID().toString());
			car3.setVehicleType("helicopter");
			car3.setMarque("isdera");
			car3.setModel("imperator 108i");
			car3.setEngine("mercedes V8");
			car3.setEnginePowerBhp("390");
			car3.setTopSpeedMph(171);
			car3.setCostUsd(135000);
			car3.setPrice(15000);
			car3.setDataInsert(getCurrentTimeStamp());
			car3.setStatus("reserved");
			vehicleDtoRepository.save(car3);
			}
			if (vehicleTypeDtoRepository.findAll().isEmpty())
			{
				VehicleTypeDto type = new VehicleTypeDto("super",1);
				VehicleTypeDto type1 = new VehicleTypeDto("jet",1);
				VehicleTypeDto type2 = new VehicleTypeDto("shit",1);
				VehicleTypeDto type3 = new VehicleTypeDto("helicopter",1);
				vehicleTypeDtoRepository.save(type);
				vehicleTypeDtoRepository.save(type1);
				vehicleTypeDtoRepository.save(type2);
				vehicleTypeDtoRepository.save(type3);
			}
			if (vehicleStatusDtoRepository.findAll().isEmpty())
			{
				VehicleStatusDto status = new VehicleStatusDto("in stock",1);
				VehicleStatusDto status1 = new VehicleStatusDto("sold",2);
				VehicleStatusDto status2 = new VehicleStatusDto("resrved",1);
				vehicleStatusDtoRepository.save(status);
				vehicleStatusDtoRepository.save(status1);
				vehicleStatusDtoRepository.save(status2);
			}
			if (vehicleMarqueDtoRepository.findAll().isEmpty())
			{
				VehicleMarqueDto marque = new VehicleMarqueDto("isdera",4);
				vehicleMarqueDtoRepository.save(marque);
			}

		}

	}
	@Scheduled(fixedDelay = 5000)
	public void priceNull() {

	}
}
