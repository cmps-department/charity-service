package cmps.charityservice.controller;

import cmps.charityservice.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/images")
@CrossOrigin
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping
    public List<String> uploadImages(@RequestParam("images") List<MultipartFile> images) {
        return images.stream()
                .map(imageService::saveImage)
                .collect(Collectors.toList());
    }

    @GetMapping("/{imageId}")
    public ResponseEntity<Resource> downloadImage(@PathVariable String imageId) {
        Resource resource = imageService.getImage(imageId);
        if (!resource.exists()) {
            return ResponseEntity.notFound()
                    .build();
        }

        return ResponseEntity.ok()
                .body(resource);
    }
}
