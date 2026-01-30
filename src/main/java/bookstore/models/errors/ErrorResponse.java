package bookstore.models.errors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private String type;
    private String title;
    private Integer status;
    private String traceId;
    private Map<String, List<String>> errors;
}
