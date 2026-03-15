package app.service;

import app.model.Order;
import app.model.Product;
import app.repository.OrderItemRepository;
import app.repository.OrderRepository;
import app.repository.ProductRepository;
import app.service.OrderService;
import app.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private ProductRepository productRepository;

    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        orderService = new OrderServiceImpl(orderRepository, orderItemRepository, productRepository);
    }

    @Test
    void createOrder_emptyItems_throws() {
        assertThrows(IllegalArgumentException.class, () ->
                orderService.createOrder(1, List.of()));
    }

    @Test
    void createOrder_productNotFound_throws() {
        when(productRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () ->
                orderService.createOrder(1, List.of(new OrderService.OrderItemDto(999, 1))));
    }

    @Test
    void createOrder_insufficientQuantity_throws() {
        Product p = new Product(1, "P", BigDecimal.ONE, 0, 1, null);
        when(productRepository.findById(1)).thenReturn(Optional.of(p));

        assertThrows(IllegalArgumentException.class, () ->
                orderService.createOrder(1, List.of(new OrderService.OrderItemDto(1, 5))));
    }

    @Test
    void createOrder_success_savesOrderAndItems() {
        Order savedOrder = new Order();
        savedOrder.setId(10);
        savedOrder.setCustomerId(1);
        savedOrder.setStatusId(1);
        when(orderRepository.save(any(Order.class))).thenAnswer(inv -> {
            Order o = inv.getArgument(0);
            o.setId(10);
            return o;
        });
        Product p = new Product(1, "P", new BigDecimal("10.00"), 10, 1, null);
        when(productRepository.findById(1)).thenReturn(Optional.of(p));

        Order result = orderService.createOrder(1, List.of(new OrderService.OrderItemDto(1, 2)));

        assertNotNull(result);
        assertEquals(10, result.getId());
        verify(orderRepository).save(any(Order.class));
        verify(orderItemRepository).save(any());
        verify(productRepository).updateQuantity(eq(1), eq(8));
    }

    @Test
    void delete_orderNotFound_returnsFalse() {
        when(orderRepository.findById(999)).thenReturn(Optional.empty());

        boolean result = orderService.delete(999, 1, false);

        assertFalse(result);
        verify(orderRepository, never()).delete(anyInt());
    }

    @Test
    void delete_notOwnerAndNotStaff_throws() {
        Order order = new Order(1, 2, 1, null);
        when(orderRepository.findById(1)).thenReturn(Optional.of(order));

        assertThrows(SecurityException.class, () ->
                orderService.delete(1, 1, false));
    }

    @Test
    void delete_owner_deletesOrder() {
        Order order = new Order(1, 1, 1, null);
        when(orderRepository.findById(1)).thenReturn(Optional.of(order));
        when(orderItemRepository.deleteByOrderId(1)).thenReturn(true);
        when(orderRepository.delete(1)).thenReturn(true);

        boolean result = orderService.delete(1, 1, false);

        assertTrue(result);
        verify(orderItemRepository).deleteByOrderId(1);
        verify(orderRepository).delete(1);
    }

    @Test
    void delete_staffCanDeleteAnyOrder() {
        Order order = new Order(1, 2, 1, null);
        when(orderRepository.findById(1)).thenReturn(Optional.of(order));
        when(orderItemRepository.deleteByOrderId(1)).thenReturn(true);
        when(orderRepository.delete(1)).thenReturn(true);

        boolean result = orderService.delete(1, 99, true);

        assertTrue(result);
        verify(orderRepository).delete(1);
    }
}
