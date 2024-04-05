package africa.semicolon.LogisticSystem.services;

import africa.semicolon.LogisticSystem.data.models.Order;
import africa.semicolon.LogisticSystem.data.models.User;
import africa.semicolon.LogisticSystem.dto.requests.requests.OrderPaymentRequest;
import africa.semicolon.LogisticSystem.dto.requests.requests.SendOrderRequest;
import africa.semicolon.LogisticSystem.dto.requests.requests.UserLoginRequest;
import africa.semicolon.LogisticSystem.dto.requests.response.UserLoginResponse;
import africa.semicolon.LogisticSystem.dto.requests.response.UserSendOrderResponse;

public interface UserServices {

    boolean isUserLoggedIn(String username);

    UserLoginResponse login(UserLoginRequest userLoginRequest);
    User findUserByNumber(String phoneNumber);

    UserSendOrderResponse sendOrder(SendOrderRequest sendOrderRequest);

    Order trackOrderById(String id);

    void makePayment(OrderPaymentRequest orderPaymentRequest);
}
