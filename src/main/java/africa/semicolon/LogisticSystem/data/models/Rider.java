package africa.semicolon.LogisticSystem.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("Riders")
public class Rider {
    private String id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String password;
    private boolean isLoggedIn;
    private Order order;
    private boolean isAvailable;
}
