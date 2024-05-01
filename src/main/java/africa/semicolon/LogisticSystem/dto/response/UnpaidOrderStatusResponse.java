package africa.semicolon.LogisticSystem.dto.response;

import lombok.Data;

@Data
public class UnpaidOrderStatusResponse {
    private String orderId;
    private int orderAmount;
    private String dateCreated;
}
