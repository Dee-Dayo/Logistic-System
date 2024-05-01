package africa.semicolon.LogisticSystem.services;

import africa.semicolon.LogisticSystem.data.models.Order;
import africa.semicolon.LogisticSystem.data.models.Rider;
import africa.semicolon.LogisticSystem.dto.requests.RiderLoginRequest;
import africa.semicolon.LogisticSystem.dto.response.RiderLoginResponse;

public interface RiderService {
    Rider findRider();
    Rider findRiderByNumber(String number);
    int findNoOfAvailableRiders();
    RiderLoginResponse login(RiderLoginRequest riderLoginRequest);
    Order pickupItemFromCustomer(Order order);
//    Order deliverItemToReceiver(Order pickedUpOrder);

    void deliverItems(Rider rider);
}
