package africa.semicolon.LogisticSystem.dto.response;

import lombok.Data;

@Data
public class OrderStatusResponse {
    private String orderId;
    private String dateCreated;
    private String datePayed;
    private String dateCollectedFromSender;
    private String dateDeliveredToHQTR;
    private String datePickedFromHQTR;
    private String dateDeliveredToReceiver;
}
