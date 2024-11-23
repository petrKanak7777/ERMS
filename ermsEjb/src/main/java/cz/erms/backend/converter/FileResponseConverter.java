package cz.erms.backend.converter;

import cz.erms.backend.ejb.entity.File;
import cz.erms.backend.model.response.FileResponse;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class FileResponseConverter implements Function<File, FileResponse> {

    @Override
    public FileResponse apply(File file) {
        FileResponse fileResponse = new FileResponse();

        fileResponse.setId(file.getId());
        fileResponse.setUserId(file.getUserId());
        fileResponse.setName(file.getName());

        return fileResponse;
    }
}
