package africa.semicolon.LogisticSystem.dto.requests.requests;

import africa.semicolon.LogisticSystem.data.models.Product;
import africa.semicolon.LogisticSystem.data.models.User;
import lombok.Data;

@Data
public class SendOrderRequest {
    private User sender;
    private Product product;
    private User receiver;
//    private boolean isPaid;
}
