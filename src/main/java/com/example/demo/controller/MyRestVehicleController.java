package com.example.demo.controller;


import com.example.demo.persistence.dao.VehicleMarqueDtoRepository;
import com.example.demo.persistence.dao.VehicleStatusDtoRepository;
import com.example.demo.persistence.dao.VehicleTypeDtoRepository;
import com.example.demo.persistence.model.VehicleMarqueDto;
import com.example.demo.persistence.model.VehicleTypeDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.example.demo.persistence.dao.VehicleDtoRepository;
import com.example.demo.persistence.model.VehicleDto;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Класс-контроллер приложения. 
 * Отвечает за обработку запросов по адресу http://localhost:8080/my
 * 
 * @author rybakov
 *
 */
@RestController
@RequestMapping("/vehicle")
public class MyRestVehicleController {

	public static final Logger logger = LoggerFactory.getLogger(MyRestVehicleController.class);

	public MyRestVehicleController() {	}

	@Autowired
	private VehicleDtoRepository vehicleDtoRepository;

	@Autowired
	private VehicleTypeDtoRepository vehicleTypeDtoRepository;
	@Autowired
	private VehicleMarqueDtoRepository vehicleMarqueDtoRepository;
	@Autowired
	private VehicleStatusDtoRepository vehicleStatusDtoRepository;

	public String getCurrentTimeStamp() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}

	@PostMapping(produces = "application/json", consumes = "application/json")
	public ResponseEntity<VehicleDto> postVehicle(@RequestBody VehicleDto vehicleDto, HttpServletRequest request) {
		/**
		 * Сохраняем машину  в базу данных.
		 */
		vehicleDto.setDataInsert(getCurrentTimeStamp());

		if (vehicleDto.getStatus()==null) vehicleDto.setStatus("in stock");
		vehicleTypeDtoRepository.findById(vehicleDto.getVehicleType())
				.orElse(vehicleTypeDtoRepository.save(new VehicleTypeDto(vehicleDto.getVehicleType(),1)));
		vehicleMarqueDtoRepository.findById(vehicleDto.getMarque())
				.orElse(vehicleMarqueDtoRepository.save(new VehicleTypeDto(vehicleDto.getMarque(),1)));
		vehicleTypeDtoRepository.findById(vehicleDto.getStatus())
				.orElse(vehicleStatusDtoRepository.save(new VehicleTypeDto(vehicleDto.getStatus(),1)));

		vehicleDto = vehicleDtoRepository.save(vehicleDto);

		/**
		 * Рапортуем о том, что машина создана.
		 */
		logger.info("Vehicle with Guid={} was created!", vehicleDto.getGuid());

		/**
		 * Возвращаем ответ.
		 */
		return ResponseEntity
				.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(vehicleDto);
	}

	@PutMapping(value = "/{guid}")
	public ResponseEntity<VehicleDto> updateVehicle(@PathVariable("guid") String guid,@Valid @RequestBody VehicleDto vehicleDtodetalis, HttpServletRequest request)throws EntityNotFoundException
	{
		VehicleDto vehicleDto=vehicleDtoRepository.findById(guid)
				.orElseThrow(()-> new EntityNotFoundException(guid));
		vehicleDto.setVehicleType(vehicleDtodetalis.getVehicleType());
		vehicleDto.setMarque(vehicleDtodetalis.getMarque());
		vehicleDto.setModel(vehicleDtodetalis.getModel());
		vehicleDto.setEngine(vehicleDtodetalis.getEngine());
		vehicleDto.setEnginePowerBhp(vehicleDtodetalis.getEnginePowerBhp());
		vehicleDto.setTopSpeedMph(vehicleDtodetalis.getTopSpeedMph());
		vehicleDto.setCostUsd(vehicleDtodetalis.getCostUsd());
		vehicleDto.setPrice(vehicleDtodetalis.getPrice());
		vehicleDto.setStatus(vehicleDtodetalis.getStatus());
		vehicleDto.setDataInsert(vehicleDtodetalis.getDataInsert());
		vehicleDto.setDatePurchase(getCurrentTimeStamp());
		return ResponseEntity
				.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(vehicleDtoRepository.save(vehicleDto));
	}

	@GetMapping(value = "/all")
	public ResponseEntity<List<VehicleDto>> allVehicle() {
		return ResponseEntity
				.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(vehicleDtoRepository.findAll());

	}
	@GetMapping(value= "/search")
	public ResponseEntity<List<VehicleDto>> searchVehicle(@RequestParam("vehicleType") String vehicleType,
														  @RequestParam("marque") String marque,
														  @RequestParam("model") String model,
														  @RequestParam("engine") String engine,
														  @RequestParam("status") String status	) {
		if(status!="")
		{
			return ResponseEntity
					.ok()
					.contentType(MediaType.APPLICATION_JSON)
					.body(vehicleDtoRepository.findByVehicleTypeAndMarqueAndModelAndEngineAndStatus(vehicleType,marque,model,engine,status));
		}
		else
		{
			return ResponseEntity
					.ok()
					.contentType(MediaType.APPLICATION_JSON)
					.body(vehicleDtoRepository.findByVehicleTypeAndMarqueAndModelAndEngine(vehicleType,marque,model,engine));
		}

	}

	@GetMapping(value = "/{guid}")
	public ResponseEntity<VehicleDto> getVehicle(@PathVariable(value = "guid") String guid, HttpServletRequest request)
	{

		VehicleDto vehicleDto = vehicleDtoRepository.getOne(guid);
		return ResponseEntity
			.ok()
			.contentType(MediaType.APPLICATION_JSON)
				.body(vehicleDto);
	}
	@GetMapping(value = "/types")
	public ResponseEntity<List<VehicleTypeDto>> typesVehicle() throws EntityNotFoundException
	{
		long supercar = vehicleDtoRepository.countByVehicleType("supercar");
		VehicleTypeDto vehicleTypeDto=vehicleTypeDtoRepository.findById("supercar")
				.orElseThrow(()-> new EntityNotFoundException("supercar"));
		vehicleTypeDto.setCount(supercar);
		long jet = vehicleDtoRepository.countByVehicleType("jet");
		vehicleTypeDto=vehicleTypeDtoRepository.findById("jet")
				.orElseThrow(()-> new EntityNotFoundException("jet"));
		vehicleTypeDto.setCount(jet);
		long ship = vehicleDtoRepository.countByVehicleType("ship");
		vehicleTypeDto=vehicleTypeDtoRepository.findById("ship")
				.orElseThrow(()-> new EntityNotFoundException("ship"));
		vehicleTypeDto.setCount(ship);
		long helicopter = vehicleDtoRepository.countByVehicleType("helicopter");
		vehicleTypeDto=vehicleTypeDtoRepository.findById("helicopter")
				.orElseThrow(()-> new EntityNotFoundException("helicopter"));
		vehicleTypeDto.setCount(helicopter);
		long submarine = vehicleDtoRepository.countByVehicleType("submarine");
		vehicleTypeDto=vehicleTypeDtoRepository.findById("submarine")
				.orElseThrow(()-> new EntityNotFoundException("submarine"));
		vehicleTypeDto.setCount(submarine);
		return ResponseEntity
				.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(vehicleTypeDtoRepository.findAll());
	}
	/**@GetMapping(value = "/model")
	public ResponseEntity<List<VehicleTypeDto>> typesModel() throws EntityNotFoundException
	{
		long supercar = vehicleDtoRepository.countByVehicleType("supercar");
		VehicleTypeDto vehicleTypeDto=vehicleTypeDtoRepository.findById("supercar")
				.orElseThrow(()-> new EntityNotFoundException("supercar"));
		vehicleTypeDto.setCount(supercar);
		long jet = vehicleDtoRepository.countByVehicleType("jet");
		vehicleTypeDto=vehicleTypeDtoRepository.findById("jet")
				.orElseThrow(()-> new EntityNotFoundException("jet"));
		vehicleTypeDto.setCount(jet);
		long ship = vehicleDtoRepository.countByVehicleType("ship");
		vehicleTypeDto=vehicleTypeDtoRepository.findById("ship")
				.orElseThrow(()-> new EntityNotFoundException("ship"));
		vehicleTypeDto.setCount(ship);
		long helicopter = vehicleDtoRepository.countByVehicleType("helicopter");
		vehicleTypeDto=vehicleTypeDtoRepository.findById("helicopter")
				.orElseThrow(()-> new EntityNotFoundException("helicopter"));
		vehicleTypeDto.setCount(helicopter);
		long submarine = vehicleDtoRepository.countByVehicleType("submarine");
		vehicleTypeDto=vehicleTypeDtoRepository.findById("submarine")
				.orElseThrow(()-> new EntityNotFoundException("submarine"));
		vehicleTypeDto.setCount(submarine);
		return ResponseEntity
				.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(vehicleTypeDtoRepository.findAll());
	}**/
}