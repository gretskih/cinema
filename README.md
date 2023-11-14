# JOB4J_Cinema
## Описание проекта
Реализован сайт кинотеатр. 
На *Главной* странице размещены постеры фильмов из проката. В разделе 
*Кинотека* размещен список фильмов с описанием. В разделе *Расписание* 
выводится таблица киносеансов, с возможностью выбора для покупки билета. 
Совершить покупку может только зарегистрированный пользователь.

## Стек технологий
- Java 17
- Spring
- Sql2o
- Thymeleaf
- Liquibase
- PostgreSQL
- Mockito
- Maven
- Bootstrap

## Окружение
- Java 17
- PostgreSQL 42.5.1
- Apache Maven 4.0.0

## Запуск проекта
1. Скачать архив проекта или создать копию (fork) проекта в своем репозитории и клонировать.
2. Создать локальную базу данных cinema, используя интерфейс PostgreSQL 16 или команду:

   ```create database cinema;```

3. В файл конфигурации db/liquibase.properties внести логин и пароль для доступа к базе данных.
4. Запустить Liquibase для создания таблиц и наполнения базы данными.
5. Для запуска на локальной машине скомпилировать и запустить проект в командной строке 

      ```mvn spring-boot:run```
   
   или после сборки проекта с использованием maven выполнить в командной строке

      ``` java -jar target/job4j_cinema-1.0-SNAPSHOT.jar```
6. Перейти по адресу http://localhost:8080/.
## Взаимодействие с приложением
![Главная страница](https://github.com/gretskih/cinema/blob/main/img/index.png)

![Расписание](https://github.com/gretskih/cinema/blob/main/img/raspisanie.png)

![Кинотека](https://github.com/gretskih/cinema/blob/main/img/kinoteka.png)

![Покупка билета](https://github.com/gretskih/cinema/blob/main/img/bye.png)

![Успешная покупка билета](https://github.com/gretskih/cinema/blob/main/img/success.png)

![Неудачная покупка билета](https://github.com/gretskih/cinema/blob/main/img/error.png)

![Регистрация](https://github.com/gretskih/cinema/blob/main/img/registration.png)

![Логин](https://github.com/gretskih/cinema/blob/main/img/login.png)
## Контакты

Telegram: _@Anatolij_gr_

Email: _gretskih@mail.ru_