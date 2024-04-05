package africa.semicolon.LogisticSystem.dto.requests.response;

import lombok.Data;

@Data
public class UserSendOrderResponse {
    private String orderId;
    private String dateCreated;
    private boolean orderStatus;
    private String riderName;
}
