
import javax.swing.*;

public class Environment {
	
	public void setUp() {
		JFrame window = new JFrame("Evacuation ABM");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Classroom classroom1 = new Classroom();
		Door door1 = new Door();
		
		window.getContentPane().add(classroom1);
		window.add(door1);
		classroom1.draw();
		
		window.pack();
		window.setVisible(true);
		window.setSize(500, 500);
	}
}
