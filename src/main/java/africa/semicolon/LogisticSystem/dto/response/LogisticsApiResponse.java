package africa.semicolon.LogisticSystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LogisticsApiResponse {
    boolean isSuccess;
    Object logisticsSystemResponse;
}
