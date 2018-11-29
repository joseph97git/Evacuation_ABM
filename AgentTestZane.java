import student.TestCase;


public class AgentTestZane extends TestCase {
	private Agent a;
	private Wall b;
	
	public void setUp()
	{
		double[] pos = new double[] {0,0};
		double[] vel = new double[] {1,1};
		a = new Agent(1, pos, 3.0, 100.0, vel, null, null);
		b = new Wall(1, 0);
	}
	
	/**
	 * test the wall force method
	 */
	public void testWallForce()
	{
		double[] result = a.f_iW(a, b);
		//Ends up being 2.37... seems to be correct 
		assertNull(result[0]);
		
	}
	
	/**
	 * tests the niw method
	 */
	public void testNiw()
	{
		double[] result = a.n_iW(a, b);
		assertEquals(0.0, result[1], .01);
		
	}
	
	/**
	 * tests the tiw method
	 */
	public void testTiw()
	{
		double[] result = a.t_iW(a, b);
		assertEquals(-1.0,result[1], .01);
	}
	
	

}
