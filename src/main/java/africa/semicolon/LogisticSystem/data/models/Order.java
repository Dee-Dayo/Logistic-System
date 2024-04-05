package africa.semicolon.LogisticSystem.data.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document("Orders")
public class Order {
    private String id;
    private Product product;
    private User sender;
    private User receiver;
    private Rider isAssignedTo;
    private boolean isPaid;
    private boolean isPending;
    private boolean isDelivered;
    private LocalDateTime dateCreated = LocalDateTime.now();
    private LocalDateTime dateCollected;
}
