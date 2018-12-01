import abbot.util.AWT;
import student.TestCase;


public class AgentTestZane extends TestCase {
	private Agent a;
	private Obstacle b;
	private Obstacle[] walls;
	private Agent[] agents;
	
	public void setUp()
	{
		double[] pos = new double[] {0,0};
		double[] vel = new double[] {1,1};
		b = new Obstacle(2, 0, 4, 4);
		walls = new Obstacle[1];
		walls[0] = b;
		a = new Agent(1, pos, 3.0, 80.0, vel, null, walls);
		agents = new Agent[1];
		agents[0] = a;
		
	}
	
	/**
	 * test the wall force method
	 */
	public void testWallForce()
	{
		Obstacle c = new Obstacle(2, 0, 2, 2);
		double[] result = a.f_iW(a, c);
		//Ends up being 2.37... seems to be correct 
		assertNull(result[0]);
		
		
		
	}
	
	public void testInt()
	{
		Obstacle c = new Obstacle(2, 0, 2, 2);
		double[] res = a.wallIntersection(a, c);
		assertEquals(3.0, res[0], .01);
	}
	
	/**
	 * tests the niw method
	 */
	public void testNiw()
	{
		double[] result = a.n_iW(a, b);
		assertEquals(0.0, result[1], .01);
		
	}
	
	public void testIntersect()
	{
		double[] result = a.wallIntersection(a, b);
		assertEquals(3.0, result[0], .01);
		assertEquals(0.0, result[1], .01);
	}
	
	public void testF()
	{
		double[] pos = new double[] {0,0};
		double[] vel = new double[] {1,1};
		Agent d = new Agent(2, pos,  3.0, 80.0, vel, agents, walls );
		double[] result = d.f();
		assertEquals(0.0, result[0], 0.1);
	}
	
	/**
	 * tests the tiw method
	 */
	public void testTiw()
	{
		double[] result = a.t_iW(a, b);
		assertEquals(-1.0,result[1], .01);
	}
	
	public void testIsTouchingWall1()
	{
		assertTrue(a.isTouching2(a, b));
	}
	
	public void testIsTouchingWall2()
	{
		Obstacle c = new Obstacle (5, 5, 4, 4);
		assertFalse(a.isTouching2(a, c));
	}
	
	

}
