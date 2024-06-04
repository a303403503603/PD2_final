package util;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Animation {

    private List<BufferedImage> list; // 图像列表
    private long deltaTime; // 每帧之间的时间间隔
    private int currentFrame = 0; // 当前帧索引
    private long previousTime; // 上一帧的时间戳

    // 构造函数
    public Animation(int deltaTime) {
        this.deltaTime = deltaTime; // 设置时间间隔
        list = new ArrayList<BufferedImage>(); // 初始化图像列表
        previousTime = 0; // 初始化上一帧时间戳
    }

    // 更新当前帧
    public void updateFrame() {
        if (System.currentTimeMillis() - previousTime >= deltaTime) { // 如果经过了足够的时间
            currentFrame++; // 切换到下一帧
            if (currentFrame >= list.size()) { // 如果已经达到列表末尾
                currentFrame = 0; // 回到列表开头
            }
            previousTime = System.currentTimeMillis(); // 更新上一帧时间戳
        }
    }

    // 添加帧到动画
    public void addFrame(BufferedImage image) {
        list.add(image); // 将图像添加到列表
    }

    // 获取当前帧的图像
    public BufferedImage getFrame() {
        return list.get(currentFrame); // 返回当前帧的图像
    }

}
