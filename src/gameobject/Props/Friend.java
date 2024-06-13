package gameobject.Props;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Friend {
	private boolean hasCollision = false;
	public abstract int getType();
	public abstract void update();
	public abstract void draw(Graphics g);
	public abstract Rectangle getBound();
	public abstract boolean isOutOfScreen();
	
	public boolean getHasCollision() {
        return hasCollision;
    }
    
    public void setHasCollision(boolean hasCollision) {
        this.hasCollision = hasCollision;
    }
}

