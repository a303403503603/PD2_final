package gameobject.EnemyFile;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Enemy {
	private boolean hasCollision = false;
	public static int ENEMY_GROUNDY = 125;

	public abstract void update();
	public abstract void draw(Graphics g);
	public abstract Rectangle getBound();
	public abstract boolean isOutOfScreen(); // 判斷障礙物是否已移動到畫面左側之外
	public abstract boolean isAtX(int distanceFromLeftEnd); // 若已自畫面右側，移動到距離左側一段特定距離的位置，就return true
	
	public boolean getHasCollision() {
        return hasCollision;
    }
    
    public void setHasCollision(boolean hasCollision) {
        this.hasCollision = hasCollision;
    }

}
