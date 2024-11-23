package cz.erms.backend.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmployeeRequest {

    @NotBlank
    @Size(min = 2, max = 256)
    @Pattern(regexp = "^[a-zA-Z]+$")
    @Schema(description = "First name value", type = "string", example = "John")
    private String firstName;

    @NotBlank
    @Size(min = 2, max = 256)
    @Pattern(regexp = "^[a-zA-Z]+$")
    @Schema(description = "Last name value", type = "string", example = "Walker")
    private String lastName;

    //todo: email regex simple form. In future, make it better.
    @NotBlank
    @Size(min = 2, max = 256)
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$")
    @Schema(description = "Email value", type = "string", example = "john.walker8953@seznam.cz")
    private String email;
}
