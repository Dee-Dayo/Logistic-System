package africa.semicolon.LogisticSystem.dto.requests.response;

import lombok.Data;

@Data
public class UserRegisterResponse {
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String id;
}
