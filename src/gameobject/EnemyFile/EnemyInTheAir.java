package gameobject.EnemyFile;

import gameobject.MainCharacter;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import util.Animation;
import util.Resource;

public class EnemyInTheAir extends Enemy{

    public static final int Y_LAND = Enemy.ENEMY_GROUNDY; // 障礙物的地面y座標
    
    private int posX;
    private int width;
    private int height; 
    
    private BufferedImage image;
    private MainCharacter mainCharacter;
    
    private Rectangle rectBound; // 碰撞邊界
    private boolean positionChecked = false;

    private Animation flyingBatAnimation;

    public EnemyInTheAir(MainCharacter mainCharacter, int posX, int width, int height) {
        this.posX = posX;
        this.width = width;
        this.height = height;
        this.mainCharacter = mainCharacter;
        rectBound = new Rectangle();
        flyingBatAnimation = new Animation(100); // 飛行障礙物的動畫
        flyingBatAnimation.addFrame(Resource.getResouceImage("data/bat1.png")); // 圖片1
        flyingBatAnimation.addFrame(Resource.getResouceImage("data/bat2.png")); // 圖片2
    }
    
    @Override
    public void update() {
        flyingBatAnimation.updateFrame();
        posX -= mainCharacter.getSpeedX()*1.2; // 根據主角的速度SpeedX的1.2倍 更新x座標 (更快速向畫面左側移動)
    }
    
    @Override
    public void draw(Graphics g) {
        image = flyingBatAnimation.getFrame();
        g.drawImage(image, posX, Y_LAND - image.getHeight()-30, null); // y座標位置 = 地面y座標-圖片高度-30 (圖片下緣距離地面30 pixal)
    }


    // 获取碰撞边界
    @Override
    public Rectangle getBound() {
        rectBound = new Rectangle(); // 重新初始化碰撞邊界
        image = flyingBatAnimation.getFrame();
        rectBound.x = (int) posX + (image.getWidth() - width)/2; // 設定x座標(長方形左側位置)
        rectBound.y = Y_LAND - image.getHeight()-30 + (image.getHeight() - height)/2; // 設定y座標(長方形上方位置)
        rectBound.width = width; // 設定長方形右側邊界
        rectBound.height = height; // 設定高度
        return rectBound; // return設定好的長方形邊界範圍
    }

    @Override
    // 判斷障礙物是否已移動到畫面左側之外
    public boolean isOutOfScreen() {
        image = flyingBatAnimation.getFrame();
        return posX < -image.getWidth(); // x座標是否小於 0-圖片寬度
    }

    @Override
    public boolean isAtX(int distanceFromLeftEnd) { // 判斷障礙物的x座標是否已經經過distanceFromLeftEnd
        if(posX < distanceFromLeftEnd && !positionChecked) {
            positionChecked = true;
            return true;
        } else {
            return false;
        }
    }
    
}
