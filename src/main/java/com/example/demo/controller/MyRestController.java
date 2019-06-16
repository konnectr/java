package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.persistence.dao.UserAccountRepository;
import com.example.demo.persistence.model.UserAccount;
import com.example.demo.persistence.dao.VehicleDtoRepository;
import com.example.demo.persistence.model.VehicleDto;
/**
 * Класс-контроллер приложения. 
 * Отвечает за обработку запросов по адресу http://localhost:8080/my
 * 
 * @author rybakov
 *
 */
@RestController
@RequestMapping("/my")
public class MyRestController {
	
	/**
	 * Логгер. Необходим для правильного вывода сообщений из кода.
	 */
	public static final Logger logger = LoggerFactory.getLogger(MyRestController.class);
	
	/**
	 * Конструктор. Вызывается spring контекстом, когда контроллер создаётся.
	 */
	public MyRestController() {
		logger.info("MyRestController was created!");
	}

	/**
	 * Аннотация @Autowired необходима для того, чтобы spring контекст вставил сюда 
	 * подходящий по типу класс-bean. bean(бины) могут быть разных типов. 
	 * Их объединяет то, что все они находятся в контексте, как фасолины в банке.
	 * spring сам решает, какой и когда вызывать в той или иной ситуации.
	 * В данном случае userAccountRepository - бин-репозиторий.
	 * 
	 * Класс, помеченный аннотацией @RestController также является бином и 
	 * находится в контексте приложения. Как устроен spring контекст - можно 
	 * почитать здесь https://habr.com/en/post/222579/ spring-boot - это оболочка над
	 * spring, с точки зрения разработчика упрощающая работу с spring, 
	 * где большая часть конфигураций происходит без участия разработчика.
	 */
	@Autowired
	/**
	 * Поле репозитория, которое будет заполнено при 
	 */
	private UserAccountRepository userAccountRepository;

	/**
	 * Метод, отвечающий за обработку post запросов по адресу 
	 * http://localhost:8080/my Метод принимает объект типа UserAccount 
	 * в теле запроса замаршаленный в json объект (Маршалинг - процесс 
	 * преобразования объекта в json/xml/yaml/tree)
	 * 
	 * Пример запроса:
	  {
	    "username":"user",
	    "password":"qwerty"
	  }
	 * 
	 * @param userAccount принимаемый объект. spring демаршалит его за нас.
	 * @param request параметры запроса.
	 * @return объект, вставленный в базу данных.
	 */
	@PostMapping(produces = "application/json", consumes = "application/json")
	public ResponseEntity<UserAccount> postUser(
			@RequestBody UserAccount userAccount,
			HttpServletRequest request) {
		/**
		 * Сохраняем пользователя в базу данных.
		 */
		userAccount = userAccountRepository.save(userAccount);
		
		/**
		 * Рапортуем о том, что пользователь создан.
		 */
		logger.info("UserAccount with id={} was created!", userAccount.getId());
		
		/**
		 * Возвращаем ответ.
		 */
		return ResponseEntity
				.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(userAccount);
	}

	/**
	 * Метод, отвечающий за обработку get запросов по адресу 
	 * http://localhost:8080/my/{id} Метод принимает id аккаунта пользователя 
	 * и возвращает пользователя из базы данных в виде json объекта.
	 * @param id - первичный ключ в бд объекта, по которому будем искать пользователя.
	 * @param request параметры запроса.
	 * @return пользователь из базы данных в виде json
	 */
	@GetMapping("/{id}")
	public ResponseEntity<UserAccount> getUser(@PathVariable Long id, HttpServletRequest request)
	{
		
		/**
		 * Вытаскиваем пользователя из базы по его id.
		 */
		UserAccount userAccount = userAccountRepository.getOne(id);
		
		/**
		 * Возвращаем ответ.
		 */
		return ResponseEntity
				.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(userAccount);
	}
}