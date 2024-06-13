package gameobject.Props;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import gameobject.MainCharacter;
import util.Resource;

public class PropsManager {
	
	private BufferedImage friendRegen;
	private BufferedImage friendLight;
	private BufferedImage friendSuperStar;	
	private Random rand;
	
	private List<Friend> friends;
	private MainCharacter mainCharacter;
	
	public PropsManager(MainCharacter mainCharacter) {
		rand = new Random();
		friendRegen = Resource.getResouceImage("data/flower1.png");
		friendLight = Resource.getResouceImage("data/flower2.png");
		friendSuperStar = Resource.getResouceImage("data/flower3.png");
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
			mainCharacter.upScore();
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
		int type = rand.nextInt(3);
		switch (type){
			case 0:
				return new Flower(mainCharacter, 300, friendRegen.getWidth() - 10, friendRegen.getHeight() - 10, friendRegen, type);
			case 1:
				return new Flower(mainCharacter, 300, friendLight.getWidth() - 10, friendLight.getHeight() - 10, friendLight, type);
			default:	
				return new Flower(mainCharacter, 300, friendSuperStar.getWidth() - 10, friendSuperStar.getHeight() - 10, friendSuperStar, type);
		}
	}
	
	public int isCollision() {
		for(Friend e : friends) {
			if (mainCharacter.getBound().intersects(e.getBound())) {
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


