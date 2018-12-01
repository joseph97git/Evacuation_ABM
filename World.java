
public class World {
	public static void main(String[] args) {
		// Classroom proportion width:depth = 1.2647:1
		int depth = 800;
		double width = depth*1.2647;
		Layout2Room twoRoom = new Layout2Room((int) width, depth);
		twoRoom.run();
	} 
}
