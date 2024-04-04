package africa.semicolon.LogisticSystem.data.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("Riders")
public class Rider {
    private String riderId;
    private String firstName;
    private String lastName;
    private Product product;
    private boolean isAvailable = true;
}
