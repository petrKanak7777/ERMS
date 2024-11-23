package cz.erms.backend.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class FileRequest {

    @NotBlank
    @Size(min = 36, max = 36)
    @Pattern(regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}+$")
    @Schema(description = "User uuid value", type = "string", example = "d8e75fdd-bc0a-4ff3-9258-1f92687f12d6")
    private String userId;

    @NotBlank
    @Size(min = 3, max = 256)
    @Pattern(regexp = "^[a-zA-Z0-9\\s]+$")
    @Schema(description = "File name value", type = "string", example = "customFileName0")
    private String name;
}
