package africa.semicolon.LogisticSystem.services;

import africa.semicolon.LogisticSystem.data.models.Order;
import africa.semicolon.LogisticSystem.data.models.User;
import africa.semicolon.LogisticSystem.data.repositories.OrderRepository;
import africa.semicolon.LogisticSystem.exceptions.OrderNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    OrderRepository orderRepository;

    @Override
    public Order getOrderByUser(User sender) {
        Order order = orderRepository.findBySender(sender);
        if (order == null) throw new OrderNotFoundException("Order not found");
        return order;
    }

    @Override
    public Order getOrderById(String id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isEmpty()) throw new OrderNotFoundException("Order not found");
        return order.get();
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
