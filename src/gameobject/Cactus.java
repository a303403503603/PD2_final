package gameobject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Cactus extends Enemy {
    
    public static final int Y_LAND = 125; // 仙人掌所在的地面高度
    
    private int posX; // X坐标
    private int width; // 宽度
    private int height; // 高度
    
    private BufferedImage image; // 图像
    private MainCharacter mainCharacter; // 主角对象
    
    private Rectangle rectBound; // 碰撞边界
    
    // 构造函数
    public Cactus(MainCharacter mainCharacter, int posX, int width, int height, BufferedImage image) {
        this.posX = posX; // 设置X坐标
        this.width = width; // 设置宽度
        this.height = height; // 设置高度
        this.image = image; // 设置图像
        this.mainCharacter = mainCharacter; // 设置主角对象
        rectBound = new Rectangle(); // 初始化碰撞边界
    }
    
    // 更新仙人掌状态
    public void update() {
        posX -= mainCharacter.getSpeedX(); // 根据主角的速度更新X坐标
    }
    
    // 绘制仙人掌
    public void draw(Graphics g) {
        g.drawImage(image, posX, Y_LAND - image.getHeight(), null); // 绘制仙人掌图像
        g.setColor(Color.red); // 设置颜色为红色
        // 绘制碰撞边界（注释掉的代码）
//      Rectangle bound = getBound();
//      g.drawRect(bound.x, bound.y, bound.width, bound.height);
    }
    
    // 获取碰撞边界
    public Rectangle getBound() {
        rectBound = new Rectangle(); // 重新初始化碰撞边界
        rectBound.x = (int) posX + (image.getWidth() - width)/2; // 设置X坐标
        rectBound.y = Y_LAND - image.getHeight() + (image.getHeight() - height)/2; // 设置Y坐标
        rectBound.width = width; // 设置宽度
        rectBound.height = height; // 设置高度
        return rectBound; // 返回碰撞边界
    }

    @Override
    // 判断仙人掌是否移出屏幕外
    public boolean isOutOfScreen() {
        if(posX < -image.getWidth()) { // 如果X坐标小于图像宽度的负值
            return true; // 移出屏幕外
        }
        return false; // 否则未移出屏幕外
    }
    
}
