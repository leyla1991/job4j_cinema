# job4j_cinema

Тестовый проект "Кинотеатр".

Проверка понимания WEB-структур, Thymeleaf. 

Создание веб-приложения на основе базы данных, Thymeleaf, Spring и аннотаций.

### Стек технологий:

_JAVA 17_

_PostgreSQL 42.2.2_

_Maven 4.0_

_Liquibase 4.15.0_

_CheckStyle 3.1.2_

_Mockito_

_Spring Boot_

_Sql2o 1.6.0_

_css_

_js_


### Список ПО 


_JAVA 17_

_PostgreSQL 16_

_Maven 4.0_

_Плагин Liquibase_

_Плагин CheckStyle_

_css_

_js_

### Порядок запуска приложения

1. ``` shell
   create database cinema;
   ```


2. ``` shell 
   liquibase:update
    ```

3. ```shell
    Main.java
    ```
 
http://localhost:8080

### Скриншоты страниц

__Главная страница__
![главная страница](screenshots/index_page.png)


__Страница кинотеки__
![](screenshots/films_page.png)


__Страница расписания сеансов__
![](screenshots/schedules_page.png)

__Страница регистрации пользователя__
![](screenshots/register_page.png)

__Страница входа в аккаунт__
![](screenshots/login_page.png)

__Страница выбора мест для покупки билетов__
![](screenshots/buy_ticket_page.png)

__Страница удачной покупки билета__
![](screenshots/successful_purchase_ticket.png)

__Страница авторизированного пользователя со всеми купленными билетами__
![](screenshots/all_ticket_buy_user.png)

__Страница ошибки регистрации пользователя__
![](screenshots/errors_register.png)

__Страница ошибки покупки билета__
![](screenshots/error_buy_ticket_page.png)