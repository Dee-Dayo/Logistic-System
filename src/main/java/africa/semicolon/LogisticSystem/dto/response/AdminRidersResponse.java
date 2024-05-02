package africa.semicolon.LogisticSystem.dto.response;

import lombok.Data;

@Data
public class AdminRidersResponse {
    private String id;
    private String firstname;
    private String lastName;
    private String phoneNumber;
    private boolean isAvailable;
}
