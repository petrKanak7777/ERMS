package cz.erms.backend.service;

import cz.erms.backend.converter.FileResponseConverter;
import cz.erms.backend.ejb.entity.File;
import cz.erms.backend.ejb.repository.FileRepository;
import cz.erms.backend.error.exception.ResourceNotFoundException;
import cz.erms.backend.model.request.FileRequest;
import cz.erms.backend.model.response.FileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class FileService {

    private static final String FILE_NOT_FOUND = "Related file not found";

    private final FileRepository fileRepository;
    private final FileResponseConverter fileResponseConverter;

    @Autowired
    public FileService(
            FileRepository fileRepository,
            FileResponseConverter fileResponseConverter) {
        this.fileRepository = fileRepository;
        this.fileResponseConverter = fileResponseConverter;
    }

    public List<FileResponse> getAllFiles() {
        List<File> files = fileRepository.findAll();

        return files
                .stream()
                .map(fileResponseConverter)
                .toList();
    }

    public FileResponse getFileById(UUID uuid) {
        File existingFile = fileRepository.findById(uuid).orElse(null);
        if (Objects.isNull(existingFile)) {
            throw new ResourceNotFoundException(FILE_NOT_FOUND);
        }

        return fileResponseConverter.apply(existingFile);
    }

    public FileResponse createFile(FileRequest fileRequest) {

        File saveFile = new File();
        saveFile.setUserId(UUID.fromString(fileRequest.getUserId()));
        saveFile.setName(fileRequest.getName());

        File file = fileRepository.save(saveFile);

        return fileResponseConverter.apply(file);
    }

    public FileResponse updateFile(UUID uuid, FileRequest fileRequest) {
        File existingFile = fileRepository.findById(uuid).orElse(null);
        if (Objects.isNull(existingFile)) {
            throw new ResourceNotFoundException(FILE_NOT_FOUND);
        }

        existingFile.setUserId(UUID.fromString(fileRequest.getUserId()));
        existingFile.setName(fileRequest.getName());
        File file = fileRepository.save(existingFile);

        return fileResponseConverter.apply(file);
    }

    public void deleteFile(UUID uuid) {
        File existingFile = fileRepository.findById(uuid).orElse(null);
        if (Objects.isNull(existingFile)) {
            throw new ResourceNotFoundException(FILE_NOT_FOUND);
        }

        fileRepository.deleteById(uuid);
    }
}
