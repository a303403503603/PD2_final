package gameobject.EnemyFile;

//import java.awt.Color;
import gameobject.MainCharacter;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class EnemyOnTheGround extends Enemy {
    
    public static final int Y_LAND = Enemy.ENEMY_GROUNDY; // 障礙物的地面y座標
    
    private int posX; // X座標
    private int width; // 圖片寬度
    private int height; // 圖片高度
    
    private BufferedImage image; // 障礙物圖像
    private MainCharacter mainCharacter;
    
    private Rectangle rectBound; // 碰撞邊界
    private boolean positionChecked = false;

    
    public EnemyOnTheGround(MainCharacter mainCharacter, int posX, int width, int height, BufferedImage image) {
        this.posX = posX;
        this.width = width;
        this.height = height;
        this.image = image;
        this.mainCharacter = mainCharacter;
        rectBound = new Rectangle(); // 初始化碰撞邊界
    }
    
    // 更新障礙物位置
    @Override
    public void update() {
        posX -= mainCharacter.getSpeedX(); // 根據主角的速度SpeedX 更新x座標 (向畫面左側移動)
    }
    
    // 繪製障礙物
    @Override
    public void draw(Graphics g) {
        g.drawImage(image, posX, Y_LAND - image.getHeight(), null); // y座標位置 = 地面y座標-圖片高度
    }

    // 取得碰撞邊界
    @Override
    public Rectangle getBound() {
        rectBound = new Rectangle(); // 重新初始化碰撞邊界
        rectBound.x = (int) posX + (image.getWidth() - width)/2; // 設定x座標(長方形左側位置)
        rectBound.y = Y_LAND - image.getHeight() + (image.getHeight() - height)/2; // 設定y座標(長方形上方位置)
        rectBound.width = width; // 設定長方形寬度(右側邊界)
        rectBound.height = height; // 設定長方形高度(下方邊界)
        return rectBound; // return設定好的長方形邊界範圍
    }

    @Override
    // 判斷障礙物是否已移動到畫面左側之外
    public boolean isOutOfScreen() {
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
