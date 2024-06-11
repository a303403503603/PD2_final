package gameobject;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Enemy {
	public abstract void update();
	public abstract void draw(Graphics g);
	public abstract Rectangle getBound();
	public abstract boolean isOutOfScreen();
	public abstract boolean isAtX(int distanceFromLeftEnd); // 若已自畫面右側，移動到距離左側一段特定距離的位置，就return true
}
