package africa.semicolon.LogisticSystem.dto.requests.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LogisticsApiResponse {
    boolean isSuccessful;
    Object logisticsSystemResponse;
}
