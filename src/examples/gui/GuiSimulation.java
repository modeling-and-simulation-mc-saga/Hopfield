package examples.gui;

import examples.KanjiPatterns;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;
import model.Hopfield;

/**
 *
 * @author tadaki
 */
public class GuiSimulation implements Runnable {

    private final KanjiPatterns pattern = new KanjiPatterns();
    private final int maxNumPatterns = pattern.getNumPatterns();
    private final double lambda = 1. / pattern.getSize();
    private final int n = (int) Math.sqrt(pattern.getSize());
    private final Hopfield sys = new Hopfield(pattern, lambda,
            new Random(1148L));
    private double temperature = 1;
    private boolean zeroTemperature = false;

    private volatile boolean running = false;
    private final DrawPanel drawPanel;
    private BufferedImage image;
    private final int sleepTime = 10;
    private int r;

    public GuiSimulation(DrawPanel drawPanel) {
        this.drawPanel = drawPanel;
    }

    public void initImage() {
        Dimension dimension = drawPanel.getPreferredSize();
        int width = dimension.width - 20;
        int height = dimension.height - 20;
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();
        Color c = drawPanel.getBackground();
        g.setColor(c);
        g.fillRect(0, 0, width, height);
        r = width/n;
        drawPanel.setImage(image);
    }

    public void update() {
        Color cd[] = {Color.CYAN, Color.RED};
        if (zeroTemperature) {
            sys.updateZero();
        } else {
            sys.update(temperature);
        }
        Graphics g = image.getGraphics();
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < n; i++) {
                int k = j * n + i;
                g.setColor(cd[(sys.getValue(k) + 1)/2]);
                g.fillRect(r*i, r*j, r, r);
            }
        }
        drawPanel.setImage(image);
    }

    @Override
    public void run() {
        while (running) {
            update();
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
            }
        }
    }

    public void start() {
        running = true;
        System.out.println("start");
    }

    public void stop() {
        running = false;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public void setZeroTemperature(boolean zeroTemperature) {
        this.zeroTemperature = zeroTemperature;
    }

    public void setNumPatterns(int numPatterns){
        if(numPatterns <1||numPatterns>maxNumPatterns){
            numPatterns =maxNumPatterns;
        }
        sys.setNumPatterns(numPatterns);
    }

    public int getMaxNumPatterns() {
        return maxNumPatterns;
    }
   
}
