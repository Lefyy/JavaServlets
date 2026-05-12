package app.repository;

import app.model.OrderStatus;
import java.util.List;

public interface OrderStatusRepository extends Repository<OrderStatus> {
    List<OrderStatus> findForAdmin(String query, int limit, int offset);

    int countForAdmin(String query);
}
