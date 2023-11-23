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

   ```create database cinema```

3. В файл конфигурации db/liquibase.properties внести логин и пароль пользователя для доступа к базе данных cinema.
4. Запустить Liquibase для создания таблиц и наполнения базы cinema информацией.
5. Для запуска на локальной машине скомпилировать и запустить проект в командной строке 

      ```mvn spring-boot:run```
   
   или после сборки проекта с использованием maven выполнить в командной строке

      ``` java -jar target/job4j_cinema-1.0-SNAPSHOT.jar```
6. Перейти по адресу http://localhost:8080/.
## Взаимодействие с приложением

### Главная страница
![Главная страница](https://github.com/gretskih/cinema/blob/main/img/index.png)

### Список сеансов
![Расписание](https://github.com/gretskih/cinema/blob/main/img/raspisanie.png)

### Кинотека
![Кинотека](https://github.com/gretskih/cinema/blob/main/img/kinoteka.png)

### Покупка билета на выбранный сеанс
![Покупка билета](https://github.com/gretskih/cinema/blob/main/img/bye.png)

### Успешная покупка билета
![Успешная покупка билета](https://github.com/gretskih/cinema/blob/main/img/success.png)

### Ошибка при покупке билета
![Неудачная покупка билета](https://github.com/gretskih/cinema/blob/main/img/error.png)

### Страница регистрации нового пользователя
![Регистрация](https://github.com/gretskih/cinema/blob/main/img/registration.png)

### Страница входа
![Логин](https://github.com/gretskih/cinema/blob/main/img/login.png)
## Контакты

email: gretskih@mail.ru <br/>
telegram: @Anatolij_gr