// 代码生成时间: 2025-08-29 15:00:43
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.env.Environment;
import javax.inject.Singleton;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

// 定义一个用于批量调整图片尺寸的服务
@Singleton
public class ImageResizerService {

    // 调整单个图片的尺寸
    public BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        return new BufferedImage(targetWidth, targetHeight, originalImage.getType());
    }

    // 批量调整文件夹内所有图片的尺寸
    public void resizeImagesInFolder(String folderPath, int targetWidth, int targetHeight) throws IOException {
        File folder = new File(folderPath);
        if (!folder.exists() || !folder.isDirectory()) {
            throw new IllegalArgumentException("The provided path is not a valid directory: " + folderPath);
        }

        File[] files = folder.listFiles((dir, name) -> name.endsWith(".jpg") || name.endsWith(".png"));
        if (files == null) {
            throw new IOException("There was an issue accessing the directory: " + folderPath);
        }

        for (File file : files) {
            try {
                BufferedImage originalImage = ImageIO.read(file);
                BufferedImage resizedImage = resizeImage(originalImage, targetWidth, targetHeight);
                String fileName = file.getName();
                int lastDotIndex = fileName.lastIndexOf('.');
                String newFileName = file.getAbsolutePath().substring(0, lastDotIndex) + "_resized" + fileName.substring(lastDotIndex);
                File newFile = new File(newFileName);
                ImageIO.write(resizedImage, "png", newFile);
                System.out.println("Image resized and saved as: " + newFileName);
            } catch (IOException e) {
                System.err.println("Error occurred while resizing image: " + file.getName());
            }
        }
    }
}

// 程序的主入口点
public class ImageResizerApp {

    public static void main(String[] args) {
        // 初始化Micronaut应用上下文
        ApplicationContext context = ApplicationContext.builder(Environment.CLI).build();

        // 获取图片尺寸批量调整器服务
        ImageResizerService imageResizerService = context.getBean(ImageResizerService.class);

        // 设置目标尺寸
        int targetWidth = 800; // 例如800像素宽
        int targetHeight = 600; // 例如600像素高

        // 调用服务调整指定文件夹内所有图片尺寸
        try {
            imageResizerService.resizeImagesInFolder("path/to/your/image/folder", targetWidth, targetHeight);
        } catch (IOException e) {
            System.err.println("Error occurred while resizing images: " + e.getMessage());
        }
    }
}