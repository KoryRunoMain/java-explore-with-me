# java-explore-with-me
https://github.com/KoryRunoMain/java-explore-with-me/pull/6


### Описание проекта
Restfull API backend-сервис для размещения пользователями событий и участия в них.


#### Основные особенности проекта:
- Разработан с использованием фреймворка Spring Boot;
- База данных postgreSQL;
- Микросервисный подход;
- 35 End-points доступных для управления данными.


## Содержание:
1. [Стэк проекта](#стэк-проекта)
2. [API сервиса](#api-сервиса)
3. [Конфигурационные файлы](#конфигурация)
4. [Инструкция](#пошаговая-инструкция-по-установке-и-запуску-проета)


## Стэк проекта
- Java 11, Spring Boot, Maven, Lombok, JPA, JPQL, Docker, Postman, Swagger

## API сервиса
### Основной сервис (ewm-service)
#### Публичный (открыт для всех)
1. Категории
- GET /categories - Получить все категории событий;
- GET /categories/{catId} - Получить категорию;
2. Комментарии
- GET /events/{eventId}/comments - Получить все комментарии события;
3. Подборки
- GET /compilations - Получить все подборки;
- GET /compilations/{compId} - Получить подборку;
4. События
- GET /events - Получить все опубликованные события;
- GET /events/{id} - Получить опубликованное событие

#### Приватный (для зарегестрированных пользователей)
1. Комментарии
- POST /users/{userId}/events/{eventId}/comments - Добавить комментарий;
- PATCH /users/{userId}/comments/{comId} - Обновить текст комментария;
- DELETE /users/{userId}/comments/{comId} - Удалить комментарий;
2. События
- GET /users/{userId}/events - Получить все события;
- GET /users/{userId}/events/{eventId} - Получить событие;
- POST /users/{userId}/events - Добавить событие;
- PATCH /users/{userId}/events/{eventId} - Обновить данные события;
3. Запросы на участие
- GET /users/{userId}/requests - Получить все запросы на участие в событии;
- POST /users/{userId}/requests - Добавить запрос на участие;
- PATCH /users/{userId}/requests/{requestId}/cancel - Отменить заявку на участие в событии;
- GET /users/{userId}/events/{eventId}/requests - Получить личный список запросов на участие в событиях;
- PATCH /users/{userId}/events/{eventId}/requests - Обновить запрос на участие в собитии;

#### Административный (Управление администратором сервиса)
1. Пользователи
- GET /admin/users - Получить список пользователей;
- POST /admin/users - Добавить пользователя;
- DELETE /admin/users/{id} - Удалить пользователя;
2. Категории
- POST /admin/categories - Добавить категорию;
- PATCH /admin/categories/{catId} - Обновить категорию;
- DELETE /admin/categories/{catId} - Удалить категория;
3. Комментарии
- GET /admin/comments/{comId} - Получить комментарий пользователя;
- PATCH /admin/{userId}/comments/{comId} - Обновить текст комментария пользователя;
- PATCH /admin/comments/{comId} - Обновить статус комментария (PENDING, APPROVED, REJECT, SPAM, BLOCKED);
4. Подборки
- POST /admin/compilations - Добавить подборку;
- PATCH /admin/compilations/{compId} - Обновить подборку;
- DELETE /admin/compilations/{compId} - Удалить подборку;
5. События
- GET /admin/events - Получить все события с фильтрацией;
- PATCH /admin/events/{eventId} - Обновить событие;

### Сервис статистики (ewm-stat-service)
- POST /hit - Добавить запись с обращением к endpoint
- GET /stats - Получить статистику вызовов endpoints
 
## Ссылки
1. Схемы базы данных
- Схема БД Сервис статистики: [ewm-stat-service](ewm-stat-service/stat-service/src/main/resources/schema.sql)
- Схема БД Сервис основной: [ewm-service](ewm-service/src/main/resources/schema.sql)
2. Спецификации
- Cпецификация сервиса статистики: [ewm-stat-service](ewm-stats-service-spec.json)
- Cпецификация основного сервиса: [ewm-service](ewm-main-service-spec.json)
- Специфик
3. Тесты
- Тесты: [tests](postman)
4. Конфигурации
- Основной в проекте: [pom.xml](pom.xml)
- Сервис основной: [pom.xml](ewm-service/pom.xml)
- Сервис статистики: [pom.xml](ewm-stat-service/pom.xml)
- Клиент сервиса статиситки: [pom.xml](ewm-stat-service/stat-client/pom.xml)
- DTO-модели сервиса статиситки: [pom.xml](ewm-stat-service/stat-dto/pom.xml)
- Сервис статиситки с логикой: [pom.xml](ewm-stat-service/stat-service/pom.xml)
- Docker: [dockerConfig](docker-compose.yml)


## Пошаговая инструкция по установке и запуску проета
1. Установите Git: Если у вас еще не установлен Git, загрузите и установите его с официального сайта
   Git: https://git-scm.com/.
2. Клонируйте репозиторий: Откройте командную строку или терминал и выполните команду клонирования для репозитория
   GitHub. Например:
    ```
    git clone https://github.com/KoryRunoMain/java-explore-with-me.git
    ```
3. Установите Docker: Если у вас еще не установлен Docker, загрузите и установите его с официального сайта
   Docker: https://www.docker.com/get-started/
3. Откройте проект в IDE: Откройте вашу среду разработки (IDE), такую как IntelliJ IDEA, Eclipse или NetBeans.
4. Импортируйте проект как Maven проект: Если вы используете IntelliJ IDEA,
   выберите File -> Open и выберите папку, в которую был склонирован репозиторий.
   IntelliJ IDEA должна автоматически распознать проект как Maven проект и импортировать его.
   В Eclipse вы можете выбрать File -> Import -> Existing Maven Projects и выбрать корневую папку проекта.
   В NetBeans вы можете выбрать File -> Open Project и выбрать папку проекта.
5. Соберите поект, например с помощью команды в терминале:
    ```
    docker compose up
    ```
6. Если контейнеры через некоторое время после старта образа отключаются как все так и по отдельности,
   настройте используемые порты.
