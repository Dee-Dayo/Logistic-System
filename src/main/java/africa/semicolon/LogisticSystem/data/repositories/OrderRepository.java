package africa.semicolon.LogisticSystem.data.repositories;

import africa.semicolon.LogisticSystem.data.models.Order;
import africa.semicolon.LogisticSystem.data.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {
    Order findBySender(User sender);
    List<Order> findByReceiverAddress(String address);
}
