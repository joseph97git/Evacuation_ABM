
import javax.swing.*;

public class Environment {
	
	public void setUp() {
		JFrame window = new JFrame("Evacuation ABM");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Classroom classroom1 = new Classroom();
		Door door1 = new Door();
		Agent agent1 = new Agent();
		
		//window.getContentPane().add(classroom1);
		window.getContentPane().add(door1);
		//window.getContentPane().add(agent1);
		
		
		window.pack();
		window.setVisible(true);
		window.setSize(500, 500);
	}
}
