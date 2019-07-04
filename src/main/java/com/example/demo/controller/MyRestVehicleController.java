package com.example.demo.controller;


import com.example.demo.persistence.dao.VehicleMarqueDtoRepository;
import com.example.demo.persistence.dao.VehicleStatusDtoRepository;
import com.example.demo.persistence.dao.VehicleTypeDtoRepository;
import com.example.demo.persistence.model.VehicleMarqueDto;
import com.example.demo.persistence.model.VehicleStatusDto;
import com.example.demo.persistence.model.VehicleTypeDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.persistence.dao.VehicleDtoRepository;
import com.example.demo.persistence.model.VehicleDto;

import java.text.SimpleDateFormat;
import java.util.*;

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
		vehicleDto.setDataInsert(getCurrentTimeStamp());
		if (vehicleDto.getStatus()==null) vehicleDto.setStatus("in stock");
		vehicleDto = vehicleDtoRepository.save(vehicleDto);// Для первода из Сет в Гет статус (Чтобы гет статус был не NULL , чтобы работал подсчёт)
		VehicleTypeDto vehicleTypeDto= vehicleTypeDtoRepository.findById(vehicleDto.getVehicleType())
				.orElse(vehicleTypeDtoRepository.save(new VehicleTypeDto(vehicleDto.getVehicleType(),1)));
		 vehicleTypeDto.setCount( vehicleDtoRepository.countByVehicleType(vehicleDto.getVehicleType()));

		VehicleMarqueDto vehicleMarqueDto = vehicleMarqueDtoRepository.findById(vehicleDto.getMarque())
				.orElse(vehicleMarqueDtoRepository.save(new VehicleMarqueDto(vehicleDto.getMarque(),1)));
		vehicleMarqueDto.setCount(vehicleDtoRepository.countByMarque(vehicleDto.getMarque()));

		VehicleStatusDto vehicleStatusDto = vehicleStatusDtoRepository.findById(vehicleDto.getStatus())
				.orElse(vehicleStatusDtoRepository.save(new VehicleStatusDto(vehicleDto.getStatus(),1)));
		vehicleStatusDto.setCount(vehicleDtoRepository.countByStatus(vehicleDto.getStatus()));

		vehicleDto = vehicleDtoRepository.save(vehicleDto);
		logger.info("Vehicle with Guid={} was created!", vehicleDto.getGuid());

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

		return ResponseEntity
				.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(vehicleTypeDtoRepository.findAll());
	}
	@GetMapping(value = "/marque")
	public ResponseEntity<List<VehicleMarqueDto>> marqueVehicle() throws EntityNotFoundException
	{

		return ResponseEntity
				.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(vehicleMarqueDtoRepository.findAll());
	}
	@GetMapping(value = "/status")
	public ResponseEntity<List<VehicleStatusDto>> statusVehicle() throws EntityNotFoundException
	{

		return ResponseEntity
				.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(vehicleStatusDtoRepository.findAll());
	}
	@GetMapping(value = "/max")
	public ResponseEntity<List<?>> maxTypeVehicle() throws EntityNotFoundException
	{
		List<VehicleTypeDto> vehicleTypeDto=vehicleTypeDtoRepository.findAll();
		List<VehicleMarqueDto> vehicleMarqueDto=vehicleMarqueDtoRepository.findAll();
		List<VehicleStatusDto> vehicleStatusDto=vehicleStatusDtoRepository.findAll();
		VehicleTypeDto maxType = vehicleTypeDto.stream().max(Comparator.comparing(vehicleTypeDtol ->vehicleTypeDtol.getCount())).get();
		VehicleMarqueDto maxMarque = vehicleMarqueDto.stream().max(Comparator.comparing(vehicleMarqueDto1 -> vehicleMarqueDto1.getCount())).get();
		VehicleStatusDto maxStatus = vehicleStatusDto.stream().max(Comparator.comparing(vehicleStatusDto1 -> vehicleStatusDto1.getCount())).get();
		List<VehicleTypeDto> vehicleallDto = new ArrayList<>();
		vehicleallDto.add(new VehicleTypeDto(maxType.getName(),maxType.getCount()));
		vehicleallDto.add(new VehicleTypeDto(maxMarque.getName(),maxMarque.getCount()));
		vehicleallDto.add(new VehicleTypeDto(maxStatus.getName(),maxStatus.getCount()));
		return ResponseEntity
				.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(vehicleallDto);

	}
	@GetMapping(value = "/reverse")
	public ResponseEntity<VehicleDto> reverse()
	{
		VehicleDto vehicleDto= vehicleDtoRepository.getOne(vehicleDtoRepository.getValues());
		VehicleDto vehicleDtonew = new VehicleDto();
		vehicleDtonew.setGuid(new StringBuffer(vehicleDto.getGuid()).reverse().toString());
		vehicleDtonew.setVehicleType(new StringBuffer(vehicleDto.getVehicleType()).reverse().toString());
		vehicleDtonew.setMarque(new StringBuffer(vehicleDto.getMarque()).reverse().toString());
		vehicleDtonew.setModel(new StringBuffer(vehicleDto.getModel()).reverse().toString());
		vehicleDtonew.setEngine(new StringBuffer(vehicleDto.getEngine()).reverse().toString());
		vehicleDtonew.setEnginePowerBhp(new StringBuffer(vehicleDto.getEnginePowerBhp()).reverse().toString());
		vehicleDtonew.setTopSpeedMph(vehicleDto.getTopSpeedMph());
		vehicleDtonew.setCostUsd(vehicleDto.getCostUsd());
		vehicleDtonew.setDataInsert(vehicleDto.getDataInsert());
		vehicleDtonew.setDatePurchase(vehicleDto.getDatePurchase());
		vehicleDtonew.setStatus(new StringBuffer(vehicleDto.getStatus()).reverse().toString());
		vehicleDtoRepository.delete(vehicleDto);
		vehicleDtonew=vehicleDtoRepository.save(vehicleDtonew);
		return ResponseEntity
				.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(vehicleDtonew);
	}
}