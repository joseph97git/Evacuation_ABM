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

    private int x;
    private int y;
    private int width;
    private int length;
    private Rectangle2D.Double door;


    /**
     * Creates a new door object.
     * 
     * @param width
     *            The thickness of the door.
     * @param length
     *            The length of the door.
     */
    public Exit(int x, int y, int width, int length) {
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
    public int xCoord() {
        return this.x;
    }


    /**
     * Gets the y coordinate of the
     * exit.
     * 
     * @return The y coordinate.
     */
    public int yCoord() {
        return this.y;
    }


    /**
     * Gets the width of the exit.
     * 
     * @return The width.
     */
    public int width() {
        return this.width;
    }


    /**
     * Gets the length of the exit.
     * 
     * @return The length.
     */
    public int length() {
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
