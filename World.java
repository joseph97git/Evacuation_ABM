
public class World {
	public static void main(String[] args) {
		// Classroom proportion width:depth = 1.2647:1
		int depth = 800;
		double width;
		
//		// simulation for one room
//		width = depth*1.2647 + 200;
//		Layout1Room oneRoom = new Layout1Room((int) width, depth);
//		oneRoom.run();
		 
//		// simulation for two rooms
//		width = depth*1.2647;
//		Layout2Room twoRoom = new Layout2Room((int) width, depth);
//		twoRoom.run();
		
		width = depth*1.2647;
		LayoutWallTest twoRoom = new LayoutWallTest((int) width, depth);
		twoRoom.run();
	} 
}
