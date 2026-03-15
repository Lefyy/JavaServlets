package app.service.auth;

import app.model.Customer;
import app.repository.CustomerRepository;
import app.service.auth.AuthContext;
import app.service.impl.auth.AuthServiceImpl;
import app.service.security.PasswordHasher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private AuthContext authContext;

    @Mock
    private PasswordHasher passwordHasher;

    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        authService = new AuthServiceImpl(customerRepository, authContext, passwordHasher);
    }

    @Test
    void login_success_setsCurrentCustomer() {
        Customer customer = new Customer(1, "Test", "test@mail.com", "hashed-pass", false);
        when(customerRepository.findByEmail("test@mail.com")).thenReturn(Optional.of(customer));
        when(passwordHasher.matches("pass", "hashed-pass")).thenReturn(true);

        boolean result = authService.login("test@mail.com", "pass");

        assertTrue(result);
        verify(authContext).setCurrentCustomer(customer);
        verify(passwordHasher).matches("pass", "hashed-pass");
    }

    @Test
    void login_legacyPlaintextPassword_migratesAndSetsCurrentCustomer() {
        Customer customer = new Customer(1, "Test", "test@mail.com", "pass", false);
        when(customerRepository.findByEmail("test@mail.com")).thenReturn(Optional.of(customer));
        when(passwordHasher.matches("pass", "pass")).thenReturn(false);
        when(passwordHasher.hash("pass")).thenReturn("new-hash");

        boolean result = authService.login("test@mail.com", "pass");

        assertTrue(result);
        assertEquals("new-hash", customer.getPassword());
        verify(customerRepository).update(customer);
        verify(authContext).setCurrentCustomer(customer);
    }

    @Test
    void login_wrongPassword_returnsFalse() {
        Customer customer = new Customer(1, "Test", "test@mail.com", "hashed-pass", false);
        when(customerRepository.findByEmail("test@mail.com")).thenReturn(Optional.of(customer));
        when(passwordHasher.matches("wrong", "hashed-pass")).thenReturn(false);

        boolean result = authService.login("test@mail.com", "wrong");

        assertFalse(result);
        verify(authContext, never()).setCurrentCustomer(any());
    }

    @Test
    void login_unknownEmail_returnsFalse() {
        when(customerRepository.findByEmail("unknown@mail.com")).thenReturn(Optional.empty());

        boolean result = authService.login("unknown@mail.com", "any");

        assertFalse(result);
        verify(authContext, never()).setCurrentCustomer(any());
    }

    @Test
    void login_nullEmail_returnsFalse() {
        boolean result = authService.login(null, "pass");
        assertFalse(result);
        verify(customerRepository, never()).findByEmail(anyString());
    }

    @Test
    void logout_clearsContext() {
        authService.logout();
        verify(authContext).clear();
    }
}
