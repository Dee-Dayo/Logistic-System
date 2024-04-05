package africa.semicolon.LogisticSystem.dto.requests;

import africa.semicolon.LogisticSystem.data.models.Product;
import africa.semicolon.LogisticSystem.data.models.User;
import lombok.Data;

@Data
public class SendOrderRequest {
    private String senderPhone;
    private Product product;
    private String receiverName;
    private String receiverPhone;
    private String receiverAddress;
}
