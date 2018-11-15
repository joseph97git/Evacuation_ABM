import student.TestCase;


public class AgentTestZane extends TestCase {
	private Agent a;
	private Wall b;
	
	public void setUp()
	{
		double[] pos = new double[] {1,1};
		double[] vel = new double[] {2,2};
		a = new Agent(1, pos, 3.0, 100.0, vel, null);
		b = new Wall(1, 2);
	}
	
	public void testWallForce()
	{
		
	}

}
