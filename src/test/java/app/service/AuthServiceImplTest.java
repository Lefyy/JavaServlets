package app.service;

import app.model.Customer;
import app.repository.CustomerRepository;
import app.service.auth.AuthContext;
import app.service.impl.AuthServiceImpl;
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

    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        authService = new AuthServiceImpl(customerRepository, authContext);
    }

    @Test
    void login_success_setsCurrentCustomer() {
        Customer customer = new Customer(1, "Test", "test@mail.com", "pass", false);
        when(customerRepository.findByEmail("test@mail.com")).thenReturn(Optional.of(customer));

        boolean result = authService.login("test@mail.com", "pass");

        assertTrue(result);
        verify(authContext).setCurrentCustomer(customer);
    }

    @Test
    void login_wrongPassword_returnsFalse() {
        Customer customer = new Customer(1, "Test", "test@mail.com", "pass", false);
        when(customerRepository.findByEmail("test@mail.com")).thenReturn(Optional.of(customer));

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
