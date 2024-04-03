package africa.semicolon.LogisticSystem.services;

import africa.semicolon.LogisticSystem.dto.requests.SendOrderRequest;
import africa.semicolon.LogisticSystem.dto.requests.UserRegisterRequest;

public interface AdminServices {
    void collectOrder(SendOrderRequest sendOrderRequest);

    Long findNoOfUsers();

    void register(UserRegisterRequest userRegisterRequest);

    int findNoOfRiders();
}
