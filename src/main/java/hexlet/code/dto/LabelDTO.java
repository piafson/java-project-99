package hexlet.code.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openapitools.jackson.nullable.JsonNullable;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LabelDTO {
    private JsonNullable<Long> id;
    private JsonNullable<String> name;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private JsonNullable<LocalDate> createdAt;
}
