import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

/**
 * Represents the door in
 * a building layout.
 * 
 * @author Joseph Kim
 * @version 10.22.18
 */
public class Exit {

    private double x;
    private double y;
    private double width;
    private double length;
    private Rectangle2D.Double door;


    /**
     * Creates a new door object.
     * 
     * @param width
     *            The thickness of the door.
     * @param length
     *            The length of the door.
     */
    public Exit(double x, double y, double width, double length) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.length = length;
        this.door = new Rectangle2D.Double(x, y, width, length);
    }


    /**
     * Gets the x coordinate fo the
     * exit.
     * 
     * @return The x coordinate.
     */
    public double xCoord() {
        return this.x;
    }


    /**
     * Gets the y coordinate of the
     * exit.
     * 
     * @return The y coordinate.
     */
    public double yCoord() {
        return this.y;
    }


    /**
     * Gets the width of the exit.
     * 
     * @return The width.
     */
    public double width() {
        return this.width;
    }


    /**
     * Gets the length of the exit.
     * 
     * @return The length.
     */
    public double length() {
        return this.length;
    }


    /**
     * Gets the rectangle that represents
     * the door.
     * 
     * @return The door.
     */
    public Rectangle2D.Double door() {
        return this.door;
    }
}
