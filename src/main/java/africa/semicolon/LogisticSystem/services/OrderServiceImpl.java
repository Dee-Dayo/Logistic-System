package africa.semicolon.LogisticSystem.services;

import africa.semicolon.LogisticSystem.data.models.Order;
import africa.semicolon.LogisticSystem.data.models.Product;
import africa.semicolon.LogisticSystem.data.models.User;
import africa.semicolon.LogisticSystem.data.repositories.OrderRepository;
import africa.semicolon.LogisticSystem.dto.requests.OrderPaymentRequest;
import africa.semicolon.LogisticSystem.dto.requests.SendOrderRequest;
import africa.semicolon.LogisticSystem.exceptions.OrderNotFoundException;
import africa.semicolon.LogisticSystem.exceptions.OrderPaymentNotMade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static africa.semicolon.LogisticSystem.utils.Mapper.requestMap;

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

    @Override
    public Order setOrder(User user, SendOrderRequest sendOrderRequest) {
        Order order = requestMap(user, sendOrderRequest);
        Product product = sendOrderRequest.getProduct();
        setOrderPrice(order, product);
        orderRepository.save(order);

        return order;
    }

    @Override
    public void setPaymentStatus(OrderPaymentRequest orderPaymentRequest) {
        Order order = getOrderById(orderPaymentRequest.getOrderId());

        if (orderPaymentRequest.getOrderAmount() >= order.getAmount()){
            order.setPaid(true);
            order.setPending(true);
            order.setDatePaymentMade(LocalDateTime.now());
            orderRepository.save(order);
        } else throw new OrderPaymentNotMade("Insufficient amount for order");
    }

    private void setOrderPrice(Order order, Product product) {
        switch (product){
            case TV -> order.setAmount(5000);
            case FOOD -> order.setAmount(1500);
            case CLOTH -> order.setAmount(2000);
            case LAPTOP -> order.setAmount(8000);
            default -> order.setAmount(10000);
        }
    }
}
