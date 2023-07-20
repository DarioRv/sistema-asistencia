package ar.edu.unju.fi.registroasistencia.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class UploadFile {
    private final static String UPLOADS_FOLDER = "src/main/resources/static/uploads";

    public Path getPath(String filename) {
        return Paths.get(UPLOADS_FOLDER).resolve(filename).toAbsolutePath();
    }

    public String copy(MultipartFile file) throws IOException {
        String uniqueFilename = file.getOriginalFilename();
        Path rootPath = getPath(uniqueFilename);
        Files.copy(file.getInputStream(), rootPath);
        return uniqueFilename;
    }

    public boolean delete(String filename) {
        Path rootPath = getPath(filename);
        File file = rootPath.toFile();
        if (file.exists() && file.canRead()) {
            if (file.delete()) {
                return true;
            }
        }
        return false;
    }
}
