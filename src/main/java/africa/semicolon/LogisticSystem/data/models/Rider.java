package africa.semicolon.LogisticSystem.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document("Riders")
public class Rider {
    private String id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String password;
    private boolean isLoggedIn;
    private List<Order> orders = new ArrayList<>();
    private List<Order> completedOrders = new ArrayList<>();
    private List<Order> pendingOrders = new ArrayList<>();
    private boolean isAvailable;
}
