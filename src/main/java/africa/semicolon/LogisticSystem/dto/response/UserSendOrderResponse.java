package africa.semicolon.LogisticSystem.dto.response;

import lombok.Data;

@Data
public class UserSendOrderResponse {
    private String orderId;
    private String dateCreated;
    private int amount;
//    private String riderName;
}
