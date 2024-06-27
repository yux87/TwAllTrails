package tw.team1.member.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

public class ImageUtil {
    private static final int TARGET_WIDTH = 500;

    public static String encodeImageToBase64(File imageFile) throws IOException {
        BufferedImage originalImage = ImageIO.read(imageFile);

        // 計算新的高度以保持原始比例
        double originalWidth = originalImage.getWidth();
        double originalHeight = originalImage.getHeight();
        double ratio = originalHeight / originalWidth;
        int newHeight = (int) (TARGET_WIDTH * ratio);

        // 創建新的緩衝圖像並調整大小
        BufferedImage resizedImage = new BufferedImage(TARGET_WIDTH, newHeight, originalImage.getType());
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, TARGET_WIDTH, newHeight, null);
        g.dispose();

        // 將調整後的圖像轉換為 Base64 字串
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        String extension = getFileExtension(imageFile);
        ImageIO.write(resizedImage, extension, outputStream);
        byte[] imageBytes = outputStream.toByteArray();
        return Base64.getEncoder().encodeToString(imageBytes);
    }

    private static String getFileExtension(File file) {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return ""; // 空字串表示沒有擴展名
        }
        return name.substring(lastIndexOf + 1).toLowerCase();
    }
}