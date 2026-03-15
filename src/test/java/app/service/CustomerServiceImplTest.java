package app.service;

import app.model.Customer;
import app.repository.CustomerRepository;
import app.service.impl.CustomerServiceImpl;
import app.service.security.PasswordHasher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PasswordHasher passwordHasher;

    private CustomerServiceImpl customerService;

    @BeforeEach
    void setUp() {
        customerService = new CustomerServiceImpl(customerRepository, passwordHasher);
    }

    @Test
    void updateOwnProfile_nullCustomer_throws() {
        assertThrows(IllegalArgumentException.class, () ->
                customerService.updateOwnProfile(null, "A", "a@b.com", "p"));
    }

    @Test
    void save_hashesPasswordBeforeSave() {
        Customer customer = new Customer(null, "N", "n@mail.com", "raw", false);
        when(passwordHasher.hash("raw")).thenReturn("hashed");

        customerService.save(customer);

        assertEquals("hashed", customer.getPassword());
        verify(customerRepository).save(customer);
    }

    @Test
    void updateOwnProfile_updatesRepository() {
        Customer current = new Customer(1, "Old", "old@mail.com", "oldhash", false);
        when(passwordHasher.hash("newpass")).thenReturn("newhash");
        ArgumentCaptor<Customer> captor = ArgumentCaptor.forClass(Customer.class);
        doNothing().when(customerRepository).update(captor.capture());

        customerService.updateOwnProfile(current, "New", "new@mail.com", "newpass");

        Customer updated = captor.getValue();
        assertEquals(1, updated.getId());
        assertEquals("New", updated.getName());
        assertEquals("new@mail.com", updated.getEmail());
        assertEquals("newhash", updated.getPassword());
        assertFalse(updated.isStaff());
        assertEquals("New", current.getName());
        assertEquals("new@mail.com", current.getEmail());
        assertEquals("newhash", current.getPassword());
    }

    @Test
    void existsByEmail_delegatesToRepository() {
        when(customerRepository.existsByEmail("x@y.com")).thenReturn(true);
        assertTrue(customerService.existsByEmail("x@y.com"));
        when(customerRepository.existsByEmail("absent@y.com")).thenReturn(false);
        assertFalse(customerService.existsByEmail("absent@y.com"));
    }
}
