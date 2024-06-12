package gameobject;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import util.Resource;

public class FriendsManager {
	
	private BufferedImage friendRegen;
	private BufferedImage friendLight;
	private BufferedImage friendGravity;	
	private Random rand;
	
	private List<Friend> friends;
	private MainCharacter mainCharacter;
	
	public FriendsManager(MainCharacter mainCharacter) {
		rand = new Random();
		friendRegen = Resource.getResouceImage("data/flower1.png");
		friendLight = Resource.getResouceImage("data/flower2.png");
		friendGravity = Resource.getResouceImage("data/flower3.png");
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
		// if (friendType = getRandom)
		int type = rand.nextInt(3);
		switch (type){
			case 0:
				return new Item(mainCharacter, 300, friendRegen.getWidth() - 10, friendRegen.getHeight() - 10, friendRegen, type);
			case 1:
				return new Item(mainCharacter, 300, friendLight.getWidth() - 10, friendLight.getHeight() - 10, friendLight, type);
			default:	
				return new Item(mainCharacter, 300, friendGravity.getWidth() - 10, friendGravity.getHeight() - 10, friendGravity, type);
		}
	}
	
	public boolean isCollision(int[] which) {
		for(Friend e : friends) {
			if (mainCharacter.getBound().intersects(e.getBound())) {
				which[0] = e.getType();
				return true;
			}
		}
		return false;
	}
	
	public void reset() {
		friends.clear();
		friends.add(createFriend());
	}
	
}


