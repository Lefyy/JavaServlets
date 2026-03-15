package app.service.impl;

import app.model.OrderStatus;
import app.repository.OrderStatusRepository;
import app.service.OrderStatusService;

import java.util.List;
import java.util.Optional;

public class OrderStatusServiceImpl implements OrderStatusService {

    private final OrderStatusRepository orderStatusRepository;

    public OrderStatusServiceImpl(OrderStatusRepository orderStatusRepository) {
        this.orderStatusRepository = orderStatusRepository;
    }

    @Override
    public Optional<OrderStatus> findById(Integer id) {
        return orderStatusRepository.findById(id);
    }

    @Override
    public List<OrderStatus> findAll() {
        return orderStatusRepository.findAll();
    }

    @Override
    public OrderStatus save(OrderStatus orderStatus) {
        return orderStatusRepository.save(orderStatus);
    }

    @Override
    public void update(OrderStatus orderStatus) {
        orderStatusRepository.update(orderStatus);
    }

    @Override
    public boolean delete(Integer id) {
        return orderStatusRepository.delete(id);
    }
}
