package userinterface;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import gameobject.MainCharacter;
import gameobject.Background.BackGround;
import gameobject.Background.BackGroundManager;
import gameobject.Enemy.EnemiesManager;
import gameobject.Props.PropsManager;
import util.Resource;

public class GameScreen extends JPanel implements Runnable, KeyListener {

	private static final int START_GAME_STATE = 0;
	private static final int GAME_PLAYING_STATE = 1;
	private static final int GAME_OVER_STATE = 2;
	private BackGround backGround;

	private MainCharacter mainCharacter;
	private BackGroundManager backGroundManager;
	private EnemiesManager enemiesManager;
	private PropsManager propsManager;//nora_0611+++
	
	private Thread thread;
	private boolean isKeyPressed;

	private int gameState;

	private BufferedImage replayButtonImage;
	private BufferedImage gameOverButtonImage;

	private float alpha;    //add background version_1;
	private float deltaAlpha;    //add background version_1;

	public GameScreen() {
		mainCharacter = new MainCharacter();
		mainCharacter.setSpeedX(4);
		
		backGroundManager = new BackGroundManager(mainCharacter, GameWindow.SCREEN_WIDTH);
		enemiesManager = new EnemiesManager(mainCharacter);
		propsManager = new PropsManager(mainCharacter);

		replayButtonImage = Resource.getResouceImage("data/replay_button.png");
		gameOverButtonImage = Resource.getResouceImage("data/gameover_text.png");
		
		backGround = new BackGround(0); //add background version_1;
		gameState = START_GAME_STATE;
	}

	public void startGame() {
		thread = new Thread(this);
		thread.start();
	}

	public void gameUpdate() {
		if (gameState == GAME_PLAYING_STATE) {
			backGroundManager.update();
			mainCharacter.update();
			enemiesManager.update();
			propsManager.update();

			if (enemiesManager.isCollision()) {
				mainCharacter.playDeadSound();
				if(mainCharacter.getLife() <= 1) {
					gameState = GAME_OVER_STATE;
					mainCharacter.dead(true);
				}
				else {
					mainCharacter.subLife();
				}
			}
			
			int propType = propsManager.isCollision();
			if (propType != 0) {
				System.out.println("type: " + propType);
				mainCharacter.setBonus(propType);
				mainCharacter.playFlowerSound();
			}
		}
	}

	public void paint(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.decode("#f7f7f7"));
		g.fillRect(0, 0, getWidth(), getHeight());

		switch (gameState) {
		case START_GAME_STATE:
			mainCharacter.draw(g);
			break;
		case GAME_PLAYING_STATE:			
		case GAME_OVER_STATE:
			
			backGroundManager.draw(g);
			enemiesManager.draw(g);
			propsManager.draw(g);//nora_0611+++
			mainCharacter.draw(g);

			g.setColor(Color.BLACK);
			g.drawString("HI " + mainCharacter.score, 500, 20);
			if (gameState == GAME_OVER_STATE) {
				g.drawImage(gameOverButtonImage, 200, 30, null);
				g.drawImage(replayButtonImage, 283, 50, null);
				
			}
			break;
		}
		if(gameState == 1) {
			backGround.draw(g);     //add background version_1;
		}
		
		
		
	}

	@Override
	public void run() {

		int fps = 100;
		long msPerFrame = 1000 * 1000000 / fps;
		long lastTime = 0;
		long elapsed;
		
		int msSleep;
		int nanoSleep;

		long endProcessGame;
		long lag = 0;

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
				Thread.sleep(msSleep, nanoSleep);
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
		enemiesManager.reset();
		propsManager.reset();//nora_0611+++
		mainCharacter.dead(false);
		mainCharacter.reset();
	}

}