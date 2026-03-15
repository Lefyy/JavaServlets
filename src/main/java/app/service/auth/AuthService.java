package app.service.auth;

import app.model.Customer;

/**
 * Сервис аутентификации. Не зависит от консоли/веба — только логика входа по email/password.
 */
public interface AuthService {

    /**
     * Выполняет вход по email и паролю. При успехе устанавливает текущего пользователя в AuthContext.
     *
     * @param email    email пользователя
     * @param password пароль (сравнивается в открытом виде; для продакшена нужен хэш)
     * @return true если вход выполнен, false если неверные данные
     */
    boolean login(String email, String password);

    /**
     * Очищает текущую сессию (выход).
     */
    void logout();

    /**
     * Текущий авторизованный пользователь или null.
     */
    Customer getCurrentCustomer();
}
