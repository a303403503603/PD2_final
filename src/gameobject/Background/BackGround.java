package gameobject.Background;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class BackGround extends JPanel{

    private float alpha;
    private float deltaAlpha=0.1f;
    private boolean increaseAlpha = true;

    public BackGround(float alpha) {
        this.alpha = alpha;
        //this.deltaAlpha = deltaAlpha;
        this.increaseAlpha = true;
    }
/* 
    public void update() {
        if(increaseAlpha) {
            alpha +=deltaAlpha;
            if (alpha >= 1.0f) {
                alpha = 1.0f;
                increaseAlpha = false;
            }
        }else {
            if (alpha <= 0.0f) {
                alpha = 0.0f;
                increaseAlpha = true;
            }
        }       

    
*/
    public void draw(Graphics g) {
        alpha += deltaAlpha;
        
        boolean increasingAlpha = true;
        if (increasingAlpha) {
            alpha += deltaAlpha;
            if (alpha >= 255) {
                alpha = 255;
                increasingAlpha = false;
            }
        } else {
            alpha -= deltaAlpha;
            if (alpha <= 0) {
                alpha = 0;
                increasingAlpha = true;
            }     
        
        }        
        g.setColor(new Color(0, 0, 0, (int)alpha));
        g.fillRect(0, 0, 600, 175);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
       // draw(g);
    }

}
