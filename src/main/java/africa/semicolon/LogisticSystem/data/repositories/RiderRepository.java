package africa.semicolon.LogisticSystem.data.repositories;

import africa.semicolon.LogisticSystem.data.models.Rider;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RiderRepository extends MongoRepository<Rider,String> {
}
