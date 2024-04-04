package africa.semicolon.LogisticSystem.data.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("Orders")
public class Order {
    private String id;
    private Product product;
    private User sender;
    private User receiver;
    private Rider isAssignedTo;
    private boolean isDelivered;
}
