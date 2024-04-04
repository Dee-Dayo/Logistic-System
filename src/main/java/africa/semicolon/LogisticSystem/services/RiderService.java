package africa.semicolon.LogisticSystem.services;

import africa.semicolon.LogisticSystem.data.models.Order;
import africa.semicolon.LogisticSystem.data.models.Rider;

public interface RiderService {
    Rider findRider();

    void assignOrder(Rider rider, Order order);
}
