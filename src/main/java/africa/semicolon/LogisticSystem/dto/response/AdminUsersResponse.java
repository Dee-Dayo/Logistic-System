package africa.semicolon.LogisticSystem.dto.response;

import lombok.Data;

@Data
public class AdminUsersResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
}
