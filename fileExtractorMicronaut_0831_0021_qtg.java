// 代码生成时间: 2025-08-31 00:21:32
package com.example.demo;

import io.micronaut.core.annotation.Introspected;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Introspected
public class FileExtractor {

    /**
     * 解压ZIP文件
     *
     * @param zipFilePath ZIP文件的路径
     * @param destDirectory 解压后文件存放的目录
     * @throws IOException 如果解压过程中发生I/O异常
     */
    public void unzip(String zipFilePath, String destDirectory) throws IOException {
        Path destDir = Paths.get(destDirectory);
        if (!Files.exists(destDir)) {
            Files.createDirectories(destDir);
        }

        try (ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry entry = zipIn.getNextEntry();
            // 遍历ZIP内的条目
            while (entry != null) {
                String filePath = destDir + File.separator + entry.getName();
                if (!entry.isDirectory()) {
                    // 如果是文件，解压文件内容
                    extractFile(zipIn, filePath);
                } else {
                    // 如果是目录，创建目录
                    Files.createDirectories(Paths.get(filePath));
                }
                zipIn.closeEntry();
                entry = zipIn.getNextEntry();
            }
        } catch (IOException e) {
            throw new IOException("Failed to unzip the file: " + zipFilePath, e);
        }
    }

    /**
     * 从ZIP输入流中提取文件
     *
     * @param zipIn ZIP输入流
     * @param filePath 要解压的文件路径
     * @throws IOException 如果文件写入过程中发生I/O异常
     */
    private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        try (BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(Paths.get(filePath)))) {
            byte[] bytesIn = new byte[4096];
            int read = 0;
            while ((read = zipIn.read(bytesIn)) != -1) {
                bos.write(bytesIn, 0, read);
            }
        } catch (IOException e) {
            throw new IOException("Failed to extract file: " + filePath, e);
        }
    }

    // 你可以添加更多的方法来支持其他压缩格式，如rar, tar.gz等
}
/*
 * 示例用法：
 * FileExtractor extractor = new FileExtractor();
 * extractor.unzip("path/to/your/file.zip", "path/to/destination/directory");
 */