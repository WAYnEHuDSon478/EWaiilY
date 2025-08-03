// 代码生成时间: 2025-08-03 08:50:55
import io.micronaut.core.annotation.Introspected;
    import java.io.*;
    import java.nio.file.*;
    import java.nio.file.attribute.BasicFileAttributes;
    import java.util.*;
    import javax.inject.Singleton;
# 优化算法效率

    /**
     * A service class for file backup and synchronization.
     */
# 增强安全性
    @Singleton
    @Introspected
    public class FileBackupSyncService {

        private final Path backupDir;

        /**
# TODO: 优化性能
         * Constructs a FileBackupSyncService with a specified backup directory.
         * @param backupDirPath The path to the backup directory.
         */
        public FileBackupSyncService(String backupDirPath) {
            this.backupDir = Paths.get(backupDirPath);
        }

        /**
         * Backups a file to the backup directory.
         * @param sourceFilePath The path to the source file.
         * @throws IOException If an I/O error occurs.
         */
        public void backupFile(Path sourceFilePath) throws IOException {
            if (!Files.exists(sourceFilePath)) {
                throw new FileNotFoundException(