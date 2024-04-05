package africa.semicolon.LogisticSystem.services;

import africa.semicolon.LogisticSystem.data.models.Order;
import africa.semicolon.LogisticSystem.dto.requests.requests.OrderPaymentRequest;
import africa.semicolon.LogisticSystem.dto.requests.requests.UserRegisterRequest;
import africa.semicolon.LogisticSystem.dto.requests.response.UserRegisterResponse;

public interface AdminServices {

    Long findNoOfUsers();

    UserRegisterResponse register(UserRegisterRequest userRegisterRequest);

    int findNoOfRiders();

    void takeOrder(Order order);

    Long noOfOrders();

    int findAvailableRiders();

    void sendOrder(OrderPaymentRequest orderPaymentRequest);
}
