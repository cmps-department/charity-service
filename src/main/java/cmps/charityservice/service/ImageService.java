package cmps.charityservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class ImageService {

    private final Path imageStorageLocation;

    public ImageService(@Value("${app.upload.directory}") String imagesDirectory) throws IOException {
        this.imageStorageLocation = Paths.get(imagesDirectory)
                .toAbsolutePath();

        if (Files.notExists(imageStorageLocation)) {
            Files.createDirectories(imageStorageLocation);
        }
    }

    public String saveImage(final MultipartFile image) {
        String imageId = UUID.randomUUID()
                .toString();
        Path path = imageStorageLocation.resolve(imageId);

        try {
            Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Can't save image", e);
        }

        return imageId;
    }

    public Resource getImage(String imageId) {
        Path path = imageStorageLocation.resolve(imageId);

        try {
            return new UrlResource(path.toUri());
        } catch (IOException e) {
            throw new RuntimeException("Can't get image", e);
        }
    }
}
