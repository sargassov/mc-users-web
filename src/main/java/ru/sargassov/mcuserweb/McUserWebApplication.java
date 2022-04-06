package ru.sargassov.mcuserweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class McUserWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(McUserWebApplication.class, args);
	}

}

//	Необходимо разработать веб-приложение, позволяющее управлять пользователями
//		(создавать, редактировать, просматривать список и детали, удалять).
//	Функциональные требования к веб-приложению:
//	- Наличие RESTful интерфейса;
//    - Поддержка операций управления (создания, чтения, редактирования и удаления) пользователями.
//	Имя
//	Фамилия
//	Дата рождения
//	Логин
//	Пароль
//	Поле ввода “О себе”
//	Адрес проживания
//	- Пользователь определяется следующими значениями:
//	- Наличие пользовательского WEB-интерфейса
//
//	Нефункциональные требования:
//
//	- JEE стек технологий
//	- Фреймворки, библиотеки: Hibernate, Spring
//	- Система хранения данных: PostgreSQL
//	- Код приложения необходимо снабдить комментариями
//	- Приложение должно собираться при помощи maven без установки или настройки каких либо дополнительных компонент;
//    - Архив с результатом тестового задания должен содержать текстовый файл readme.txt с инструкцией по сборке, настройке, конфигурированию и развертыванию приложения (если необходимо).