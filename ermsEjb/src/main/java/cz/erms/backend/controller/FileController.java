package cz.erms.backend.controller;

import cz.erms.backend.error.ApiError;
import cz.erms.backend.model.request.FileRequest;
import cz.erms.backend.model.response.FileResponse;
import cz.erms.backend.service.FileService;
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

@Tag(name = "File", description = "File api")
@RestController
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @Operation(operationId = "getAllFiles", summary = "Get all files")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns all files",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = FileResponse.class)),
                            examples = {@ExampleObject(
                                    value = "[{ \"id\":\"f857d277-057d-4f19-b0fe-c747f07e530e\", \"userId\":\"503f99cd-7f1d-4519-8156-caa2a702704c\", \"name\":\"customFileName0\" }, { \"id\":\"f857d277-057d-4f19-b0fe-c747f07e530f\", \"userId\":\"503f99cd-7f1d-4519-8156-caa2a702704c\", \"name\":\"customFileName1\" }]"
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
    public ResponseEntity<List<FileResponse>> getAllFiles() {
        List<FileResponse> files = fileService.getAllFiles();
        return new ResponseEntity<>(files, HttpStatus.OK);
    }

    @Operation(operationId = "getFileById", summary = "Get a file by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return the file",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = FileResponse.class),
                            examples = {@ExampleObject(
                                    value = "{ \"id\":\"f857d277-057d-4f19-b0fe-c747f07e530e\", \"userId\":\"503f99cd-7f1d-4519-8156-caa2a702704c\", \"name\":\"customFileName\" }"
                            )}
                    )}),
            @ApiResponse(responseCode = "404", description = "File not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = {@ExampleObject(
                                    value = "{ \"status\":\"NOT_FOUND\", \"message\":\"Related file not found\", \"error\":\"404\", \"timestamp\":\"17-11-2024 07:26:02\" }"
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
    public ResponseEntity<FileResponse> getFileById(@PathVariable String uuid) {
        FileResponse file = fileService.getFileById(UUID.fromString(uuid));
        return new ResponseEntity<>(file, HttpStatus.OK);
    }

    @Operation(operationId = "createFile", summary = "Create a file")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Create the file",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = FileResponse.class),
                            examples = {@ExampleObject(
                                    value = "{ \"id\":\"f857d277-057d-4f19-b0fe-c747f07e530e\", \"userId\":\"503f99cd-7f1d-4519-8156-caa2a702704c\", \"name\":\"customFileName\" }"
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
    public ResponseEntity<FileResponse> createFile(
            @RequestBody @Valid FileRequest fileRequest) {
        FileResponse file = fileService.createFile(fileRequest);
        return new ResponseEntity<>(file, HttpStatus.OK);
    }

    @Operation(operationId = "updateFile", summary = "Update file by its id and file request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update the file",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = FileResponse.class),
                            examples = {@ExampleObject(
                                    value = "{ \"id\":\"f857d277-057d-4f19-b0fe-c747f07e530e\", \"userId\":\"503f99cd-7f1d-4519-8156-caa2a702704c\", \"name\":\"customFileName\" }"
                            )}
                    )}),
            @ApiResponse(responseCode = "404", description = "File not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = {@ExampleObject(
                                    value = "{ \"status\":\"NOT_FOUND\", \"message\":\"Related file not found\", \"error\":\"404\", \"timestamp\":\"17-11-2024 07:26:02\" }"
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
    public ResponseEntity<FileResponse> updateFile(@PathVariable String uuid, @RequestBody @Valid FileRequest fileRequest) {
        FileResponse file = fileService.updateFile(UUID.fromString(uuid), fileRequest);
        return new ResponseEntity<>(file, HttpStatus.OK);
    }

    @Operation(operationId = "deleteFile", summary = "Delete file by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Delete the file",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = FileResponse.class),
                            examples = {@ExampleObject()}
                    )}),
            @ApiResponse(responseCode = "404", description = "File not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = {@ExampleObject(
                                    value = "{ \"status\":\"NOT_FOUND\", \"message\":\"Related file not found\", \"error\":\"404\", \"timestamp\":\"17-11-2024 07:26:02\" }"
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
    public ResponseEntity<Void> deleteFile(@PathVariable String uuid) {
        fileService.deleteFile(UUID.fromString(uuid));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
