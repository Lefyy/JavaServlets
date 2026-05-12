package app.repository;

import app.model.Order;

import java.util.List;

public interface OrderRepository extends Repository<Order> {

    List<Order> findByCustomerId(Integer customerId);

    List<Order> findForAdmin(String query, String sortDate, int limit, int offset);

    int countForAdmin(String query);
}
