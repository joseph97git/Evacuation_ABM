
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
	private double p;
	private double length;
	private double width;
	
	public Wall(double x, double y, double w, double l)
	{
		this.x=x;
		this.y=y;
		this.width = w;
		this.length = l;
		this.wall = new Rectangle2D.Double(x, y, width, length);
	}
	
	public double xCoord()
	{
		return this.x;
	}
	
	public double yCoord()
	{
		return this.y;
	}
	
	public double getXRange()
	{
		return this.width;
	}
	
	public double getYRange()
	{
		return this.length;
	}
	
	
	
	public Rectangle2D.Double wall()
	{
		return this.wall;
	}

	public double[] p() {
		double[] pos = new double[] {this.xCoord(), this.yCoord()};
		return pos;
	}
}
