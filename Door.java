import java.awt.Color;
import java.awt.Graphics;

public class Door extends Classroom{
	public void draw() {
		super.draw();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.RED);
		g.fillRect(225, 420, 50, 10);
	}
}
