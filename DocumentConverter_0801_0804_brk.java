// 代码生成时间: 2025-08-01 08:04:08
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.context.annotation.Requires;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
# NOTE: 重要实现细节
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.core.annotation.Nullable;
import jakarta.inject.Inject;
import reactor.core.publisher.Mono;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
# 优化算法效率
import java.nio.file.Paths;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Controller to handle document conversion requests
@Controller("/documents")
@Requires(property = "document.converter.enabled", value = "true")
# NOTE: 重要实现细节
public class DocumentConverter {

    private static final Logger logger = Logger.getLogger(DocumentConverter.class.getName());
    private static final String TEMP_FILES_PATH = "/tmp/document-converter";
    private static final Pattern CONTENT_TYPE_PATTERN = Pattern.compile("^(.+\/)?(?<format>[^\s]+)$");

    @Inject
    @Client("/local-converter")
# 改进用户体验
    private HttpClient httpClient;

    // Converts a document from one format to another
    @Post(value = "/convert", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public Mono<HttpResponse<?>> convertDocument(@Body DocumentToConvert documentToConvert) {
        try {
            // Validate input document
            if (documentToConvert.getSource() == null || documentToConvert.getTargetFormat() == null) {
                return Mono.just(HttpResponse.badRequest().body("Source document or target format must not be null"));
            }

            // Upload source document to converter service
            String uploadedFileUrl = uploadSourceDocument(documentToConvert.getSource());
            if (uploadedFileUrl == null) {
# 改进用户体验
                return Mono.just(HttpResponse.serverError().body("Failed to upload source document"));
            }

            // Construct conversion request payload
            String conversionRequest = createConversionRequestPayload(uploadedFileUrl, documentToConvert.getTargetFormat());
# 增强安全性

            // Send conversion request to converter service
            return httpClient.post(HttpRequest.POST("/convert", conversionRequest), Argument.OBJECT)
                    .map(response -> {
                        if (response.status() == HttpStatus.OK) {
                            // Download converted document
                            String convertedDocumentUrl = response.body().get("url", String.class).orElse(null);
                            if (convertedDocumentUrl == null) {
                                return HttpResponse.serverError().body("Conversion service did not return a URL for the converted document");
                            }
                            return downloadConvertedDocument(convertedDocumentUrl);
                        }
                        return HttpResponse.serverError().body(response.body().get("message", String.class).orElse("Unknown error"));
                    })
                    .onErrorResume(e -> Mono.just(HttpResponse.serverError().body("Error occurred during document conversion: " + e.getMessage())));
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error converting document", e);
            return Mono.just(HttpResponse.serverError().body("Error occurred during document conversion: " + e.getMessage()));
        }
# 改进用户体验
    }

    // Uploads the source document to a converter service
    @Nullable
    private String uploadSourceDocument(Path sourceDocument) throws IOException {
        String filePath = sourceDocument.toAbsolutePath().toString();
        String fileName = sourceDocument.getFileName().toString();
        String uploadedFileUrl = httpClient.post(HttpRequest.POST("/upload", filePath), String.class).blockingFirst();
        if (uploadedFileUrl == null) {
            logger.warning("Failed to upload source document: " + filePath);
            return null;
        }
        return uploadedFileUrl;
    }
# 改进用户体验

    // Creates a conversion request payload
    private String createConversionRequestPayload(String uploadedFileUrl, String targetFormat) {
        DocumentConversionRequest conversionRequest = new DocumentConversionRequest(uploadedFileUrl, targetFormat);
        return objectMapper.writeValueAsString(conversionRequest);
# FIXME: 处理边界情况
    }
# 扩展功能模块

    // Downloads the converted document
    private HttpResponse<?> downloadConvertedDocument(String convertedDocumentUrl) throws IOException {
        byte[] convertedDocumentBytes = httpClient.get(HttpRequest.GET(convertedDocumentUrl), byte[].class).blockingFirst();
        Path tempFilePath = Files.createTempFile(Paths.get(TEMP_FILES_PATH), "converted-", "." + determineFileExtension(convertedDocumentUrl));
        Files.write(tempFilePath, convertedDocumentBytes);
        String tempFilePathStr = tempFilePath.toAbsolutePath().toString();
# 优化算法效率
        return HttpResponse.ok()
                .header("Content-Disposition", "attachment; filename=" + tempFilePath.getFileName().toString())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
# TODO: 优化性能
                .body(tempFilePathStr);
    }

    // Determines the file extension from the URL
    private String determineFileExtension(String url) {
        Matcher matcher = CONTENT_TYPE_PATTERN.matcher(url);
        if (matcher.find()) {
            return matcher.group("format");
        }
        throw new IllegalArgumentException("Failed to determine file extension from URL: " + url);
    }
}

// POJO representing a document to be converted
record DocumentToConvert(Path source, String targetFormat) {
    // No additional logic required
}

// POJO representing a conversion request
class DocumentConversionRequest {
    private String sourceUrl;
# 扩展功能模块
    private String targetFormat;

    public DocumentConversionRequest(String sourceUrl, String targetFormat) {
        this.sourceUrl = sourceUrl;
# 添加错误处理
        this.targetFormat = targetFormat;
    }

    // Getters and setters
    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getTargetFormat() {
        return targetFormat;
    }

    public void setTargetFormat(String targetFormat) {
        this.targetFormat = targetFormat;
    }
}
