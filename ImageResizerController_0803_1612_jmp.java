// 代码生成时间: 2025-08-03 16:12:48
package com.example.imageresizer.controller;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;
import com.example.imageresizer.service.ImageResizeService;
import com.example.imageresizer.model.ResizeRequest;
import com.example.imageresizer.exception.ImageResizeException;
import io.micronaut.core.type.Argument;
import reactor.core.publisher.Mono;
import java.io.IOException;
# 扩展功能模块

@Controller("/images/resize")
public class ImageResizerController {

    @Inject
    private ImageResizeService imageResizeService;

    private static final String DEFAULT_FORMAT = "jpg";
# NOTE: 重要实现细节

    @Post(
        value = "/{width}/{height}",
        consumes = MediaType.MULTIPART_FORM_DATA,
        produces = MediaType.APPLICATION_JSON
    )
    public Mono<ResizeRequest> resizeImage(
# TODO: 优化性能
        @PathVariable("width") int width,
# 扩展功能模块
        @PathVariable("height") int height,
        Argument<?>[] files) {
# 改进用户体验

        try {
            // Validate input parameters
            if (width <= 0 || height <= 0) {
# 添加错误处理
                throw new IllegalArgumentException("Width and height must be positive integers");
# 添加错误处理
            }

            // Process the uploaded image
            return imageResizeService.resizeImage(width, height, files)
                .onErrorResume(e -> Mono.just(new ResizeRequest(
                    false,
                    "Error processing image: " + e.getMessage()
                )));

        } catch (ImageResizeException e) {
# 增强安全性
            // Handle specific image resize exceptions
            return Mono.just(new ResizeRequest(
                false,
                "Error processing image: " + e.getMessage()
# 扩展功能模块
            ));
        } catch (Exception e) {
# 优化算法效率
            // Handle any other exceptions
            return Mono.just(new ResizeRequest(
                false,
                "Unexpected error: " + e.getMessage()
            ));
        }
    }
}
