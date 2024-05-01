package africa.semicolon.LogisticSystem.dto.response;

import africa.semicolon.LogisticSystem.data.models.Product;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderResponse {
    private String orderId;
    private Product product;
    private String sender;
    private String receiver;
    private String receiverAddress;
    private int amount;
    private boolean isPaid;
    private boolean isDelivered;
//    private LocalDateTime dateCreated;
//    private LocalDateTime datePaymentMade;
//    private LocalDateTime datePickedUp;
//    private LocalDateTime dateDeliveredToHQ;
//    private LocalDateTime datePickedUpFromHQ;
//    private LocalDateTime dateDeliveredToReceiver;
}
