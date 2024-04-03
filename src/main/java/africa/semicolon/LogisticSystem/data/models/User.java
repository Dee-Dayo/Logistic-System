package africa.semicolon.LogisticSystem.data.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("Users")
public class User {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String password;
    private String address;
    private String id;
    private Product product;
    private boolean isSent;
    private boolean isReceived;
    private boolean isLoggedIn = false;
}
