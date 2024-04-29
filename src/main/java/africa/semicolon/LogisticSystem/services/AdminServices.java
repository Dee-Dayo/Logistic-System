package africa.semicolon.LogisticSystem.services;

import africa.semicolon.LogisticSystem.data.models.Order;
import africa.semicolon.LogisticSystem.data.models.User;
import africa.semicolon.LogisticSystem.dto.requests.OrderPaymentRequest;
import africa.semicolon.LogisticSystem.dto.requests.RiderRegisterRequest;
import africa.semicolon.LogisticSystem.dto.requests.SendOrderRequest;
import africa.semicolon.LogisticSystem.dto.requests.UserRegisterRequest;
import africa.semicolon.LogisticSystem.dto.response.RiderRegisterResponse;
import africa.semicolon.LogisticSystem.dto.response.UserRegisterResponse;

import java.util.List;

public interface AdminServices {
    Long findNoOfUsers();
    UserRegisterResponse register(UserRegisterRequest userRegisterRequest);
    Long findNoOfRiders();
    Order takeOrder(User user, SendOrderRequest sendOrderRequest);
    Long noOfOrders();
    int findAvailableRiders();
    void sendOrder(OrderPaymentRequest orderPaymentRequest);
    List<Order> getAllOrders();
    RiderRegisterResponse register(RiderRegisterRequest riderRegisterRequest);
}
