// 代码生成时间: 2025-10-03 18:35:51
import io.micronaut.core.annotation.Introspected;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 图像滤镜引擎，用于应用不同的图像滤镜效果。
 * 这个类提供了一个基础的架构，可以根据需要扩展更多的滤镜功能。
 */
@Introspected
public class ImageFilterEngine {

    private BufferedImage image;

    /**
     * 构造函数，加载图像文件。
     * @param imagePath 图像文件路径
     * @throws IOException 如果图像文件无法加载。
     */
    public ImageFilterEngine(String imagePath) throws IOException {
        this.image = ImageIO.read(new File(imagePath));
        if (this.image == null) {
            throw new IOException("无法加载图像文件: " + imagePath);
        }
    }

    /**
     * 应用灰度滤镜效果。
     * @return 应用滤镜后的图像。
     */
    public BufferedImage applyGrayscaleFilter() {
        if (image == null) {
            throw new IllegalStateException("图像未加载，请先加载图像。");
        }

        BufferedImage grayscaleImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int rgb = image.getRGB(x, y);
                int r = (rgb >> 16) & 0xff;
                int g = (rgb >> 8) & 0xff;
                int b = rgb & 0xff;

                // 计算灰度值
                int gray = (int) (r * 0.3 + g * 0.59 + b * 0.11);
                grayscaleImage.setRGB(x, y, gray << 16 | gray << 8 | gray);
            }
        }
        return grayscaleImage;
    }

    /**
     * 应用滤镜效果，并保存到文件。
     * @param filteredImagePath 滤镜效果后的图像文件路径。
     * @param filterName 滤镜名称。
     * @throws IOException 如果图像无法保存。
     */
    public void applyFilterAndSave(String filteredImagePath, String filterName) throws IOException {
        BufferedImage filteredImage = applyGrayscaleFilter(); // 以灰度滤镜为例
        File outputFile = new File(filteredImagePath);
        ImageIO.write(filteredImage, "png", outputFile);
        if (!outputFile.exists()) {
            throw new IOException("滤镜效果图像保存失败: " + filteredImagePath);
        }
        System.out.println("应用了" + filterName + "滤镜的图像已保存到：" + filteredImagePath);
    }

    // 可以继续添加其他滤镜效果的方法

    public static void main(String[] args) {
        try {
            ImageFilterEngine engine = new ImageFilterEngine("path/to/your/image.jpg");
            engine.applyFilterAndSave("path/to/your/filtered_image.jpg", "Grayscale");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}