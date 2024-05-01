package africa.semicolon.LogisticSystem.dto.response;

import lombok.Data;

@Data
public class UndeliveredOrderStatusResponse {
    private String orderId;
    private int orderAmount;
    private String dateCreated;
    private String datePickedUp;
    private String dateDroppedAtHQTR;
}
