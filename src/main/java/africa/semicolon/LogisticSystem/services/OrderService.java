package africa.semicolon.LogisticSystem.services;

import africa.semicolon.LogisticSystem.data.models.Order;
import africa.semicolon.LogisticSystem.data.models.User;

import java.util.List;

public interface OrderService {
    Order getOrderByUser(User sender);
    Order getOrderById(String id);
    List<Order> getAllOrders();
}
