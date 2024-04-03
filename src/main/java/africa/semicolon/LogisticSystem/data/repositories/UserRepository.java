package africa.semicolon.LogisticSystem.data.repositories;

import africa.semicolon.LogisticSystem.data.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findByPhoneNumber(String lowerCase);

    boolean existsByPhoneNumber(String phoneNumber);
}
