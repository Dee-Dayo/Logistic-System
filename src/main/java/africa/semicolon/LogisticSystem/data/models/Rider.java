package africa.semicolon.LogisticSystem.data.models;

import lombok.Data;

@Data
public class Rider {
    private String riderId;
    private String firstName;
    private String lastName;
    private Product product;
    private boolean isAvailable = true;
}
