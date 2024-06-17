package gameobject.Props;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import gameobject.MainCharacter;
import util.Resource;

public class PropsManager {
	
	private BufferedImage friendRegen;
	private BufferedImage friendLight;
	private BufferedImage friendGravity;	
	private Random rand;
	private AudioClip flowerSound;
	
	private List<Friend> friends;
	private MainCharacter mainCharacter;
	
	public PropsManager(MainCharacter mainCharacter) {
		rand = new Random();
		try {
			flowerSound =  Applet.newAudioClip(new URL("file","","data/flower.wav"));
			friendRegen = Resource.getResouceImage("data/flower1.png");
			friendLight = Resource.getResouceImage("data/flower2.png");
			friendGravity = Resource.getResouceImage("data/flower3.png");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		friends = new ArrayList<Friend>();
		this.mainCharacter = mainCharacter;
		friends.add(createFriend());
	}
	
	public void update() {
		for(Friend e : friends) {
			e.update();
		}
		Friend friend = friends.get(0);
		if(friend.isOutOfScreen()) {
			friends.clear();
			friends.add(createFriend());
		}
	}
	
	public void draw(Graphics g) {
		for(Friend e : friends) {
			e.draw(g);
		}
	}
	
	public Friend createFriend() {
		int batteryLevel = mainCharacter.getBatteryLevel();
		int life = mainCharacter.getLife();
		int type = rand.nextInt(100);
		int regenRatio = (life <= 1)? 45 : 35;
		int lightRatio = (batteryLevel > 10) ? 55 : 65;

		if(type < regenRatio) {
			return new Flower(mainCharacter, 650, friendRegen.getWidth() - 10, friendRegen.getHeight() - 10, friendRegen, 1);
		}
		else if(type > lightRatio){
			return new Flower(mainCharacter, 650, friendLight.getWidth() - 10, friendLight.getHeight() - 10, friendLight, 2);
		}
		else{	
			return new Flower(mainCharacter, 650, friendGravity.getWidth() - 10, friendGravity.getHeight() - 10, friendGravity, 3);
		}
	}
	
	public int isCollision() {
		for(Friend e : friends) {
			if (mainCharacter.getBound().intersects(e.getBound()) && e.getHasCollision() == false) {
				e.setHasCollision(true);
				flowerSound.play();
				e.removeItem();
				return e.getType();
			}
		}
		return 0;
	}
	
	public void reset() {
		friends.clear();
		friends.add(createFriend());
	}
	
}


