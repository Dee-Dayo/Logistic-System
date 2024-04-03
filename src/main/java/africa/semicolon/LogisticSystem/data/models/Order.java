package africa.semicolon.LogisticSystem.data.models;

import lombok.Data;

@Data
public class Order {
    private String orderId;
    private Product product;
    private User sender;
    private User receiver;
}
