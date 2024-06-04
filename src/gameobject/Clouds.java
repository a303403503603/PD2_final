package gameobject;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import userinterface.GameWindow;
import util.Resource;

public class Clouds {
    private List<ImageCloud> listCloud; // 云的列表
    private BufferedImage cloud; // 云的图片

    private MainCharacter mainCharacter; // 主角对象

    // 构造函数
    public Clouds(int width, MainCharacter mainCharacter) {
        this.mainCharacter = mainCharacter;
        // 获取云的图片资源
        cloud = Resource.getResouceImage("data/cloud.png");
        listCloud = new ArrayList<ImageCloud>(); // 初始化云的列表

        // 初始化云的位置
        ImageCloud imageCloud = new ImageCloud();
        imageCloud.posX = 0;
        imageCloud.posY = 30;
        listCloud.add(imageCloud);

        imageCloud = new ImageCloud();
        imageCloud.posX = 150;
        imageCloud.posY = 40;
        listCloud.add(imageCloud);

        imageCloud = new ImageCloud();
        imageCloud.posX = 300;
        imageCloud.posY = 50;
        listCloud.add(imageCloud);

        imageCloud = new ImageCloud();
        imageCloud.posX = 450;
        imageCloud.posY = 20;
        listCloud.add(imageCloud);

        imageCloud = new ImageCloud();
        imageCloud.posX = 600;
        imageCloud.posY = 60;
        listCloud.add(imageCloud);
    }

    // 更新云的位置
    public void update() {
        Iterator<ImageCloud> itr = listCloud.iterator();
        ImageCloud firstElement = itr.next();
        firstElement.posX -= mainCharacter.getSpeedX() / 8;
        while (itr.hasNext()) {
            ImageCloud element = itr.next();
            element.posX -= mainCharacter.getSpeedX() / 8;
        }
        if (firstElement.posX < -cloud.getWidth()) {
            listCloud.remove(firstElement);
            firstElement.posX = GameWindow.SCREEN_WIDTH;
            listCloud.add(firstElement);
        }
    }

    // 绘制云
    public void draw(Graphics g) {
        for (ImageCloud imgLand : listCloud) {
            g.drawImage(cloud, (int) imgLand.posX, imgLand.posY, null);
        }
    }

    // 云的内部类
    private class ImageCloud {
        float posX; // x坐标
        int posY; // y坐标
    }
}
