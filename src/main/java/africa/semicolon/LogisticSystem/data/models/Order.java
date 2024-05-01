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
    private String receiverName;
    private String receiverPhone;
    private String receiverAddress;
    private Rider isAssignedTo;
    private int amount;
    private boolean isPaid;
    private boolean isPending;
    private boolean isDelivered;
    private LocalDateTime dateCreated = LocalDateTime.now();
    private LocalDateTime datePaymentMade;
    private LocalDateTime datePickedUp;
    private LocalDateTime dateDeliveredToHQ;
    private LocalDateTime datePickedUpFromHQ;
    private LocalDateTime dateDeliveredToReceiver;
}
