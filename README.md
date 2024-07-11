# java-explore-with-me
Restfull API backend-сервис для размещения пользователями событий и участия в них.

### ЭТАП 1
Реализация сервиса статистики.

#### Требования:
1. реализовать сервис статистики в соответствии со спецификацией: ewm-stats-service.json.
2. реализовать HTTP-клиент для работы с сервисом статистики.
3. подготовить сборку проекта.
4. определится с тематикой дополнительной функциональности, которую вы будете реализовывать.

#### Базовые требования:
1. разработка должна вестись в публичном репозитории, созданном на основе шаблона.
2. весь код первого этапа разместите в отдельной ветке с именем stat_svc.

#### Что будет проверяться 
Работающая сборка проекта:
1. проект компилируется без ошибок;
2. сервис статистики успешно запускается в докер-контейнере;
3. экземпляр PostgreSQL для сервиса статистики успешно запускается в докер-контейнере.

Корректная работа сервиса статистики:
1. все эндпоинты отрабатывают в соответствии со спецификацией;
2. данные успешно сохраняются и выгружаются из базы данных;
3. реализован HTTP-клиент сервиса статистики.


### ЭТАП 2
Реализация основного сервиса.

#### Базовые требования:
1. реализация должна вестись в отдельной ветке с именем main_svc. 
2. ветка должна основываться на ветке main в которую слиты изменения предыдущего этапа.

#### Что будет проверяться
Работающая сборка проекта:
1. проект компилируется без ошибок;
2. основной сервис и сервис статистики успешно запускаются в Docker-контейнерах;
3. для каждого сервиса запускается свой экземпляр PostgreSQL в Docker-контейнере.

Корректная работа основного сервиса:
1. все эндпоинты отрабатывают в соответствии со спецификацией;
2. данные успешно сохраняются и выгружаются из базы данных;
3. основной сервис и сервис статистики корректно взаимодействуют;
4. реализация работы с данными не производит лишней нагрузки на базу данных.

- спецификация основного сервиса: [ewm-service](ewm-main-service-spec.json)
- спецификация сервиса статистики: [ewm-stat-service](ewm-stats-service-spec.json)

### ЭТАП 3
Реализация дополнительного функционала (фича комментарии)
