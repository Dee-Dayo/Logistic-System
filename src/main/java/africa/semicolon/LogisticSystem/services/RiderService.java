package africa.semicolon.LogisticSystem.services;

import africa.semicolon.LogisticSystem.data.models.Order;
import africa.semicolon.LogisticSystem.data.models.Rider;
import africa.semicolon.LogisticSystem.dto.requests.RiderLoginRequest;
import africa.semicolon.LogisticSystem.dto.requests.UserLoginRequest;
import africa.semicolon.LogisticSystem.dto.response.RiderLoginResponse;
import africa.semicolon.LogisticSystem.dto.response.UserLoginResponse;

public interface RiderService {
    Rider findRider();
    Rider findRiderByNumber(String number);
    void assignOrder(Rider rider, Order order);
    int findNoOfAvailableRiders();
    void save(Rider rider1);
    RiderLoginResponse login(RiderLoginRequest riderLoginRequest);
}
