package gameobject;

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
    public static final int MAX_BATTERY = 1;
    public static final int MIN_BATTERY = 15;


    private static final int SCORE_INCREMENT = 10;
    private static final int TIME_INTERVAL = 1000; // 每秒增加分數
    private static final int BRIGHTNESS_INCREMENT = 0;
    private static final int BRIGHTNESS_DECREMENT = 15; // 每次遞減的亮度
    private static final int DECREMENT_INTERVAL = 1000; // 每 15 秒
    private static final int MAX_GRAVITY_TIME = 5000;
    private static final int MAX_LIFE = 3;

    //private boolean isInvincible;
    //private long invincibleStartTime;
    private float gravity;
    private String gravityMode;
    private int brightness;
    private int life;
    private int batteryLevel;
    
    private long startTime;
    private long gravityTime;
    private boolean gravityStatus;
    private long lastBrightnessDecrementTime;

    // 位置與速度相關的變數
    private float posY;
    private float posX;
    private float speedX;
    private float speedY;
    private Rectangle rectBound;

    // 分數
    public int score;
    public int maxScore;

    // 現在的狀態
    private int state;

    // 不同動畫與圖片
    private Animation normalRunAnim;
    private BufferedImage jumping;
    private Animation downRunAnim;
    private BufferedImage deathImage;
    private BufferedImage heartImage; // 愛心圖示

    // 聲音效果
    private AudioClip jumpSound;
    private AudioClip deadSound;
    private AudioClip scoreUpSound;
   

    // 建構函式
    public MainCharacter() {
        //isInvincible = false;
        gravity = 0.4f;
        brightness = BRIGHTNESS_INCREMENT;
        life = MAX_LIFE;
        batteryLevel = MAX_BATTERY;
        gravityStatus = false;
        gravityMode = "Earth";
        setSpeedX(5);

        startTime = System.currentTimeMillis();
        lastBrightnessDecrementTime = startTime;

        posX = 50;
        posY = LAND_POSY;
        
        score = 0;
        maxScore = 0;
        state = NORMAL_RUN;

        rectBound = new Rectangle();
        normalRunAnim = new Animation(60);
        downRunAnim = new Animation(60);
        
        try {
            normalRunAnim.addFrame(Resource.getResouceImage("data/main-character1.png"));
            normalRunAnim.addFrame(Resource.getResouceImage("data/main-character2.png"));
            jumping = Resource.getResouceImage("data/main-character3.png");
            
            downRunAnim.addFrame(Resource.getResouceImage("data/main-character5.png"));
            downRunAnim.addFrame(Resource.getResouceImage("data/main-character6.png"));
            
            deathImage = Resource.getResouceImage("data/main-character4.png");
            heartImage = Resource.getResouceImage("data/heart.png");

            jumpSound =  Applet.newAudioClip(new URL("file","","data/jump.wav"));
            deadSound =  Applet.newAudioClip(new URL("file","","data/dead.wav"));
            scoreUpSound =  Applet.newAudioClip(new URL("file","","data/scoreup.wav"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    public float getSpeedX() {
        return speedX;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    public void addSpeedX() {
        speedX += 1;
    }

    //生命值部分
    public void addLife() {
        if(life <= 3) {
            life++;
        }
    }

    public int getLife() {
        return life;
    }

    public void subLife() {
        if(life > 0) {
            life--;
        }
    }


    //亮度部分
    public void setBrightness() {
        brightness = 0;
        updateBatteryLevel();
    }

    public int getBrightness() {
        return brightness;
    }

    public int getBatteryLevel() {
        return batteryLevel;
    }

    public void increaseBrightness(int increment) { //暫時用不到
        brightness += increment;
        if (brightness > 255) {
            brightness = 255; // 確保亮度不超過最大值
        }
        updateBatteryLevel();
    }

    public void decreaseBrightness(int decrement) {
        brightness -= decrement;
        if (brightness < 0) {
            brightness = 0; // 確保亮度不低於 0
        }
        updateBatteryLevel();
    }

    private void updateBatteryLevel() {
        batteryLevel = (brightness / 15);
        if (batteryLevel > MIN_BATTERY) {
            batteryLevel = MIN_BATTERY;
        }
        if (batteryLevel < 0) {
            batteryLevel = 0;
        }
    }


    //重力部分
    public void changeGravity() {
        gravityTime = System.currentTimeMillis();
        setGravity(0.225f);
        gravityStatus = true;
    }

    private void setGravity(float gravity) {
        this.gravity = gravity;
        if(gravity == 0.225f) {
            gravityMode = "Moon";
        }
        else {
            gravityMode = "Earth";
        }
    }


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

        // 繪製邊界 (用於測試碰撞)
        //Rectangle bound = getBound();
        //g.setColor(Color.RED);
        //g.drawRect(bound.x, bound.y, bound.width, bound.height);

        drawBatteryStatus(g);
        drawLifeStatus(g);
        drawScore(g);
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

        long currentTime = System.currentTimeMillis();

        // 計算時間並增加分數
        if (currentTime - startTime >= TIME_INTERVAL) {
            score += SCORE_INCREMENT;
            if(score != 0 && score % 200 == 0) {
                scoreUpSound.play();
                addSpeedX();
            }
            startTime = currentTime; // 重置計時器
        }

        

        // 亮度每 15 秒遞減一次
        if (currentTime - lastBrightnessDecrementTime >= DECREMENT_INTERVAL) {
            increaseBrightness(BRIGHTNESS_DECREMENT);
            lastBrightnessDecrementTime = currentTime; // 重置亮度遞減計時器
        }

        if ((currentTime - gravityTime) > MAX_GRAVITY_TIME && gravityStatus == true) {
            setGravity(0.4f);
            gravityTime = currentTime;
            gravityStatus = false;
        }
    }

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

    
    public void down(boolean isDown) {
        if(state == JUMPING) {
            speedY = (float)(gravity * 10);
            posY += speedY;
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
            if(score > maxScore) {
                maxScore = score;
            }
            state = DEATH;
        } else {
            state = NORMAL_RUN;
        }
    }

    // 重置主角位置
    public void reset() {
        posY = LAND_POSY;

        gravity = 0.4f;
        setSpeedX(5);
        brightness = BRIGHTNESS_INCREMENT;
        life = MAX_LIFE;
        batteryLevel = MAX_BATTERY;
        gravityStatus = false;
        gravityMode = "Earth";
        score = 0;

        startTime = System.currentTimeMillis();
        lastBrightnessDecrementTime = startTime;
    }

    // 播放死亡音效
    public void playDeadSound() {
        deadSound.play();
    }


    private void drawBatteryStatus(Graphics g) {
        int batteryX = 170;
        int batteryY = 0;
        int batteryWidth = 25;
        int batteryHeight = 10;
        int gap = 5;
        
        for (int i = 0; i < MIN_BATTERY; i++) {
            
            if ((15 - batteryLevel) < i) {
                g.setColor(Color.GRAY);
            } else {
                g.setColor(Color.GREEN);
            }
            g.fillRect(batteryX  + i * (batteryHeight + gap) , batteryY, batteryWidth, batteryHeight);
        }
    }

    private void drawLifeStatus(Graphics g) {
        int heartX = 500;
        int heartY = 10;
        int gap = 5;
        
        for (int i = 0; i < life; i++) {
            g.drawImage(heartImage, heartX + i * (heartImage.getWidth() + gap), heartY, null);
        }
    }

    private void drawScore(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawString("Max score: " + maxScore, 10, 20);
        g.drawString("score: " + score, 10, 30);
        g.drawString("gravity: " + gravityMode, 10, 40);
    }
}
