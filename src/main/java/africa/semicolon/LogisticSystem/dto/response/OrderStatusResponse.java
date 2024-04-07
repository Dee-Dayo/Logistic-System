package africa.semicolon.LogisticSystem.dto.response;

import lombok.Data;

@Data
public class OrderStatusResponse {
    private String orderId;
    private String dateCreated;
    private boolean paymentMade;
    private String dateCollected;
    private boolean completed;
    private String dateDelivered;
}
