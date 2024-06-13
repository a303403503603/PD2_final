package gameobject.Enemy;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import gameobject.MainCharacter;
import util.Animation;
import util.Resource;

public class EnemyInTheAir extends Enemy{

    public static final int Y_LAND = 125;
    
    private int posX;
    private int width;
    private int height; 
    
    private BufferedImage image;
    private MainCharacter mainCharacter;
    
    private Rectangle rectBound; // 碰撞边界
    private boolean positionChecked = false;

    private Animation flyingBatAnimation;

    public EnemyInTheAir(MainCharacter mainCharacter, int posX, int width, int height) {
        this.posX = posX; // 设置X坐标
        this.width = width; // 设置宽度
        this.height = height; // 设置高度
        this.mainCharacter = mainCharacter; // 设置主角对象
        rectBound = new Rectangle(); // 初始化碰撞边界
        flyingBatAnimation = new Animation(100);
        flyingBatAnimation.addFrame(Resource.getResouceImage("data/bat1.png"));
        flyingBatAnimation.addFrame(Resource.getResouceImage("data/bat2.png"));
    }
    
    @Override
    public void update() {
        flyingBatAnimation.updateFrame();
        posX -= mainCharacter.getSpeedX()*1.3; // 根据主角的速度更新X坐标
    }
    
    public void draw(Graphics g) {
        image = flyingBatAnimation.getFrame();
        g.drawImage(image, posX, Y_LAND - image.getHeight()-30, null); // 绘制图像 (圖片下緣距離地面30 pixal)
    }
    
    // 获取碰撞边界
    public Rectangle getBound() {
        rectBound = new Rectangle(); // 重新初始化碰撞边界
        image = flyingBatAnimation.getFrame();
        rectBound.x = (int) posX + (image.getWidth() - width)/2; // 设置X坐标
        rectBound.y = Y_LAND - image.getHeight()-30 + (image.getHeight() - height)/2; // 设置Y坐标
        rectBound.width = width; // 设置宽度
        rectBound.height = height; // 设置高度
        return rectBound; // 返回碰撞边界
    }

    @Override
    // 判断障礙物是否移出屏幕外
    public boolean isOutOfScreen() {
        image = flyingBatAnimation.getFrame();
        if(posX < -image.getWidth()) { // 如果X坐标小于图像宽度的负值
            return true; // 移出屏幕外
        }
        return false; // 否则未移出屏幕外
    }

    public boolean isAtX(int distanceFromLeftEnd) {
        if(posX < distanceFromLeftEnd && !positionChecked) {
            positionChecked = true;
            return true;
        } else {
            return false;
        }
    }
    
}
