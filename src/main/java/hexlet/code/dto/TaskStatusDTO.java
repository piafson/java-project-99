package hexlet.code.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openapitools.jackson.nullable.JsonNullable;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskStatusDTO {

    private JsonNullable<Long> id;

    @NotBlank
    private JsonNullable<String> name;

    @NotBlank
    private JsonNullable<String> slug;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private JsonNullable<LocalDate> createdAt;
}
