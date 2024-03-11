package game.component;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;

public class PanelGame extends JComponent{
    private Graphics2D g2;
    private BufferedImage image;
    private int width;
    private int height;
    private Thread  thread;
    private boolean start = true;
    
    // Game FPS
    private final int FPS = 60;
    private final int TARGET_TIME = 1000000000 / FPS;
    
    public void start(){
        width = getWidth();
        height = getHeight();
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        thread = new Thread(() -> {
            while(start){
                long startTime= System.nanoTime();
                drawBackground();
                drawGame();
                render();
                long time= System.nanoTime()- startTime;
                if(time < TARGET_TIME){
                    long sleep = (TARGET_TIME - time) / 1000000;
                    try {
                        Thread.sleep(sleep);
                    } catch (InterruptedException ex) {
                        System.err.println(ex);
                    }
                }
            }
        });
        thread.start();
    }
    
    private void drawBackground(){
        g2.setColor(new Color(30, 30, 30));
        g2.fillRect(0, 0, width, height);
    }
   
    private void drawGame(){
        // Implement game drawing logic here
    }
    
    private void render(){
        Graphics g = getGraphics();
        if (g != null) {
            g.drawImage(image, 0, 0, null);
            g.dispose();
        }
    }
}
