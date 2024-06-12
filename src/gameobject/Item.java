package gameobject;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Item extends Friend {

// public static final int Y_LAND = 125;
public static final int Y_LAND = 92;
public static final int COUNT_DOWN = 200;
//Try to make the item fall down from the top of the screen
//Another thing is that the item should be randomly positioned

private int posX;
private int width;
private int height;
private int type;
private static int countDown = COUNT_DOWN;
private static int ranPosY =  Y_LAND;
private Random rand = new Random();

private BufferedImage image;
private MainCharacter mainCharacter;
// private Random rand;

private Rectangle rectBound;

public Item(MainCharacter mainCharacter, int posX, int width, int height, BufferedImage image, int type) {
	this.mainCharacter = mainCharacter;
	this.posX = posX;
	this.width = width;
	this.height = height;
	this.image = image;
	this.type = type;
}

public void update() {
	posX -= mainCharacter.getSpeedX();
}

public void removeItem(){
	ranPosY = 500;
}

public void draw(Graphics g) {

	// System.out.println("ranPosY: " + ranPosY);
	//if(ranPosY != (Y_LAND - image.getHeight()))
	//System.out.println("randPosY: " + ranPosY);
	// g.drawImage(image, posX, Y_LAND - image.getHeight(), null
	g.drawImage(image, posX , Y_LAND - ranPosY, null);
	// g.drawImage(image, posX, Y_LAND - image.getHeight(), null);
	//g.setColor(Color.red);
	//Rectangle bound = getBound();
	//g.drawRect(bound.x, bound.y, bound.width, bound.height);
}

public int getType() {
	return type;
}
public Rectangle getBound() {
	rectBound = new Rectangle();
	rectBound.x = (int) posX + (image.getWidth() - width)/2;
	rectBound.y = Y_LAND - image.getHeight() + (image.getHeight() - height)/2;
	rectBound.width = width;
	rectBound.height = height;
	return rectBound;
}

@Override
public boolean isOutOfScreen() {
	if(posX < -image.getWidth()) {
		//ranPosY = rand.nextInt(Y_LAND - image.getHeight());
		ranPosY = rand.nextInt(100);
		if(ranPosY < 15) 
			ranPosY = 15;
		System.out.println("randPosY2: " + ranPosY);
		return true;
	}

	return false;
}

}