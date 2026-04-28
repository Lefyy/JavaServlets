package app.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DatabaseConnection {
    private static Connection connection;
    private static String dbUrl;
    private static String dbUser;
    private static String dbPassword;

    private DatabaseConnection() {}

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            ensureConfigLoaded();
            ensureDriverLoaded();
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        }
        return connection;
    }

    private static void ensureConfigLoaded() {
        if (dbUrl != null) {
            return;
        }
        Properties props = new Properties();
        try (InputStream in = DatabaseConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (in != null) {
                props.load(in);
            }
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при чтении настроек БД", e);
        }

        dbUrl = envOrDefault("JAVASHOP_DB_URL", props.getProperty("db.url"));
        dbUser = envOrDefault("JAVASHOP_DB_USER", props.getProperty("db.user"));
        dbPassword = envOrDefault("JAVASHOP_DB_PASSWORD", props.getProperty("db.password"));
        if (isBlank(dbUrl) || isBlank(dbUser)) {
            throw new RuntimeException("Не заданы параметры подключения к БД (url/user).");
        }
    }

    private static void ensureDriverLoaded() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("PostgreSQL JDBC driver не найден в classpath.", e);
        }
    }

    private static String envOrDefault(String envName, String fallback) {
        String value = System.getenv(envName);
        return isBlank(value) ? fallback : value.trim();
    }

    private static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
