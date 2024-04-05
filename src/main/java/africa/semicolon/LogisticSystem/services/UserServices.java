package africa.semicolon.LogisticSystem.services;

import africa.semicolon.LogisticSystem.data.models.Order;
import africa.semicolon.LogisticSystem.data.models.User;
import africa.semicolon.LogisticSystem.dto.requests.OrderPaymentRequest;
import africa.semicolon.LogisticSystem.dto.requests.SendOrderRequest;
import africa.semicolon.LogisticSystem.dto.requests.UserLoginRequest;
import africa.semicolon.LogisticSystem.dto.requests.UserRegisterRequest;

public interface UserServices {

    boolean isUserLoggedIn(String username);

    void login(UserLoginRequest userLoginRequest);
    User findUserByNumber(String phoneNumber);

    void sendOrder(SendOrderRequest sendOrderRequest);

    Order trackOrderById(String id);

    void makePayment(OrderPaymentRequest orderPaymentRequest);
}
