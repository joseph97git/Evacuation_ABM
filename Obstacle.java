import java.awt.geom.Rectangle2D;

/**
 * Wall represented as a 2D rectangle.
 * 
 * @author Joseph Kim
 * @version 11.19.18
 */
public class Obstacle {

    private double x;
    private double y;
    private double width;
    private double height;
    private Rectangle2D.Double wall;


    /**
     * Creates a new wall object.
     * 
     * @param x
     *            The x coordinate
     * @param y
     *            The y coordinate
     * @param width
     *            The width
     * @param length 
     *            The length
     */
    public Obstacle(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.wall = new Rectangle2D.Double(this.x, this.y, this.width, this.height);
    }


    /**
     * Gets the x coordinate.
     * 
     * @return The x coordinate
     */
    public double xCoord() {
        return this.x;
    }


    /**
     * Gets the y coordinate.
     * 
     * @return The y coordinate
     */
    public double yCoord() {
        return this.y;
    }


    /**
     * Gets the width.
     * 
     * @return The width
     */
    public double width() {
        return this.width;
    }


    /**
     * Gets the height.
     * 
     * @return The height.
     */
    public double height() {
        return this.height;
    }
    
    public Rectangle2D.Double wall() {
        return this.wall;
    }


    /**
     * Sets the x coordinate.
     * 
     * @param x
     *            The new x coordinate
     */
    public void setX(double x) {
        this.x = x;
    }


    /**
     * Sets the y coordinate.
     * 
     * @param y
     *            The new y coordinate
     */
    public void setY(double y) {
        this.y = y;
    }


    /**
     * Sets the width.
     * 
     * @param w
     *            The new width
     */
    public void setWidth(double w) {
        this.width = w;
    }


    /**
     * Sets the height.
     * 
     * @param h
     *            The new height
     */
    public void setHeight(double h) {
        this.height = h;
    }
    
    public double[] p() {
		double[] pos = new double[] {this.xCoord(), this.yCoord()};
		return pos;
	}
}
