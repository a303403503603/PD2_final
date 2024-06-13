package gameobject.EnemyFile;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import gameobject.MainCharacter;
import util.Resource;

public class EnemiesManager {
    
    private BufferedImage chair; 
    private BufferedImage box;
    private BufferedImage bat1;
    //private BufferedImage bat2;
    private Random rand; 

    private List<Enemy> enemies; 
    private MainCharacter mainCharacter; 

    
    // 构造函数
    public EnemiesManager(MainCharacter mainCharacter) {
        rand = new Random(); 
        chair = Resource.getResouceImage("data/chair.png");
        box = Resource.getResouceImage("data/box.png");
        bat1 = Resource.getResouceImage("data/bat1.png");
        //bat2 = Resource.getResouceImage("data/bat2.png");
        enemies = new ArrayList<Enemy>(); 
        this.mainCharacter = mainCharacter;
        enemies.add(createEnemy());
    }
    
    // 更新敌人状态
    public void update() {
        for(Enemy e : enemies) {
            e.update();
        }
        
        if(enemies.size() == 0) {
            enemies.add(createEnemy());
        } else {
            if(enemies.get(enemies.size()-1).isAtX(400)) { //每次update都用list中最後一個enemy的位置來確認是否需要新增enemy
                enemies.add(createEnemy());
            }

            if(enemies.get(0).isOutOfScreen()) {
                enemies.remove(enemies.get(0));
            }
        }

        
    }
    
    // 绘制敌人
    public void draw(Graphics g) {
        for(Enemy e : enemies) {
            e.draw(g);
        }
    }
    
    int adjust = 0;
    // 创建敌人
    private Enemy createEnemy() {
        int type = rand.nextInt(5); 
        int posX = 650 + rand.nextInt(150);
        
        if(type == 4) {
            posX = (posX +300 -adjust) * (int)(mainCharacter.getSpeedX()/4);
            adjust = 100; // 蝙蝠移動速度更快，會拉近與前一個障礙物的距離、拉開與下一個的距離，所以蝙蝠出現位置要比不會移動的障礙物更遠，蝙蝠的下一個障礙物出現位置要往左挪
            return new EnemyInTheAir(mainCharacter, posX, bat1.getWidth() - 10, bat1.getHeight() - 10); //, bat1, bat2
        } else if(type/2 == 0){
            posX = (posX -adjust) * (int)(mainCharacter.getSpeedX()/4);
            adjust = 0;
            return new EnemyOnTheGround(mainCharacter, posX, box.getWidth() - 10, box.getHeight() - 10, box);
        } else {
            posX = (posX -adjust) * (int)(mainCharacter.getSpeedX()/4);
            adjust = 0;
            return new EnemyOnTheGround(mainCharacter, posX, chair.getWidth() - 10, chair.getHeight() - 10, chair);
        }
    }
    
    // 碰撞
    public boolean isCollision() {
        for(Enemy e : enemies) {
            if (mainCharacter.getBound().intersects(e.getBound()) && e.getHasCollision() == false) { // 如果主角与敌人相交
                e.setHasCollision(true);
                return true; // 返回碰撞true
            }
        }
        return false; // 没有碰撞，返回false
    }
    
    // 重置
    public void reset() {
        enemies.clear(); 
        if(enemies.size() == 0) {
            enemies.add(createEnemy()); 
        }
    }
}
