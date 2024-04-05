package africa.semicolon.LogisticSystem.dto.response;

import lombok.Data;

@Data
public class OrderPaymentResponse {
    private String orderId;
    private String dateCreated;
    private String riderName;
    private String dateCollected;
    private boolean paymentStatus;

}
