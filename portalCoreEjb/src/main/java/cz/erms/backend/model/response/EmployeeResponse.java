package cz.erms.backend.model.response;

import lombok.Data;

import java.util.UUID;

@Data
public class EmployeeResponse {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
}
