import student.TestCase;


public class AgentTestZane extends TestCase {
	private Agent a;
	private Wall b;
	
	public void setUp()
	{
		double[] pos = new double[] {0,0};
		double[] vel = new double[] {1,1};
		a = new Agent(1, pos, 3.0, 100.0, vel, null);
		b = new Wall(1, 2);
	}
	
	public void testWallForce()
	{
		double[] result = a.f_iW(a, b);
		assertNull(result[0]);
		
	}

}
