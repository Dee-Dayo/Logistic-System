package africa.semicolon.LogisticSystem.dto.requests;

import africa.semicolon.LogisticSystem.data.models.Product;
import lombok.Data;

@Data
public class UserRegisterRequest {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String password;
    private Product product;
    private String address;
}
