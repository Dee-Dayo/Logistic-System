package africa.semicolon.LogisticSystem.dto.requests;

import lombok.Data;

@Data
public class OrderPaymentRequest {
    private String orderId;
    private boolean isPaid;
}
