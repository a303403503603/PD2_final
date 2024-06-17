package gameobject.BackgroundFile;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import gameobject.MainCharacter;

public class Object {
    
    private int posX;
	private int posY;
	private int type;
    
    private BufferedImage image; 
    private MainCharacter mainCharacter;

    private boolean positionChecked = false;

    
    public Object(MainCharacter mainCharacter, int posX, int posY, BufferedImage image, int type) {
        this.posX = posX; // 设置X坐标
		this.posY = posY;
		this.image = image;
		this.type = type;
        this.mainCharacter = mainCharacter;
    }
    
    public void update() {
        posX -= mainCharacter.getSpeedX();
    }
    
    public void draw(Graphics g) {
        g.drawImage(image, posX, posY - image.getHeight(), null); 
    }

    public boolean isOutOfScreen() {
        if(posX < -image.getWidth()) {
            return true;
        }
        return false;
    }

	public int getType() {
		return type;
	}

    public int getX() {
		return posX;
	}

    public int getWidth() {
		return image.getWidth();
	}

    public int setX(int x) {
		return posX = x;
	}

    public boolean isAtX(int distanceFromLeftEnd) {
        if(posX < distanceFromLeftEnd && !positionChecked) {
            positionChecked = true;
            return true;
        } else {
            return false;
        }
    }
}


