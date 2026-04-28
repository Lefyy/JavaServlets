## JavaShop (Servlet Edition)

Проект переведен с CLI на `Java Servlets + JSP` (WAR для Tomcat).

### Запуск

1. Собрать WAR:
   - `mvn clean package`
2. Задеплоить `target/JavaShop-1.0-SNAPSHOT.war` в Tomcat.
3. Открыть приложение:
   - `/products` — каталог
   - `/auth/login` — вход
   - `/admin` — админ-панель (для staff-пользователей)

### Настройка БД

Можно передать подключение через переменные окружения (приоритет выше `db.properties`):

- `JAVASHOP_DB_URL` (например, `jdbc:postgresql://localhost:5432/JavaShop`)
- `JAVASHOP_DB_USER`
- `JAVASHOP_DB_PASSWORD`
