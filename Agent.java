import javax.swing.*;
import java.awt.*;

public class Agent extends JPanel {

    public void draw() {
        repaint();
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLUE);
        g.fillOval(200, 200, 40, 40);
    }
}
