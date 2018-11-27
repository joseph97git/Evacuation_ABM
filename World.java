
public class World {
	public static void main(String[] args) {
		// Classroom proportion width:depth = 1.2647:1
		int depth = 800;
		double width = depth*1.2647;
		Layout oneRoom = new Layout((int) width, depth);
		oneRoom.run();
	}
}
