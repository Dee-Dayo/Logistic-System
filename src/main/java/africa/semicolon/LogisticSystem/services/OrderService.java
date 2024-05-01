package africa.semicolon.LogisticSystem.services;

import africa.semicolon.LogisticSystem.data.models.Order;
import africa.semicolon.LogisticSystem.data.models.User;
import africa.semicolon.LogisticSystem.dto.requests.OrderPaymentRequest;
import africa.semicolon.LogisticSystem.dto.requests.SendOrderRequest;

import java.util.List;

public interface OrderService {
    Order getOrderByUser(User sender);
    Order getOrderById(String id);
    List<Order> getAllOrders();

    Order setOrder(User user, SendOrderRequest sendOrderRequest);

    void setPaymentStatus(OrderPaymentRequest orderPaymentRequest);
}
