package userinterface; // 套件聲明

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import gameobject.Clouds;
import gameobject.EnemiesManager;
import gameobject.Land;
import gameobject.MainCharacter;
import util.Resource;

public class GameScreen extends JPanel implements Runnable, KeyListener {

    // 遊戲狀態常數
    private static final int START_GAME_STATE = 0;
    private static final int GAME_PLAYING_STATE = 1;
    private static final int GAME_OVER_STATE = 2;
    
    // 遊戲物件
    private Land land;
    private MainCharacter mainCharacter;
    private EnemiesManager enemiesManager;
    private Clouds clouds;
    private Thread thread;

    private boolean isKeyPressed;

    private int gameState = START_GAME_STATE;

    private BufferedImage replayButtonImage;
    private BufferedImage gameOverButtonImage;

    // 建構函式
    public GameScreen() {
        // 初始化遊戲物件
        mainCharacter = new MainCharacter();
        land = new Land(GameWindow.SCREEN_WIDTH, mainCharacter);
        mainCharacter.setSpeedX(4);
        replayButtonImage = Resource.getResouceImage("data/replay_button.png");
        gameOverButtonImage = Resource.getResouceImage("data/gameover_text.png");
        enemiesManager = new EnemiesManager(mainCharacter);
        clouds = new Clouds(GameWindow.SCREEN_WIDTH, mainCharacter);
    }

    // 開始遊戲
    public void startGame() {
        thread = new Thread(this);
        thread.start();
    }

    // 遊戲更新
    public void gameUpdate() {
        if (gameState == GAME_PLAYING_STATE) {
            clouds.update();
            land.update();
            mainCharacter.update();
            enemiesManager.update();
            if (enemiesManager.isCollision()) {
                mainCharacter.playDeadSound();
                gameState = GAME_OVER_STATE;
                mainCharacter.dead(true);
            }
        }
    }

    // 繪製遊戲畫面
    public void paint(Graphics g) {
        g.setColor(Color.decode("#f7f7f7"));
        g.fillRect(0, 0, getWidth(), getHeight());

        switch (gameState) {
        case START_GAME_STATE:
            mainCharacter.draw(g);
            break;
        case GAME_PLAYING_STATE:
        case GAME_OVER_STATE:
            clouds.draw(g);
            land.draw(g);
            enemiesManager.draw(g);
            mainCharacter.draw(g);
            g.setColor(Color.BLACK);
            g.drawString("HI " + mainCharacter.score, 500, 20);
            if (gameState == GAME_OVER_STATE) {
                g.drawImage(gameOverButtonImage, 200, 30, null);
                g.drawImage(replayButtonImage, 283, 50, null);
            }
            break;
        }
    }

    // 遊戲執行緒
    @Override
    public void run() {
        // 遊戲主迴圈
        while (true) {
            gameUpdate();
            repaint();
            // 暫停一段時間，控制遊戲更新頻率
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // 處理按鍵按下事件
    @Override
    public void keyPressed(KeyEvent e) {
        if (!isKeyPressed) {
            isKeyPressed = true;
            switch (gameState) {
            case START_GAME_STATE:
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    gameState = GAME_PLAYING_STATE;
                }
                break;
            case GAME_PLAYING_STATE:
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    mainCharacter.jump();
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    mainCharacter.down(true);
                }
                break;
            case GAME_OVER_STATE:
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    gameState = GAME_PLAYING_STATE;
                    resetGame();
                }
                break;

            }
        }
    }

    // 處理按鍵釋放事件
    @Override
    public void keyReleased(KeyEvent e) {
        isKeyPressed = false;
        if (gameState == GAME_PLAYING_STATE) {
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                mainCharacter.down(false);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    // 重置遊戲
    private void resetGame() {
        enemiesManager.reset();
        mainCharacter.dead(false);
        mainCharacter.reset();
    }

}
