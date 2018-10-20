import javax.swing.*;
import java.awt.*;

public class Classroom extends JPanel
{
	public void draw() {
		repaint();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawRect(50,25,400,400);
	}
}
