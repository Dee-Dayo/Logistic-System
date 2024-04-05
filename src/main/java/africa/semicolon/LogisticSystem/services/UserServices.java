package africa.semicolon.LogisticSystem.services;

import africa.semicolon.LogisticSystem.data.models.Order;
import africa.semicolon.LogisticSystem.data.models.User;
import africa.semicolon.LogisticSystem.dto.requests.requests.OrderPaymentRequest;
import africa.semicolon.LogisticSystem.dto.requests.requests.SendOrderRequest;
import africa.semicolon.LogisticSystem.dto.requests.requests.UserLoginRequest;

public interface UserServices {

    boolean isUserLoggedIn(String username);

    void login(UserLoginRequest userLoginRequest);
    User findUserByNumber(String phoneNumber);

    void sendOrder(SendOrderRequest sendOrderRequest);

    Order trackOrderById(String id);

    void makePayment(OrderPaymentRequest orderPaymentRequest);
}
