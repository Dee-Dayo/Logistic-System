package africa.semicolon.LogisticSystem.dto.requests;

import lombok.Data;

@Data
public class RiderRegisterRequest {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String password;
}
