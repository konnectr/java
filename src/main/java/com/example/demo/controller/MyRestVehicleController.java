package com.example.demo.controller;

import com.example.demo.persistence.dao.VehicleDtoRepository;
import com.example.demo.persistence.dao.VehicleMarqueDtoRepository;
import com.example.demo.persistence.dao.VehicleStatusDtoRepository;
import com.example.demo.persistence.dao.VehicleTypeDtoRepository;
import com.example.demo.persistence.model.VehicleDto;
import com.example.demo.persistence.model.VehicleMarqueDto;
import com.example.demo.persistence.model.VehicleStatusDto;
import com.example.demo.persistence.model.VehicleTypeDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Класс - контроллер приложение.
 * Отвечает за обработку запросов по адресу  http://127.0.0.1:8090/vehicle/*
 *
 * @author Smirnov
 */
@RestController
@RequestMapping("/vehicle")
public class MyRestVehicleController {
	/**
	 * Логгер. Необходим для правильного вывода сообщений из кода.
	 */
	public static final Logger logger = LoggerFactory.getLogger(MyRestVehicleController.class);
	/**
	 * Конструктор. Вызывается spring контекстом, когда контроллер создаётся.
	 */
	public MyRestVehicleController() {logger.info("MyRestController was created!");	}
	@Autowired
	/**
	 * Поле репозитория, которое будет заполнено при запуске программы
	 */
	private VehicleDtoRepository vehicleDtoRepository;
	@Autowired
	/**
	 * Поле репозитория, которое будет заполнено при запуске программы
	 */
	private VehicleTypeDtoRepository vehicleTypeDtoRepository;
	@Autowired
	/**
	 * Поле репозитория, которое будет заполнено при запуске программы
	 */
	private VehicleMarqueDtoRepository vehicleMarqueDtoRepository;
	@Autowired
	/**
	 * Поле репозитория, которое будет заполнено при запуске программы
	 */
	private VehicleStatusDtoRepository vehicleStatusDtoRepository;
	/**
	 *
	 * @return данное время в определённом формате
	 */
	public String getCurrentTimeStamp() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}

	/**
	 *	Метод , отвечающий за обработку post запросов по адресу
	 *  http://127.0.0.1:8090/vehicle/ Метод принимает объект типа VehicleDto
	 *
	 *	 Пример запроса:
	 *  {
	 *   "vehicleType":"supercar",
	 *   "marque":"Isdera",
	 *   "model":"Imperator 108i",
	 *   "engine":"Mercedes V8",
	 *   "enginePowerBhp":390,
	 *   "topSpeedMph":171,
	 *   "datePurchase":"2019-02-03 10:08:02",
	 *   "costUsd":135000,
	 *   "price":150000
	 *  }
	 *
	 * @param vehicleDto принимает объект
	 * @param request параметры запроса
	 * @return объект , вставленнный в базу данных
	 */
	@PostMapping(produces = "application/json", consumes = "application/json")
	public ResponseEntity<VehicleDto> postVehicle(@RequestBody VehicleDto vehicleDto, HttpServletRequest request) {
		vehicleDto.setDataInsert(getCurrentTimeStamp());
		/**
		 * Присваеваем Status если он не задан Статус in stock
		 */
		if (vehicleDto.getStatus()==null) vehicleDto.setStatus("in stock");
		/**
		 * Сохраняем машину, чтобы дальше можно было сохранить в другие таблици значения БД
		 */
		vehicleDto = vehicleDtoRepository.save(vehicleDto);
		updateTable(vehicleDto);
		//???7vehicleDto = vehicleDtoRepository.save(vehicleDto);
		/**
		 * Рапортуем о том , что машина создана
		 */
		logger.info("Vehicle with Guid={} was created!", vehicleDto.getGuid());
		/**
		 * Возращаем ответ.
		 */
		return ResponseEntity
				.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(vehicleDto);
	}

	private void updateTable(@RequestBody VehicleDto vehicleDto) {
		/**
		 * Происходит поиск в таблице vehicle_type_dto , если не находит то создаём новый элемент в таблице,
		 * и делаем перерасчёт количества одинаковыйх VehicleType элементов в таблице Vehicle_Dto
		 */

		VehicleTypeDto vehicleTypeDto= vehicleTypeDtoRepository.findById(vehicleDto.getVehicleType())
				.orElse(vehicleTypeDtoRepository.save(new VehicleTypeDto(vehicleDto.getVehicleType(),1)));
		vehicleTypeDto.setCount( vehicleDtoRepository.countByVehicleType(vehicleDto.getVehicleType()));
		/**
		 * Происходит поиск в таблице vehicle_marque_dto , если не находит то создаём новый элемент в таблице,
		 * и делаем перерасчёт количества одинаковыйх marque элементов в таблице Vehicle_Dto
		 */
		VehicleMarqueDto vehicleMarqueDto = vehicleMarqueDtoRepository.findById(vehicleDto.getMarque())
				.orElse(vehicleMarqueDtoRepository.save(new VehicleMarqueDto(vehicleDto.getMarque(),1)));
		vehicleMarqueDto.setCount(vehicleDtoRepository.countByMarque(vehicleDto.getMarque()));
		/**
		 * Происходит поиск в таблице vehicle_status_dto , если не находит то создаём новый элемент в таблице,
		 * и делаем перерасчёт количества одинаковыйх status элементов в таблице Vehicle_Dto
		 */
		VehicleStatusDto vehicleStatusDto = vehicleStatusDtoRepository.findById(vehicleDto.getStatus())
				.orElse(vehicleStatusDtoRepository.save(new VehicleStatusDto(vehicleDto.getStatus(),1)));
		vehicleStatusDto.setCount(vehicleDtoRepository.countByStatus(vehicleDto.getStatus()));
	}

	private void deletTable(@RequestBody VehicleDto vehicleDto)
	{
		VehicleTypeDto vehicleTypeDto = vehicleTypeDtoRepository.getOne(vehicleDto.getVehicleType());
		vehicleTypeDto.setCount(vehicleDtoRepository.countByVehicleType(vehicleDto.getVehicleType()));

		VehicleMarqueDto vehicleMarqueDto = vehicleMarqueDtoRepository.getOne(vehicleDto.getMarque());
		vehicleMarqueDto.setCount(vehicleDtoRepository.countByVehicleType(vehicleDto.getMarque()));

		VehicleStatusDto vehicleStatusDto = vehicleStatusDtoRepository.getOne(vehicleDto.getStatus());
		vehicleStatusDto.setCount(vehicleDtoRepository.countByVehicleType(vehicleDto.getStatus()));
	}

	/**
	 * Метод , отвечающий за обработку put запроса по адресу
	 * http://127.0.0.1:8090/vehicle/{guid} Метод принимает объект типа VehicleDto
	 *
	 * Пример запроса:
	 * {
	 *  "vehicleType":"supercar",
	 * 	"marque":"Isdera",
	 * 	"model":"Imperator 108i",
	 * 	"engine":"Mercedes V8",
	 * 	"enginePowerBhp":390,
	 * 	"topSpeedMph":171,
	 * 	"datePurchase":"2019-02-03 10:08:02",
	 *  "costUsd":135000,
	 *  "price":150000
	 * }
	 *
	 * @param guid первичный ключ по которому определяем какой объект обновляем
	 * @param vehicleDtodetalis принимаем объект
	 * @param request параметры запроса
	 * @return объект , сохраненённый в БД
	 * @throws EntityNotFoundException
	 */
	@PutMapping(value = "/{guid}")
	public ResponseEntity<VehicleDto> updateVehicle(@PathVariable("guid") String guid,@Valid @RequestBody VehicleDto vehicleDtodetalis, HttpServletRequest request)throws EntityNotFoundException
	{
		/**
		 * Функция для взятия элемента для обновления
		 */
		VehicleDto vehicleDto=vehicleDtoRepository.getOne(guid);

		/**
		 * Блок обновлений данных в БД
		 */
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
		/**
		 * Обновляем 3 таблице
		 */
		updateTable(vehicleDto);
		/**
		 * Сохраняем изменения в бд
		 */
		vehicleDto=vehicleDtoRepository.save(vehicleDto);
		/**
		 * Рапортуем о том , что запись обновлена
		 */
		logger.info("Vehicle with Guid={} was update!", vehicleDto.getGuid());

		/**
		 * Возращаем ответ
		 */
		return ResponseEntity
				.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(vehicleDtoRepository.save(vehicleDto));
	}

	/**
	 * Метод , отвечающий за обработку get запроса по адресу
	 * http://127.0.0.1:8090/vehicle/all Метод принимает объект типа VehicleDto
	 *
	 *
	 * @return Возращаем все объекты находящийся в Таблице VehicleDto в JSON объектах
	 */
	@GetMapping(value = "/all")
	public ResponseEntity<List<VehicleDto>> allVehicle() {
		/**
		 * Возращаем все найденные объекты в JSON
		 */
		logger.info("All Vehicles return!");
		return ResponseEntity
				.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(vehicleDtoRepository.findAll());

	}

	/**
	 * Метод , отвечающий за обработку get запроса по адресу
	 *
	 * http://127.0.0.1:8090/vehicle/search?vehicleType=supercar&marque=isdera&model=imperator%20108i&engine=Mesedes%20V8&status=sold
	 * либо
	 * http://127.0.0.1:8090/vehicle/search?vehicleType=supercar&marque=isdera&model=imperator%20108i&engine=Mesedes%20V8&status=
	 *
	 * Зависит от регистра
	 * @param vehicleType параметр для поиска
	 * @param marque параметр для поиска
	 * @param model параметр для поиска
	 * @param engine параметр для поиска
	 * @param status параметр для поиска
	 * @return результаты поиска в JSON объектах
	 */
	@GetMapping(value= "/search")
	public ResponseEntity<List<VehicleDto>> searchVehicle(@RequestParam("vehicleType") String vehicleType,
														  @RequestParam("marque") String marque,
														  @RequestParam("model") String model,
														  @RequestParam("engine") String engine,
														  @RequestParam("status") String status	) {
		if(status!="")
		{
			logger.info("Search is performed by parameters:vehicleType,marque,model,engine,status");
			return ResponseEntity
					.ok()
					.contentType(MediaType.APPLICATION_JSON)
					.body(vehicleDtoRepository.findByVehicleTypeAndMarqueAndModelAndEngineAndStatus(vehicleType,marque,model,engine,status));
		}
		else
		{
			logger.info("Search is performed by parameters:vehicleType,marque,model,engine");
			return ResponseEntity
					.ok()
					.contentType(MediaType.APPLICATION_JSON)
					.body(vehicleDtoRepository.findByVehicleTypeAndMarqueAndModelAndEngine(vehicleType,marque,model,engine));
		}

	}

	/**
	 * Метод отвечающий за обработку get запроса по адресу
	 * http://127.0.0.1:8090/vehicle/{guid}
	 * и возращает пользователя из базы данных в виде json объекта
	 * @param guid первичный ключ по которому определяем какой объект возращать
	 * @param request параметры запроса
	 * @return возращаем найденый в JSON объектах
	 */
	@GetMapping(value = "/{guid}")
	public ResponseEntity<VehicleDto> getVehicle(@PathVariable(value = "guid") String guid, HttpServletRequest request)
	{
		VehicleDto vehicleDto=vehicleDtoRepository.getOne(guid);
		logger.info("Vehicle with Guid={} return!", vehicleDto.getGuid());
		return ResponseEntity
			.ok()
			.contentType(MediaType.APPLICATION_JSON)
				.body(vehicleDto);
	}

	/**
	 * Метод отвечающий за обработку get запроса по адресу
	 * http://127.0.0.1:8090/vehicle/types
	 *
	 * @return возращает все из таблице types в JSON объектах
	 * @throws EntityNotFoundException
	 */
	@GetMapping(value = "/types")
	public ResponseEntity<List<VehicleTypeDto>> typesVehicle() throws EntityNotFoundException
	{
		logger.info("Types with return!");
		return ResponseEntity
				.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(vehicleTypeDtoRepository.findAll());
	}
	/**
	 * Метод отвечающий за обработку get запроса по адресу
	 * http://127.0.0.1:8090/vehicle/marque
	 *
	 * @return возращает все из таблице marque в JSON объектах
	 * @throws EntityNotFoundException
	 */
	@GetMapping(value = "/marque")
	public ResponseEntity<List<VehicleMarqueDto>> marqueVehicle() throws EntityNotFoundException
	{
		logger.info("Marque with return!");
		return ResponseEntity
				.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(vehicleMarqueDtoRepository.findAll());
	}
	/**
	 * Метод отвечающий за обработку get запроса по адресу
	 * http://127.0.0.1:8090/vehicle/status
	 *
	 * @return возращает все из таблице status в JSON объектах
	 * @throws EntityNotFoundException
	 */
	@GetMapping(value = "/status")
	public ResponseEntity<List<VehicleStatusDto>> statusVehicle() throws EntityNotFoundException
	{
		logger.info("Status with return!");
		return ResponseEntity
				.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(vehicleStatusDtoRepository.findAll());
	}
	/**
	 * Метод отвечающий за обработку get запроса по адресу
	 * http://127.0.0.1:8090/vehicle/max
	 *
	 * @return возращает максимальные значения из трёх таблиц БД в JSON объектах
	 * @throws EntityNotFoundException
	 */
	@GetMapping(value = "/max")
	public ResponseEntity<List<?>> maxTypeVehicle() throws EntityNotFoundException
	{
		/**
		 * Берём все элементы из таблице Type
		 */
		List<VehicleTypeDto> vehicleTypeDto=vehicleTypeDtoRepository.findAll();
		/**
		 * Берём все элементы из таблице Marque
		 */
		List<VehicleMarqueDto> vehicleMarqueDto=vehicleMarqueDtoRepository.findAll();
		/**
		 * Берём все элементы из таблице Status
		 */
		List<VehicleStatusDto> vehicleStatusDto=vehicleStatusDtoRepository.findAll();
		/**
		 * Ищем максимальное в VehicleTypeDto значение из колонке count
		 */
		VehicleTypeDto maxType = vehicleTypeDto.stream().max(Comparator.comparing(vehicleTypeDtol ->vehicleTypeDtol.getCount())).get();
		/**
		 * Ищем максимальное в VehicleMarqueDto значение из колонке count
		 */
		VehicleMarqueDto maxMarque = vehicleMarqueDto.stream().max(Comparator.comparing(vehicleMarqueDto1 -> vehicleMarqueDto1.getCount())).get();
		/**
		 * Ищем максимальное в VehicleStatusDto значение из колонке count
		 */
		VehicleStatusDto maxStatus = vehicleStatusDto.stream().max(Comparator.comparing(vehicleStatusDto1 -> vehicleStatusDto1.getCount())).get();
		/**
		 * Создаем лист определённого типа для того чтобы вернуть всё одновременно
		 */
		List<VehicleTypeDto> vehicleallDto = new ArrayList<>();
		/**
		 * Сохраняем в List максимальное значения
		 */
		vehicleallDto.add(new VehicleTypeDto(maxType.getName(),maxType.getCount()));
		vehicleallDto.add(new VehicleTypeDto(maxMarque.getName(),maxMarque.getCount()));
		vehicleallDto.add(new VehicleTypeDto(maxStatus.getName(),maxStatus.getCount()));
		/**
		 * Рапортуем что , вернули макс элементы
		 */
		logger.info("Maximum values returned!");
		return ResponseEntity
				.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(vehicleallDto);

	}

	/**
	 * Метод отвечающий за обработку get запроса по адресу
	 * http://127.0.0.1:8090/vehicle/reverse
	 * Выполняет reverse всех String переменных
	 *
	 * @return возращает сохранённый объект в БД в JSON формате
	 */
	@GetMapping(value = "/reverse")
	public ResponseEntity<VehicleDto> reverse()
	{
		/**
		 * Берём запись из БД по случайному Guid
		 */
		VehicleDto vehicleDto= vehicleDtoRepository.getOne(vehicleDtoRepository.getRandomGuid());
		/**
		 * Создаём новую запись , чтобы мы могли изменить guid
		 */
		VehicleDto vehicleDtonew = new VehicleDto();
		/**
		 * Блок reverse строк
		 */
		vehicleDtonew.setGuid(new StringBuffer(vehicleDto.getGuid()).reverse().toString());
		vehicleDtonew.setVehicleType(new StringBuffer(vehicleDto.getVehicleType()).reverse().toString());
		vehicleDtonew.setMarque(new StringBuffer(vehicleDto.getMarque()).reverse().toString());
		vehicleDtonew.setModel(new StringBuffer(vehicleDto.getModel()).reverse().toString());
		vehicleDtonew.setEngine(new StringBuffer(vehicleDto.getEngine()).reverse().toString());
		vehicleDtonew.setEnginePowerBhp(new StringBuffer(vehicleDto.getEnginePowerBhp()).reverse().toString());
		vehicleDtonew.setTopSpeedMph(vehicleDto.getTopSpeedMph());
		vehicleDtonew.setCostUsd(vehicleDto.getCostUsd());
		vehicleDtonew.setDataInsert(vehicleDto.getDataInsert());
		vehicleDtonew.setStatus(new StringBuffer(vehicleDto.getStatus()).reverse().toString());
		/**
		 * Время изменения записи
		 */
		vehicleDtonew.setDatePurchase(getCurrentTimeStamp());
		updateTable(vehicleDtonew);
		/**
		 * Удаляем старую запись
		 */
		vehicleDtoRepository.delete(vehicleDto);
		deletTable(vehicleDto);
		/**
		 * Сохраняем новую запись
		 */
		vehicleDtonew=vehicleDtoRepository.save(vehicleDtonew);
		/**
		 * Рапортуем об том что , произвели reverse
		 */
		logger.info("Vehicle with Guid={} reverse!", vehicleDto.getGuid());
		/**
		 * Возращаем reverse сообщение
		 */
		return ResponseEntity
				.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(vehicleDtonew);
	}
}