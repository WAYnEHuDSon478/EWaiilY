// 代码生成时间: 2025-09-02 21:54:17
package com.example.micronaut.backup;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Body;
import javax.inject.Singleton;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.Optional;

@Singleton
@Controller("/data")
public class DataBackupService {

    private static final String BACKUP_DIR = "./backups/";
    private static final Path BACKUP_PATH = Paths.get(BACKUP_DIR);

    // Backups the data
    @Post("/backup")
    public String backupData(@Body @NonNull String data) {
        try {
            Path backupFile = createBackupFile(data);
            return "Data backed up successfully. Backup file: " + backupFile;
        } catch (IOException e) {
            return "Failed to backup data: " + e.getMessage();
        }
    }

    // Restores the data from the latest backup
    @Post("/restore")
    public String restoreData() {
        try {
            Path latestBackup = findLatestBackup();
            if (latestBackup == null) {
                return "No backup found to restore.";
            }
            String restoredData = Files.readString(latestBackup);
            return "Data restored successfully. Restored data: " + restoredData;
        } catch (IOException e) {
            return "Failed to restore data: " + e.getMessage();
        }
    }

    // Creates a backup file and writes the data to it
    private Path createBackupFile(String data) throws IOException {
        // Ensure backup directory exists
        Files.createDirectories(BACKUP_PATH);

        // Create a new backup file with a timestamp
        String timestamp = String.valueOf(System.currentTimeMillis());
        Path backupFile = BACKUP_PATH.resolve("backup_" + timestamp + ".txt");

        // Write data to the backup file
        Files.writeString(backupFile, data);
        return backupFile;
    }

    // Finds the latest backup file
    @Nullable
    private Path findLatestBackup() throws IOException {
        return Files.walk(BACKUP_PATH)
                .filter(Files::isRegularFile)
                .max(Path::compareTo);
    }
}
