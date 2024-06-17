package userinterface;

//import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
//import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import gameobject.MainCharacter;
//import gameobject.BackgroundFile.BackGround;
import gameobject.BackgroundFile.BackGroundManager;
import gameobject.EnemyFile.EnemiesManager;
import gameobject.Props.PropsManager;
import util.Resource;

public class GameScreen extends JPanel implements Runnable, KeyListener {

	private static final int START_GAME_STATE = 0;
	private static final int GAME_PLAYING_STATE = 1;
	private static final int GAME_OVER_STATE = 2;
	//private BackGround backGround;

	private MainCharacter mainCharacter;
	private BackGroundManager backGroundManager;
	private EnemiesManager enemiesManager;
	private PropsManager propsManager;//nora_0611+++
	
	private Thread thread;
	private boolean isKeyPressed;

	private int gameState;
	private long endProcessGame;

	private BufferedImage replayButtonImage;
	private BufferedImage gameOverButtonImage;

	public GameScreen() {
		mainCharacter = new MainCharacter();
		
		backGroundManager = new BackGroundManager(mainCharacter, GameWindow.SCREEN_WIDTH);
		enemiesManager = new EnemiesManager(mainCharacter);
		propsManager = new PropsManager(mainCharacter);

		replayButtonImage = Resource.getResouceImage("data/replay_button.png");
		gameOverButtonImage = Resource.getResouceImage("data/gameover_text.png");
		
		//backGround = new BackGround(0); //add background version_1;
		gameState = START_GAME_STATE;
	}

	public void startGame() {
		thread = new Thread(this);
		thread.start();
	}
	//更新遊戲內容
	public void gameUpdate() {
		if (gameState == GAME_PLAYING_STATE) {
			backGroundManager.update();
			mainCharacter.update();
			enemiesManager.update();
			propsManager.update();

			if (enemiesManager.isCollision()) {
				if(mainCharacter.getLife() <= 1) {	//生命條數量不足
					mainCharacter.playDeadSound();	   //播放死亡音效
					gameState = GAME_OVER_STATE;	  //轉換遊戲模式
					mainCharacter.dead(true);
				}
				else {
					mainCharacter.subLife();
					
				}
			}
			
			int propType = propsManager.isCollision();
			if (propType != 0) {
				pickUpItem(propType);
			}

		}
	}

	private void pickUpItem(int itemType) {
        if (itemType == 1) {
            mainCharacter.addLife();
        } 
        else if (itemType == 2) {
            mainCharacter.setBrightness();
        } 
        else if (itemType == 3) {
			mainCharacter.changeGravity();
		}
    }

	public void paint(Graphics g) {
		super.paintComponent(g);
		backGroundManager.draw(g);
		enemiesManager.draw(g);
		propsManager.draw(g);//nora_0611+++
		mainCharacter.draw(g);
		//畫面做明暗度的調整
		if (gameState == GAME_PLAYING_STATE) {
			g.setColor(new Color(0, 0, 0, mainCharacter.getBrightness()));
			g.fillRect(0, 0, getWidth(), getHeight());
		}
		
		if (gameState == GAME_OVER_STATE) {
			g.drawImage(gameOverButtonImage, 200, 30, null);
			g.drawImage(replayButtonImage, 283, 50, null);
		}
	}

	@Override
	public void run() {

		int fps = 60;
		long msPerFrame = 1000 * 1000000 / fps;
		long lastTime = System.nanoTime(); 
		long elapsed;
		
		int msSleep;
		int nanoSleep;

		while (true) {
			gameUpdate();
			repaint();
			endProcessGame = System.nanoTime();
			elapsed = (lastTime + msPerFrame - System.nanoTime());
			msSleep = (int) (elapsed / 1000000);
			nanoSleep = (int) (elapsed % 1000000);
			if (msSleep <= 0) {
				lastTime = System.nanoTime();
				continue;
			}
			try {
				Thread.sleep(msSleep, nanoSleep);  //設定更新頻率
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			lastTime = System.nanoTime();
		}
	}

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

	}

	private void resetGame() {
		backGroundManager.reset();
		enemiesManager.reset();
		propsManager.reset();//nora_0611+++
		mainCharacter.dead(false);
		mainCharacter.reset();
	}

}
