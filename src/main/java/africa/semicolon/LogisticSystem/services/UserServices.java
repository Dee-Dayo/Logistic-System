package africa.semicolon.LogisticSystem.services;

import africa.semicolon.LogisticSystem.data.models.Order;
import africa.semicolon.LogisticSystem.data.models.User;
import africa.semicolon.LogisticSystem.dto.requests.OrderPaymentRequest;
import africa.semicolon.LogisticSystem.dto.requests.SendOrderRequest;
import africa.semicolon.LogisticSystem.dto.requests.UserLoginRequest;
import africa.semicolon.LogisticSystem.dto.response.OrderPaymentResponse;
import africa.semicolon.LogisticSystem.dto.response.UserLoginResponse;
import africa.semicolon.LogisticSystem.dto.response.UserSendOrderResponse;

public interface UserServices {

    boolean isUserLoggedIn(String username);

    UserLoginResponse login(UserLoginRequest userLoginRequest);
    User findUserByNumber(String phoneNumber);

    UserSendOrderResponse sendOrder(SendOrderRequest sendOrderRequest);

    Order trackOrderById(String id);

    OrderPaymentResponse makePayment(OrderPaymentRequest orderPaymentRequest);
}
