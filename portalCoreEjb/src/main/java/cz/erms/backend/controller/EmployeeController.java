package cz.erms.backend.controller;

import cz.erms.backend.error.ApiError;
import cz.erms.backend.model.request.EmployeeRequest;
import cz.erms.backend.model.response.EmployeeResponse;
import cz.erms.backend.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Tag(name = "Employee", description = "Employee api")
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Operation(operationId = "getAllEmployees", summary = "Get all employees")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns all employees",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = EmployeeResponse.class)),
                            examples = {@ExampleObject(
                                    value = "[{ \"id\":\"f857d277-057d-4f19-b0fe-c747f07e530e\", \"firstName\":\"John\", \"lastName\":\"Doe\", \"email\":\"john.doe123@gmail.com\" }, { \"id\":\"f857d277-057d-4f19-b0fe-c747f07e530e\", \"firstName\":\"Anna\", \"lastName\":\"Freeman\", \"email\":\"anna.freeman123@gmail.com\" }]"
                            )})
                    }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = {@ExampleObject(
                                    value = "{ \"status\":\"INTERNAL_SERVER_ERROR\", \"message\":\"java.lang.IllegalArgumentException Invalid UUID string: errorUUID\", \"error\":\"500\", \"timestamp\":\"17-11-2024 06:56:19\" }"
                            )}
                    )})
    })
    @GetMapping("")
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees() {
        List<EmployeeResponse> files = employeeService.getAllEmployees();
        return new ResponseEntity<>(files, HttpStatus.OK);
    }

    @Operation(operationId = "getEmployeeById", summary = "Get a employee by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return the file",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmployeeResponse.class),
                            examples = {@ExampleObject(
                                    value = "{ \"id\":\"f857d277-057d-4f19-b0fe-c747f07e530e\", \"firstName\":\"John\", \"lastName\":\"Doe\", \"email\":\"john.doe123@gmail.com\" }"
                            )}
                    )}),
            @ApiResponse(responseCode = "404", description = "Employee not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = {@ExampleObject(
                                    value = "{ \"status\":\"NOT_FOUND\", \"message\":\"Related employee not found\", \"error\":\"404\", \"timestamp\":\"17-11-2024 07:26:02\" }"
                            )}
                    )}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = {@ExampleObject(
                                    value = "{ \"status\":\"INTERNAL_SERVER_ERROR\", \"message\":\"java.lang.IllegalArgumentException Invalid UUID string: errorUUID\", \"error\":\"500\", \"timestamp\":\"17-11-2024 06:56:19\" }"
                            )}
                    )})
    })
    @GetMapping("/{uuid}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable String uuid) {
        EmployeeResponse employee = employeeService.getEmployeeById(UUID.fromString(uuid));
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @Operation(operationId = "createEmployee", summary = "Create a employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Create the employee",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmployeeResponse.class),
                            examples = {@ExampleObject(
                                    value = "{ \"id\":\"f857d277-057d-4f19-b0fe-c747f07e530e\", \"firstName\":\"John\", \"lastName\":\"Doe\", \"email\":\"john.doe123@gmail.com\" }"
                            )}
                    )}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = {@ExampleObject(
                                    value = "{ \"status\":\"INTERNAL_SERVER_ERROR\", \"message\":\"java.lang.IllegalArgumentException Invalid UUID string: errorUUID\", \"error\":\"500\", \"timestamp\":\"17-11-2024 06:56:19\" }"
                            )}
                    )})
    })
    @PostMapping("")
    public ResponseEntity<EmployeeResponse> createEmployee(
            @RequestBody @Valid EmployeeRequest employeeRequest) {
        EmployeeResponse employee = employeeService.createEmployee(employeeRequest);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @Operation(operationId = "updateEmployee", summary = "Update employee by its id and employee request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update the employee",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmployeeResponse.class),
                            examples = {@ExampleObject(
                                    value = "{ \"id\":\"f857d277-057d-4f19-b0fe-c747f07e530e\", \"firstName\":\"John\", \"lastName\":\"Doe\", \"email\":\"john.doe123@gmail.com\" }"
                            )}
                    )}),
            @ApiResponse(responseCode = "404", description = "Employee not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = {@ExampleObject(
                                    value = "{ \"status\":\"NOT_FOUND\", \"message\":\"Related employee not found\", \"error\":\"404\", \"timestamp\":\"17-11-2024 07:26:02\" }"
                            )}
                    )}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = {@ExampleObject(
                                    value = "{ \"status\":\"INTERNAL_SERVER_ERROR\", \"message\":\"java.lang.IllegalArgumentException Invalid UUID string: errorUUID\", \"error\":\"500\", \"timestamp\":\"17-11-2024 06:56:19\" }"
                            )}
                    )})
    })
    @PutMapping("/{uuid}")
    public ResponseEntity<EmployeeResponse> updateEmployee(@PathVariable String uuid, @RequestBody @Valid EmployeeRequest employeeRequest) {
        EmployeeResponse employee = employeeService.updateEmployee(UUID.fromString(uuid), employeeRequest);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @Operation(operationId = "deleteEmployee", summary = "Delete employee by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Delete the employee",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmployeeResponse.class),
                            examples = {@ExampleObject()}
                    )}),
            @ApiResponse(responseCode = "404", description = "File not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = {@ExampleObject(
                                    value = "{ \"status\":\"NOT_FOUND\", \"message\":\"Related employee not found\", \"error\":\"404\", \"timestamp\":\"17-11-2024 07:26:02\" }"
                            )}
                    )}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = {@ExampleObject(
                                    value = "{ \"status\":\"INTERNAL_SERVER_ERROR\", \"message\":\"java.lang.IllegalArgumentException Invalid UUID string: errorUUID\", \"error\":\"500\", \"timestamp\":\"17-11-2024 06:56:19\" }"
                            )}
                    )})
    })
    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable String uuid) {
        employeeService.deleteEmployee(UUID.fromString(uuid));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
