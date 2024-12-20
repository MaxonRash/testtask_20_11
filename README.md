**Использованы:**
- **Java17**
- **SpringBoot 3.3.5**
- **Maven, Lombok, jjwt-api, Swagger, Spring Validation, Spring Data, Spring Security**
- **Postgresql, Flyway**
- **Junit, Mockito**
- **Docker, Docker-compose**

Для простоты деплоя ключ для подписи токенов лежит прямо в `application.properties`.

Запуск командой `docker-compose up --build -d`, перед этим собрать проект командой `./mvnw clean package`. Можно использовать скрипт `start.sh` в корне проекта, если утилита - `docker-compose` (на linux часто бывает `docker compose` через пробел) \
\
К БД, развёрнутой в докере, можно подключиться (например, через PgAdmin) по адресу `localhost:5430`, все креды можно посмотреть в `docker-compose.yml` \
\
Swagger после запуска по адресу `http://localhost:8080/swagger-ui/index.html` \
Примеры тел запросов можно брать оттуда.  Для тестирования в Postman можно также импортировать коллекцию `Task_management_useful_test_requests.postman_collection.json`, которая лежит в корне проекта. Там можно найти основные методы. \
Jwt token передается в хэдере `Authorization` со значением `Bearer tokenValue` \
\
Сначала доступ открыт только к методам `/auth` и Swagger'у (на всё остальное будет `403 FORBIDDEN`). После помещения токена в хэдер можно пользоваться остальными. \
В этом проекте в качестве логина везде используется имейл, он должен быть валидным.
В методах, где нужна роль админа, будет понятная ошибка с 403 статусом. Для тестирования можно получить роль админа для текущего пользователя (владельца токена) по адресу `http://localhost:8080/tasks/get-admin` \
\
При создании таски или назначении в качестве исполнителя (`implementer`) имейла пользователя нужно сначала зарегистрировать его, иначе будет ошибка `UserNotFound`
****
Тесты написал для Контроллера Аутентификации и для метода создания таски в Сервисе, работающем с БД. \
Для Контроллера Тасок тесты будут очень похожими на тесты Контроллера Аутентификации, отличаться будут только методы (get, post, put, delete) и входные данные. Пример теста показал для метода, имеющего тело. \
Для остальных методов сервисов, работающих с бд тесты будут тоже аналогичными, с другими входными данными. \
Поэтому не стал много времени тратить на тесты, в имеющихся была цель показать, что умею работать с Junit и Mockito.