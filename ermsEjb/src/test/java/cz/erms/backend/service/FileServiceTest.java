package cz.erms.backend.service;

import cz.erms.backend.converter.FileResponseConverter;
import cz.erms.backend.ejb.entity.File;
import cz.erms.backend.ejb.repository.FileRepository;
import cz.erms.backend.error.exception.ResourceNotFoundException;
import cz.erms.backend.model.request.FileRequest;
import cz.erms.backend.model.response.FileResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FileServiceTest {

    private static final String FILE_UUID_0 = UUID.randomUUID().toString();
    private static final String USER_UUID_0 = UUID.randomUUID().toString();
    private static final String FILE_NAME_0 = "fileName0";

    private static final String FILE_UUID_1 = UUID.randomUUID().toString();
    private static final String USER_UUID_1 = UUID.randomUUID().toString();
    private static final String FILE_NAME_1 = "fileName1";

    private static final String FILE_UUID_2 = UUID.randomUUID().toString();

    @InjectMocks
    private FileService fileService;

    @Mock
    private FileRepository fileRepository;

    @Spy
    private FileResponseConverter fileResponseConverter;

    @Test
    void getAllFiles_whenMethodIsCalled_thenCorrectResultIsReturned() {
        when(fileRepository.findAll()).thenReturn(mockFiles());

        List<FileResponse> files = fileService.getAllFiles();

        assertEquals(2, files.size());
        verify(fileRepository, times(1)).findAll();
        assertEquals(FILE_UUID_0, files.getFirst().getId().toString());
        assertEquals(USER_UUID_0, files.getFirst().getUserId().toString());
        assertEquals(FILE_NAME_0, files.getFirst().getName());
        assertEquals(FILE_UUID_1, files.get(1).getId().toString());
        assertEquals(USER_UUID_1, files.get(1).getUserId().toString());
        assertEquals(FILE_NAME_1, files.get(1).getName());
    }

    @Test
    void getFileById_whenMethodIsCalled_thenCorrectResultIsReturned() {
        when(fileRepository.findById(UUID.fromString(FILE_UUID_0))).thenReturn(Optional.of(mockFile()));

        FileResponse file = fileService.getFileById(UUID.fromString(FILE_UUID_0));

        assertNotNull(file);
        verify(fileRepository, times(1)).findById(UUID.fromString(FILE_UUID_0));
        assertEquals(FILE_UUID_0, file.getId().toString());
        assertEquals(USER_UUID_0, file.getUserId().toString());
        assertEquals(FILE_NAME_0, file.getName());
    }

    @Test
    void getFileById_whenUnknownUUIDUsed_thenThrowException() {
        when(fileRepository.findById(UUID.fromString(FILE_UUID_0))).thenReturn(Optional.empty());

        UUID uuid = UUID.fromString(FILE_UUID_0);

        assertThatThrownBy(() -> fileService.getFileById(uuid))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Related file not found");
        verify(fileRepository, times(1)).findById(UUID.fromString(FILE_UUID_0));
    }

    @Test
    void createFile_whenCorrectRequestUsed_thenSaveFile() {
        when(fileRepository.save(any(File.class))).thenReturn(mockFile());

        FileRequest fileRequest = new FileRequest();
        fileRequest.setUserId(USER_UUID_0);
        fileRequest.setName(FILE_NAME_0);

        FileResponse file = fileService.createFile(fileRequest);

        assertNotNull(file);
        verify(fileRepository, times(1)).save(any(File.class));
        assertEquals(FILE_UUID_0, file.getId().toString());
        assertEquals(USER_UUID_0, file.getUserId().toString());
        assertEquals(FILE_NAME_0, file.getName());
    }

    @Test
    void updateFile_whenCorrectRequestUsed_thenSaveFile() {
        UUID fileUUID0 = UUID.fromString(FILE_UUID_0);

        when(fileRepository.findById(fileUUID0)).thenReturn(Optional.of(mockFile()));
        when(fileRepository.save(any(File.class))).thenReturn(mockFile());

        FileRequest fileRequest = new FileRequest();
        fileRequest.setUserId(USER_UUID_0);
        fileRequest.setName(FILE_NAME_0);

        FileResponse file = fileService.updateFile(fileUUID0, fileRequest);

        assertNotNull(file);
        verify(fileRepository, times(1)).findById(fileUUID0);
        verify(fileRepository, times(1)).save(any(File.class));
        assertEquals(FILE_UUID_0, file.getId().toString());
        assertEquals(USER_UUID_0, file.getUserId().toString());
        assertEquals(FILE_NAME_0, file.getName());
    }

    @Test
    void updateFile_whenFileNotExists_thenThrowException() {
        UUID fileUUID2 = UUID.fromString(FILE_UUID_2);

        when(fileRepository.findById(fileUUID2)).thenReturn(Optional.empty());

        FileRequest fileRequest = new FileRequest();
        fileRequest.setUserId(USER_UUID_0);
        fileRequest.setName(FILE_NAME_0);

        assertThrows(ResourceNotFoundException.class, () -> {
            fileService.updateFile(fileUUID2, fileRequest);
        });

        verify(fileRepository, times(1)).findById(fileUUID2);
        verify(fileRepository, times(0)).save(any(File.class));
    }

    @Test
    void deleteFile_whenFileExists_thenDeleteFile() {
        UUID fileUUID0 = UUID.fromString(FILE_UUID_0);

        when(fileRepository.findById(fileUUID0)).thenReturn(Optional.of(mockFile()));

        fileService.deleteFile(fileUUID0);

        verify(fileRepository, times(1)).findById(fileUUID0);
        verify(fileRepository, times(1)).deleteById(fileUUID0);
    }

    @Test
    void deleteFile_whenFileNotExists_thenThrowException() {
        UUID fileUUID2 = UUID.fromString(FILE_UUID_2);

        when(fileRepository.findById(fileUUID2)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            fileService.deleteFile(fileUUID2);
        });

        verify(fileRepository, times(1)).findById(fileUUID2);
        verify(fileRepository, times(0)).deleteById(fileUUID2);
    }

    private List<File> mockFiles() {
        return Arrays.asList(
                new File(
                        UUID.fromString(FILE_UUID_0),
                        UUID.fromString(USER_UUID_0),
                        FILE_NAME_0),
                new File(
                        UUID.fromString(FILE_UUID_1),
                        UUID.fromString(USER_UUID_1),
                        FILE_NAME_1)
        );
    }

    private File mockFile(String fileName) {
        return switch (fileName) {
            case FILE_NAME_0 -> new File(
                    UUID.fromString(FILE_UUID_0),
                    UUID.fromString(USER_UUID_0),
                    FILE_NAME_0
            );
            case FILE_NAME_1 -> new File(
                    UUID.fromString(FILE_UUID_1),
                    UUID.fromString(USER_UUID_1),
                    FILE_NAME_1
            );
            default -> new File(
                    UUID.fromString(FILE_UUID_0),
                    UUID.fromString(USER_UUID_0),
                    FILE_NAME_0
            );
        };
    }

    private File mockFile() {
        return mockFile(FILE_NAME_0);
    }
}
