// 代码生成时间: 2025-08-25 07:24:20
package com.example.imageresizer.controller;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.RequestBody;
import io.micronaut.http.multipart.CompletedFileUpload;
import io.micronaut.http.multipart.FileUpload;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller("/images")
public class ImageResizerController {

    private static final String TEMP_DIR = "/tmp/imageresizer";
    private static final int MAX_WIDTH = 800; // Maximum width for resized images
    private static final int MAX_HEIGHT = 600; // Maximum height for resized images

    @Post(value = "/resize", consumes = MediaType.MULTIPART_FORM_DATA)
    public Mono<String> resizeImages(@RequestBody Flux<CompletedFileUpload> files) {
        return files.flatMap(this::resizeImage).collect(Collectors.toList())
                .map(resizedPaths -> "Resized images saved to: " + resizedPaths);
    }

    private Mono<String> resizeImage(CompletedFileUpload file) {
        String fileName = file.getFilename();
        String extension = fileName.substring(fileName.lastIndexOf('.'));
        String tempFileName = UUID.randomUUID().toString() + extension;
        String tempFilePath = TEMP_DIR + "/" + tempFileName;
        
        try {
            Path tempFile = Paths.get(tempFilePath);
            file.transferTo(tempFile);
            BufferedImage originalImage = ImageIO.read(tempFile.toFile());
            BufferedImage resizedImage = resizeImage(originalImage);
            String resizedFilePath = saveResizedImage(resizedImage, tempFileName);
            return Mono.just(resizedFilePath);
        } catch (IOException e) {
            e.printStackTrace();
            return Mono.error(e);
        }
    }

    private BufferedImage resizeImage(BufferedImage originalImage) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        double ratio = Math.min((double) MAX_WIDTH / width, (double) MAX_HEIGHT / height);
        int newWidth = (int) (width * ratio);
        int newHeight = (int) (height * ratio);
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, originalImage.getType());
        resizedImage.getGraphics().drawImage(originalImage.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH), 0, 0, null);
        return resizedImage;
    }

    private String saveResizedImage(BufferedImage resizedImage, String filename) throws IOException {
        String resizedFilePath = TEMP_DIR + "/" + filename;
        Path outputPath = Paths.get(resizedFilePath);
        Files.createDirectories(outputPath.getParent());
        ImageIO.write(resizedImage, filename.substring(filename.lastIndexOf('.') + 1), outputPath.toFile());
        return resizedFilePath;
    }
}
