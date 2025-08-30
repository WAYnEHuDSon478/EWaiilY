// 代码生成时间: 2025-08-30 10:17:41
// 文件夹结构整理器
// 该程序用于整理文件夹结构，确保文件按照一定的规则进行组织。
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.micronaut.context.annotation.Requires;
import io.micronaut.core.annotation.Nullable;
import javax.inject.Singleton;

@Singleton
@Requires(env = "dev") // 仅在开发环境下启用
public class FolderStructureOrganizer {

    // 整理指定文件夹结构的方法
    public void organizeFolderStructure(String folderPath) {
        File folder = new File(folderPath);
        if (!folder.exists()) {
            throw new IllegalArgumentException("Folder does not exist: " + folderPath);
        }
        if (!folder.isDirectory()) {
            throw new IllegalArgumentException("Path is not a directory: " + folderPath);
        }

        try (Stream<Path> paths = Files.walk(Paths.get(folderPath))) {
            paths.filter(Files::isRegularFile)
                .forEach(this::moveFileToCorrectFolder);
        } catch (IOException e) {
            throw new RuntimeException("Error organizing folder structure", e);
        }
    }

    // 将文件移动到正确的文件夹中
    private void moveFileToCorrectFolder(Path filePath) {
        // 根据文件类型或其他规则确定目标文件夹路径
        String fileName = filePath.getFileName().toString();
        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
        String targetFolder = "./sorted/" + fileType; // 假设按照文件类型排序

        File targetDir = new File(targetFolder);
        if (!targetDir.exists() && !targetDir.mkdirs()) {
            throw new RuntimeException("Could not create directory: " + targetFolder);
        }

        File targetFile = new File(targetDir, fileName);
        try {
            Files.move(filePath, targetFile.toPath());
        } catch (IOException e) {
            throw new RuntimeException("Error moving file: " + fileName, e);
        }
    }

    // 主方法，用于测试
    public static void main(String[] args) {
        FolderStructureOrganizer organizer = new FolderStructureOrganizer();
        String folderPath = "./testFolder"; // 假设有一个名为 testFolder 的文件夹需要整理
        try {
            organizer.organizeFolderStructure(folderPath);
            System.out.println("Folder structure organized successfully.");
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}
