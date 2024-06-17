package gameobject.BackgroundFile;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import gameobject.MainCharacter;
import util.Resource;

public class Land {
    
    public static final int LAND_POSY = 103; // 地面的Y座標
    
    private List<ImageLand> listLand; // 地面圖片列表
    private BufferedImage land1; // 地面圖像1
    private BufferedImage land2; // 地面圖像2
    private BufferedImage land3; // 地面圖像3
    
    private MainCharacter mainCharacter; // 主角對象
    
    // 建構函數
    public Land(int width, MainCharacter mainCharacter) {
        this.mainCharacter = mainCharacter; // 設置主角對象
        // 獲取地面圖像
        land1 = Resource.getResouceImage("data/land1.png");
        land2 = Resource.getResouceImage("data/land2.png");
        land3 = Resource.getResouceImage("data/land3.png");
        // 計算需要多少個地面圖像填充屏幕
        int numberOfImageLand = width / land1.getWidth() + 2;
        listLand = new ArrayList<ImageLand>(); // 初始化地面圖片列表
        // 創建地面圖片對象並添加到列表中
        for(int i = 0; i < numberOfImageLand; i++) {
            ImageLand imageLand = new ImageLand();
            imageLand.posX = i * land1.getWidth();
            setImageLand(imageLand);
            listLand.add(imageLand);
        }
    }
    
    // 更新land狀態
    public void update(){
        Iterator<ImageLand> itr = listLand.iterator();
        ImageLand firstElement = itr.next();
        firstElement.posX -= mainCharacter.getSpeedX();
        float previousPosX = firstElement.posX;
        while(itr.hasNext()) {
            ImageLand element = itr.next();
            element.posX = previousPosX + land1.getWidth();
            previousPosX = element.posX;
        }
        if(firstElement.posX < -land1.getWidth()) {
            listLand.remove(firstElement);
            firstElement.posX = previousPosX + land1.getWidth();
            setImageLand(firstElement);
            listLand.add(firstElement);
        }
    }
    
    // 設置land圖像
    private void setImageLand(ImageLand imgLand) {
        int typeLand = getTypeOfLand();
        if(typeLand == 1) {
            imgLand.image = land1;
        } else if(typeLand == 3) {
            imgLand.image = land3;
        } else {
            imgLand.image = land2;
        }
    }
    
    // 繪製地面
    public void draw(Graphics g) {
        for(ImageLand imgLand : listLand) {
            g.drawImage(imgLand.image, (int) imgLand.posX, LAND_POSY, null);
        }
    }
    
    // 獲取land類型
    private int getTypeOfLand() {
        Random rand = new Random();
        int type = rand.nextInt(10);
        if(type > 5) {
            return 1;
        } else if(type < 5) {
            return 2;
        } else {
            return 3;
        }
    }
    
    // 地面圖片的內部class
    private class ImageLand {
        float posX; // X座標
        BufferedImage image; // 圖像
    }
    
}
