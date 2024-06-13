package gameobject.BackgroundFile;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import gameobject.MainCharacter;
import util.Resource;

public class BackGroundManager {
	public static final int LAND_POSY = 103;

	
	private BufferedImage light1;
	private BufferedImage light2;
	private BufferedImage painting1;
	private BufferedImage painting2;	
	private BufferedImage painting3;	
	private BufferedImage land1;
	private BufferedImage land2;

	private List<BufferedImage> lightImageList;
	private List<BufferedImage> paintingImageList;
	private List<BufferedImage> landImageList;
	
	private Random rand;
	
	private List<Object> objects;
	private List<Object> listLand;
	private MainCharacter mainCharacter;
	
	public BackGroundManager (MainCharacter mainCharacter, int width) {
		rand = new Random();
		light1 = Resource.getResouceImage("data/light1.png");
		light2 = Resource.getResouceImage("data/light2.png");
		lightImageList = Arrays.asList(light1, light2);

		painting1 = Resource.getResouceImage("data/painting1.png");
		painting2 = Resource.getResouceImage("data/painting2.png");
		painting3 = Resource.getResouceImage("data/painting3.png");
		paintingImageList = Arrays.asList(painting1, painting2, painting3);

		land1 = Resource.getResouceImage("data/land1.png");
		land2 = Resource.getResouceImage("data/land2.png");
		landImageList = Arrays.asList(land1, land2);

		objects = new ArrayList<Object>();
		listLand = new ArrayList<Object>();
		this.mainCharacter = mainCharacter;
		
		objects.add(createObject(1));
		objects.add(createObject(2));
		
		int numberOfImageLand = width / land1.getWidth() + 2;
		for(int i = 0; i < numberOfImageLand; i++) {
            int landX = i * land1.getWidth();
			Object imageLand = new Object(mainCharacter, landX, LAND_POSY, getRandomImage(landImageList, rand), 3);
			listLand.add(imageLand);
        }
	}
	
	public void update() {
		for(Object e : objects) {
			e.update();
		}
		Object object = objects.get(0);
		if(object.isOutOfScreen()) {
			int type = object.getType();
			objects.clear();
			objects.add(createObject(type));
		}

		//TODO--UPDATE LAND
	}
	
	public void draw(Graphics g) {
		for(Object e : objects) {
			e.draw(g);
		}
		for(Object land : listLand) {
			land.draw(g);
		}
	}
	
	public Object createObject(int type) {
		if(type == 0) {
			return new Object(mainCharacter, 650, 125, getRandomImage(lightImageList, rand), 1);
		}
		else if(type == 2) {
			return new Object(mainCharacter, 650, 125, getRandomImage(paintingImageList, rand), 2);
		}
		else {
			return new Object(mainCharacter, 650, LAND_POSY, getRandomImage(landImageList, rand), 3);
		}
	}

	public static BufferedImage getRandomImage(List<BufferedImage> imageList, Random random) {
        int randomIndex = random.nextInt(imageList.size());
        return imageList.get(randomIndex);
    }
	
	public void reset() {
		objects.clear();
		objects.add(createObject(1));
		objects.add(createObject(2));
	}
}


