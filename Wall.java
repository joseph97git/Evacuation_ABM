import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

/**
 * 
 */

/**
 * @author zsquare
 *
 */
public class Wall extends JPanel {
	private double x;
	private double y;
	private Rectangle2D.Double wall;
	
	public Wall(double x, double y)
	{
		this.x=x;
		this.y=y;
		this.wall = new Rectangle2D.Double(x, y, 4, 4);
	}
	
	public double xCoord()
	{
		return this.x;
	}
	
	public double yCoord()
	{
		return this.y;
	}
	
	public Rectangle2D.Double rect()
	{
		return this.wall;
	}

}
