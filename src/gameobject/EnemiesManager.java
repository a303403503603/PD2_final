package gameobject;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import util.Resource;

public class EnemiesManager {
    
    private BufferedImage cactus1; // 仙人掌1的图像
    private BufferedImage cactus2; // 仙人掌2的图像
    private Random rand; // 随机数生成器
    
    private List<Enemy> enemies; // 敌人列表
    private MainCharacter mainCharacter; // 主角对象
    
    // 构造函数
    public EnemiesManager(MainCharacter mainCharacter) {
        rand = new Random(); // 初始化随机数生成器
        // 获取仙人掌1和仙人掌2的图像资源
        cactus1 = Resource.getResouceImage("data/cactus1.png");
        cactus2 = Resource.getResouceImage("data/cactus2.png");
        enemies = new ArrayList<Enemy>(); // 初始化敌人列表
        this.mainCharacter = mainCharacter; // 设置主角对象
        enemies.add(createEnemy()); // 添加初始敌人
    }
    
    // 更新敌人状态
    public void update() {
        for(Enemy e : enemies) {
            e.update();
        }
        Enemy enemy = enemies.get(0);
        if(enemy.isOutOfScreen()) { // 如果敌人离开了屏幕
            mainCharacter.upScore(); // 主角得分增加
            enemies.clear(); // 清空敌人列表
            enemies.add(createEnemy()); // 添加新的敌人
        }
    }
    
    // 绘制敌人
    public void draw(Graphics g) {
        for(Enemy e : enemies) {
            e.draw(g);
        }
    }
    
    // 创建敌人
    private Enemy createEnemy() {
        int type = rand.nextInt(2); // 随机选择敌人类型
        if(type == 0) {
            return new Cactus(mainCharacter, 800, cactus1.getWidth() - 10, cactus1.getHeight() - 10, cactus1); // 创建仙人掌1
        } else {
            return new Cactus(mainCharacter, 800, cactus2.getWidth() - 10, cactus2.getHeight() - 10, cactus2); // 创建仙人掌2
        }
    }
    
    // 检测碰撞
    public boolean isCollision() {
        for(Enemy e : enemies) {
            if (mainCharacter.getBound().intersects(e.getBound())) { // 如果主角与敌人相交
                return true; // 返回碰撞为真
            }
        }
        return false; // 没有碰撞，返回假
    }
    
    // 重置敌人
    public void reset() {
        enemies.clear(); // 清空敌人列表
        enemies.add(createEnemy()); // 添加新的敌人
    }
    
}
