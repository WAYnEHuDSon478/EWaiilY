// 代码生成时间: 2025-10-01 02:38:29
package com.example.imageresizer;
# 扩展功能模块

import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
# 改进用户体验
import io.micronaut.http.exceptions.HttpStatusException;
# NOTE: 重要实现细节
import io.micronaut.http.server.util.HttpHeaderValue;
import javax.imageio.ImageIO;
# NOTE: 重要实现细节
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
# NOTE: 重要实现细节
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller("/images")
# 增强安全性
@Introspected
public class ImageResizer {
    // Specify the source and destination directories
    private static final String SOURCE_DIRECTORY = "/path/to/source";
    private static final String DESTINATION_DIRECTORY = "/path/to/destination";
    private static final int NEW_WIDTH = 800; // New width for resized images
    private static final int NEW_HEIGHT = 600; // New height for resized images

    @Get(value = "/resize/{width}/{height}", produces = MediaType.TEXT_PLAIN)
    public HttpResponse<String> resizeImages(@PathVariable int width, @PathVariable int height) {
        try {
            // Validate input parameters
            if (width <= 0 || height <= 0) {
                throw new IllegalArgumentException("Width and height must be positive integers.");
# TODO: 优化性能
            }

            // Resize images
# FIXME: 处理边界情况
            resizeImagesInDirectory(SOURCE_DIRECTORY, DESTINATION_DIRECTORY, width, height);

            return HttpResponse.ok("Images resized successfully.");
# 添加错误处理
        } catch (IOException e) {
            throw new HttpStatusException(HttpResponse.badRequest(), "Error processing images: " + e.getMessage());
        } catch (IllegalArgumentException e) {
# 优化算法效率
            throw new HttpStatusException(HttpResponse.badRequest(), e.getMessage());
# TODO: 优化性能
        }
    }

    /**
     * Resizes images in the specified directory and saves them to the destination directory.
     *
     * @param sourceDir The directory containing the original images.
     * @param destDir The directory where resized images will be saved.
     * @param width The new width for the resized images.
     * @param height The new height for the resized images.
     * @throws IOException If an error occurs while processing images.
     */
    private void resizeImagesInDirectory(String sourceDir, String destDir, int width, int height) throws IOException {
        File sourceDirFile = new File(sourceDir);
        if (!sourceDirFile.exists() || !sourceDirFile.isDirectory()) {
# 增强安全性
            throw new IOException("Source directory does not exist or is not a directory.");
        }

        File destDirFile = new File(destDir);
        if (!destDirFile.exists() && !destDirFile.mkdirs()) {
            throw new IOException("Failed to create destination directory.");
        }

        File[] files = sourceDirFile.listFiles((dir, name) -> name.toLowerCase().endsWith(".jpg"));
        if (files == null || files.length == 0) {
# TODO: 优化性能
            throw new IOException("No image files found in the source directory.");
# 扩展功能模块
        }
# 增强安全性

        for (File file : files) {
            resizeImage(file, width, height, destDir);
        }
    }

    /**
# 增强安全性
     * Resizes a single image and saves it to the destination directory.
     *
     * @param file The image file to resize.
     * @param width The new width for the resized image.
     * @param height The new height for the resized image.
     * @param destDir The directory where the resized image will be saved.     * @throws IOException If an error occurs while processing the image.
# TODO: 优化性能
     */
# 优化算法效率
    private void resizeImage(File file, int width, int height, String destDir) throws IOException {
        BufferedImage originalImage = ImageIO.read(file);
        BufferedImage resizedImage = new BufferedImage(width, height, originalImage.getType());
        resizedImage.getGraphics().drawImage(originalImage.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH), 0, 0, null);
# 添加错误处理

        Path destPath = Paths.get(destDir, file.getName());
        ImageIO.write(resizedImage, "jpg", destPath.toFile());
    }
}
