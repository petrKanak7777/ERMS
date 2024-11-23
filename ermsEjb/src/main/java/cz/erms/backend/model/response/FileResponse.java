package cz.erms.backend.model.response;

import lombok.Data;

import java.util.UUID;

@Data
public class FileResponse {
    private UUID id;
    private UUID userId;
    private String name;
}
