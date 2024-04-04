package africa.semicolon.LogisticSystem.services;

import africa.semicolon.LogisticSystem.data.models.Order;
import africa.semicolon.LogisticSystem.dto.requests.SendOrderRequest;
import africa.semicolon.LogisticSystem.dto.requests.UserRegisterRequest;

public interface AdminServices {

    Long findNoOfUsers();

    void register(UserRegisterRequest userRegisterRequest);

    int findNoOfRiders();

    void takeOrder(Order order);
}
