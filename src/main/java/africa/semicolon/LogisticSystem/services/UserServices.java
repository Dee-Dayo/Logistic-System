package africa.semicolon.LogisticSystem.services;

import africa.semicolon.LogisticSystem.data.models.User;
import africa.semicolon.LogisticSystem.dto.requests.CheckStatusRequest;
import africa.semicolon.LogisticSystem.dto.requests.OrderPaymentRequest;
import africa.semicolon.LogisticSystem.dto.requests.SendOrderRequest;
import africa.semicolon.LogisticSystem.dto.requests.UserLoginRequest;
import africa.semicolon.LogisticSystem.dto.response.*;

public interface UserServices {

    boolean isUserLoggedIn(String username);
    UserLoginResponse login(UserLoginRequest userLoginRequest);
    User findUserByNumber(String phoneNumber);
    UserSendOrderResponse sendOrder(SendOrderRequest sendOrderRequest);
    Object trackOrderById(String orderId);
    OrderPaymentResponse makePayment(OrderPaymentRequest orderPaymentRequest);
}
