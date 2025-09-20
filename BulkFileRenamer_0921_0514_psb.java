// 代码生成时间: 2025-09-21 05:14:29
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.exceptions.HttpStatusException;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import javax.inject.Singleton;

/**
 * A controller for batch file renaming.
 */
@Singleton
@Controller("/api/rename")
@ExecuteOn(TaskExecutors.IO)
public class BulkFileRenamer {

    private static final String ERROR_MESSAGE = "Error occurred while renaming files: 
";

    /**
     * Handle POST request to rename files in bulk.
     * @param renameRequestBody The list of files to rename.
     * @return A HttpResponse indicating the success or failure of the operation.
     */
    @Post("/bulk")
    public HttpResponse<?> renameFiles(@Body RenameRequestBody renameRequestBody) {
        List<File> filesToRename = renameRequestBody.getFiles();
        try {
            List<String> renamedFiles = renameFiles(filesToRename, renameRequestBody.getNewNamePattern());
            return HttpResponse.ok(renamedFiles);
        } catch (IOException e) {
            return HttpResponse.serverError(ERROR_MESSAGE + e.getMessage());
        }
    }

    /**
     * Renames each file in the provided list according to the given pattern.
     * @param files The list of files to rename.
     * @param newNamePattern The new name pattern to apply.
     * @return A list of new file names.
     * @throws IOException If an I/O error occurs.
     */
    private List<String> renameFiles(List<File> files, String newNamePattern) throws IOException {
        List<String> renamedFiles = new ArrayList<>();
        for (File file : files) {
            Path path = file.toPath();
            String fileName = file.getName();
            String newFileName = String.format(newNamePattern, fileName);
            Path newPath = path.resolveSibling(newFileName);
            if (Files.exists(newPath)) {
                throw new IOException("A file with the new name already exists.");
            }
            Files.move(path, newPath);
            renamedFiles.add(newFileName);
        }
        return renamedFiles;
    }

    /**
     * A data class to hold the rename request body.
     */
    @Introspected
    public static class RenameRequestBody {
        private List<File> files;
        private String newNamePattern;

        // Standard getters and setters
        public List<File> getFiles() {
            return files;
        }

        public void setFiles(List<File> files) {
            this.files = files;
        }

        public String getNewNamePattern() {
            return newNamePattern;
        }

        public void setNewNamePattern(String newNamePattern) {
            this.newNamePattern = newNamePattern;
        }
    }
}
