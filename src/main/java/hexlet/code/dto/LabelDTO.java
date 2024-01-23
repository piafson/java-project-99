package hexlet.code.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openapitools.jackson.nullable.JsonNullable;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LabelDTO {
    private JsonNullable<Long> id;
    private JsonNullable<String> name;
    private JsonNullable<Date> createdAt;
}
