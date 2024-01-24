package hexlet.code.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openapitools.jackson.nullable.JsonNullable;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {

    private JsonNullable<Long> id;

    private JsonNullable<Integer> index;

    @NotBlank
    private JsonNullable<String> title;

    private JsonNullable<String> content;

    @NotNull
    private JsonNullable<String> status;

    private JsonNullable<Long> assignee_id;

    private JsonNullable<List<Long>> taskLabelIds;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private JsonNullable<LocalDate> createdAt;

}
