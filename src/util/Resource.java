package util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Resource {
    
    // 获取图像资源的静态方法
    public static BufferedImage getResouceImage(String path) {
        BufferedImage img = null; // 初始化图像对象
        try {
            img = ImageIO.read(new File(path)); // 从文件中读取图像
        } catch (IOException e) {
            e.printStackTrace(); // 输出异常信息
        }
        return img; // 返回图像对象
    }
    
}
