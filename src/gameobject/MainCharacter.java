package gameobject; // 套件聲明

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;

import util.Animation;
import util.Resource;

public class MainCharacter {

    // 主角落地的 Y 座標
    public static final int LAND_POSY = 80;
    
    // 不同狀態的常數
    private static final int NORMAL_RUN = 0;
    private static final int JUMPING = 1;
    private static final int DOWN_RUN = 2;
    private static final int DEATH = 3;
    public static final int MAX_BATTERY = 5;
    
    private float gravity;
    private int brightness;
    private int life;
    private int batteryLevel;
    // 位置與速度相關的變數
    private float posY;
    private float posX;
    private float speedX;
    private float speedY;
    private Rectangle rectBound;
    
    // 分數
    public int score = 0;
    public int maxScore = 0;
    
    // 現在的狀態
    private int state = NORMAL_RUN;
    
    // 不同動畫與圖片
    private Animation normalRunAnim;
    private BufferedImage jumping;
    private Animation downRunAnim;
    private BufferedImage deathImage;
    

    // 聲音效果
    private AudioClip jumpSound;
    private AudioClip deadSound;
    private AudioClip scoreUpSound;
    
    // 建構函式
    public MainCharacter() {
        gravity = 1f;
        brightness = 100;
        life = 1;
        batteryLevel = MAX_BATTERY;
        posX = 50;
        posY = LAND_POSY;
        rectBound = new Rectangle();
        normalRunAnim = new Animation(90);
        normalRunAnim.addFrame(Resource.getResouceImage("data/main-character1.png"));
        normalRunAnim.addFrame(Resource.getResouceImage("data/main-character2.png"));
        jumping = Resource.getResouceImage("data/main-character3.png");
        downRunAnim = new Animation(90);
        downRunAnim.addFrame(Resource.getResouceImage("data/main-character5.png"));
        downRunAnim.addFrame(Resource.getResouceImage("data/main-character6.png"));
        deathImage = Resource.getResouceImage("data/main-character4.png");
        
        try {
            // 載入音效
            jumpSound =  Applet.newAudioClip(new URL("file","","data/jump.wav"));
            deadSound =  Applet.newAudioClip(new URL("file","","data/dead.wav"));
            scoreUpSound =  Applet.newAudioClip(new URL("file","","data/scoreup.wav"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    // 取得 X 軸速度
    public float getSpeedX() {
        return speedX;
    }

    // 設置 X 軸速度
    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    public void addLife() {
        life++;
    }

    public int getLife() {
        return life;
    }


    public int getBrightness() {
        return brightness;
    }

    public void setGravity(float gravity) {
        this.gravity = gravity;
    }
    
    // 畫出主角
    public void draw(Graphics g) {
        switch(state) {
            case NORMAL_RUN:
                g.drawImage(normalRunAnim.getFrame(), (int) posX, (int) posY, null);
                break;
            case JUMPING:
                g.drawImage(jumping, (int) posX, (int) posY, null);
                break;
            case DOWN_RUN:
                g.drawImage(downRunAnim.getFrame(), (int) posX, (int) (posY + 20), null);
                break;
            case DEATH:
                g.drawImage(deathImage, (int) posX, (int) posY, null);
                break;
        }
//      // 繪製邊界 (用於測試碰撞)
        Rectangle bound = getBound();
        g.setColor(Color.RED);
        g.drawRect(bound.x, bound.y, bound.width, bound.height);

        drawBatteryStatus(g);
    }
    
    // 更新主角狀態
    public void update() {
        normalRunAnim.updateFrame();
        downRunAnim.updateFrame();
        if(posY >= LAND_POSY) {
            posY = LAND_POSY;
            if(state != DOWN_RUN) {
                state = NORMAL_RUN;
            }
        } else {
            speedY += gravity;
            posY += speedY;
        }
    }
    
    // 主角跳躍
    public void jump() {
        if(posY >= LAND_POSY) {
            if(jumpSound != null) {
                jumpSound.play();
            }
            speedY = -7.5f;
            posY += speedY;
            state = JUMPING;
        }
    }
    
    // 主角下蹲
    public void down(boolean isDown) {
        if(state == JUMPING) {
            return;
        }
        if(isDown) {
            state = DOWN_RUN;
        } else {
            state = NORMAL_RUN;
        }
    }
    
    // 取得主角的邊界
    public Rectangle getBound() {
        rectBound = new Rectangle();
        if(state == DOWN_RUN) {
            rectBound.x = (int) posX + 5;
            rectBound.y = (int) posY + 20;
            rectBound.width = downRunAnim.getFrame().getWidth() - 10;
            rectBound.height = downRunAnim.getFrame().getHeight();
        } else {
            rectBound.x = (int) posX + 5;
            rectBound.y = (int) posY;
            rectBound.width = normalRunAnim.getFrame().getWidth() - 10;
            rectBound.height = normalRunAnim.getFrame().getHeight();
        }
        return rectBound;
    }
    
    // 主角死亡
    public void dead(boolean isDeath) {
        if(isDeath) {
            state = DEATH;
        } else {
            state = NORMAL_RUN;
        }
    }
    
    // 重置主角位置
    public void reset() {
        posY = LAND_POSY;
    }
    
    // 播放死亡音效
    public void playDeadSound() {
        deadSound.play();
    }
    
    // 分數增加
    public void upScore() {
        score += 20;
        // 每增加 100 分播放一次音效
        if(score % 100 == 0) {
            scoreUpSound.play();
        }
    }
    
    //電池狀態
    public void setBrightness(int brightness) {
        this.brightness = brightness;
        updateBatteryLevel();
    }
    
    private void updateBatteryLevel() {
        batteryLevel = (brightness / 20) + 1;
        if (batteryLevel > MAX_BATTERY) {
            batteryLevel = MAX_BATTERY;
        }
        if (batteryLevel < 0) {
            batteryLevel = 0;
        }
    }

    private void drawBatteryStatus(Graphics g) {
        int batteryX = 20;
        int batteryY = 20;
        int batteryWidth = 10;
        int batteryHeight = 20;
        int gap = 5;
    
        for (int i = 0; i < MAX_BATTERY; i++) {
            if (i < batteryLevel) {
                g.setColor(Color.GREEN);
            } else {
                g.setColor(Color.GRAY);
            }
            g.fillRect(batteryX, batteryY + i * (batteryHeight + gap), batteryWidth, batteryHeight);
        }
    }

    //撿道具
    public void pickUpItem(String itemType) {
        if (itemType.equals("lowGravity")) {
            setGravity(0.5f); 
        } else if (itemType.equals("normalGravity")) {
            setGravity(1f); 
        }
        //其他道具效果
    }
    
}
