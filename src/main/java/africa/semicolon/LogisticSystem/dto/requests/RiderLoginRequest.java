package africa.semicolon.LogisticSystem.dto.requests;

import lombok.Data;

@Data
public class RiderLoginRequest {
    private String phoneNumber;
    private String password;
}
