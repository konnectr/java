package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.example.demo.persistence.dao.VehicleDtoRepository;
import com.example.demo.persistence.model.VehicleDto;

import java.text.SimpleDateFormat;
import java.util.Date;

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

	public MyRestVehicleController() {
		logger.info("MyRestController was created!");
	}

	@Autowired
	/**
	 * Поле репозитория, которое будет заполнено при 
	 */
	//private UserAccountRepository userAccountRepository;
	private VehicleDtoRepository vehicleDtoRepository;

	public String getCurrentTimeStamp() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}
	/**
	 * Метод, отвечающий за обработку post запросов по адресу
	 * http://localhost:8080/my Метод принимает объект типа UserAccount
	 * в теле запроса замаршаленный в json объект (Маршалинг - процесс
	 * преобразования объекта в json/xml/yaml/tree)
	 *
	 * Пример запроса:
	 * {
	 *   "username":"user",
	 *   "password":"qwerty"
	 * }
	 *
	 * @param vehicleDto принимаемый объект. spring демаршалит его за нас.
	 * @param request параметры запроса.
	 * @return объект, вставленный в базу данных.
	 */

	@PostMapping(produces = "application/json", consumes = "application/json")
	public ResponseEntity<VehicleDto> postVehicle(@RequestBody VehicleDto vehicleDto, HttpServletRequest request) {
		/**
		 * Сохраняем машину  в базу данных.
		 */
		vehicleDto.setDataInsert(getCurrentTimeStamp());
		vehicleDto.setDatePurchase(getCurrentTimeStamp());
		if (vehicleDto.getStatus()==null) vehicleDto.setStatus("in stock");
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
      /**
	    * @param vehicleDto принимаемый объект. spring демаршалит его за нас.
	    * @param request параметры запроса.
	    * @return объект, вставленный в базу данных.
	    **/

	@PutMapping(produces = "application/json", consumes = "application/json")
	public ResponseEntity<VehicleDto> putVehicle(@RequestBody VehicleDto vehicleDto, HttpServletRequest request)
	{


		vehicleDto=vehicleDtoRepository.save(vehicleDto);


		logger.info("Vehicle with Guid={} was update!", vehicleDto.getGuid());
		return ResponseEntity
				.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(vehicleDto);
	}
	/**
	 * Метод, отвечающий за обработку get запросов по адресу
	 * http://localhost:8080/my/{id} Метод принимает id аккаунта пользователя
	 * и возвращает пользователя из базы данных в виде json объекта.
	 * @param request параметры запроса.
	 * @return пользователь из базы данных в виде json
	 */
	@GetMapping(value = "/{guid}")
	public ResponseEntity<VehicleDto> getVehicle(@PathVariable(value = "guid") String guid, HttpServletRequest request)
	{

		/**
		 * Вытаскиваем пользователя из базы по его guid.
		 */

		VehicleDto vehicleDto = vehicleDtoRepository.getOne(guid);
	//	VehicleDto vehicleDto = vehicleDtoRepository.getOne(guid);

		/**
		 * Возвращаем ответ.
		 */
		return ResponseEntity
				.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(vehicleDto);
	}
}