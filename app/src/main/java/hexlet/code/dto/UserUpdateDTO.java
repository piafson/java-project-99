package hexlet.code.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Setter
@Getter
public class UserUpdateDTO {

    private JsonNullable<String> firstName;

    private JsonNullable<String> lastName;

    @NotBlank
    @Email
    private JsonNullable<String> email;

    @NotBlank
    private JsonNullable<String> password;
}
