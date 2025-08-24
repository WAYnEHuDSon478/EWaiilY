// 代码生成时间: 2025-08-24 14:05:56
import io.micronaut.core.annotation.Introspected;
    import java.io.File;
    import java.io.IOException;
    import java.nio.file.Files;
    import java.nio.file.Path;
    import java.nio.file.Paths;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.stream.Collectors;
    
    /**
     * FolderStructureOrganizer is a class designed to organize the structure of a folder.
     * It sorts files into subdirectories based on file extensions.
     */
    @Introspected
    public class FolderStructureOrganizer {
    
        private final String rootFolderPath;
    
        /**
         * Constructs a FolderStructureOrganizer with the given root folder path.
         *
         * @param rootFolderPath The path to the root folder to organize.
         */
        public FolderStructureOrganizer(String rootFolderPath) {
            this.rootFolderPath = rootFolderPath;
        }
    
        /**
         * Organizes the files in the root folder into subdirectories based on their file extensions.
         *
         * @throws IOException If an I/O error occurs.
         */
        public void organize() throws IOException {
            File rootFolder = new File(rootFolderPath);
            if (!rootFolder.exists() || !rootFolder.isDirectory()) {
                throw new IllegalArgumentException("The provided path is not a valid directory: " + rootFolderPath);
            }
    
            // Get all files in the root directory
            File[] files = rootFolder.listFiles();
            if (files == null) {
                throw new IOException("There was a problem accessing the directory: " + rootFolderPath);
            }
    
            // Group files by their extensions
            List<File> filesGroupedByExtension = groupFilesByExtension(files);
    
            // Create subdirectories and move files
            for (File file : filesGroupedByExtension) {
                String extension = getFileExtension(file);
                File subDir = new File(rootFolder, extension);
                if (!subDir.exists()) {
                    subDir.mkdir();
                }
                file.renameTo(new File(subDir, file.getName()));
            }
        }
    
        /**
         * Groups files by their extensions.
         *
         * @param files Array of files to group.
         * @return A list of files grouped by extension.
         */
        private List<File> groupFilesByExtension(File[] files) {
            if (files == null) {
                return new ArrayList<>();
            }
    
            return List.of(files)
                    .stream()
                    .filter(File::isFile)
                    .collect(Collectors.groupingBy(this::getFileExtension))
                    .values()
                    .stream()
                    .flatMap(List::stream)
                    .collect(Collectors.toList());
        }
    
        /**
         * Retrieves the file extension from a file.
         *
         * @param file The file to retrieve the extension from.
         * @return The file extension or an empty string if the file has no extension.
         */
        private String getFileExtension(File file) {
            String name = file.getName();
            int lastIndexOf = name.lastIndexOf('.');
            if (lastIndexOf == -1) {
                return 