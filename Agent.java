import javax.swing.*;
import java.awt.geom.*;

/**
 * The agents within the model that
 * mimic human behavior.
 * 
 * @author Joseph Kim
 * @version 10.22.18
 */
public class Agent extends JPanel {

    /**
     * Prevent different serial versions.
     */
    private static final long serialVersionUID = 2L;
    private int id;
    private double x;
    private double y;
    private double xTarget;
    private double yTarget;
    private double radius;
    private double mass;
    private double velocity;
    private Agent[] other;
    private Ellipse2D.Double person;


    /**
     * Creates a new agent object.
     */
    public Agent(int id, double x, double y, double r, double m, double v, Agent[] other) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.radius = r;
        this.mass = m;
        this.velocity = v;
        this.other = other;
        this.person = new Ellipse2D.Double(x, y, r, r);
    }


    /**
     * Gets the associated shape for
     * the agent.
     * 
     * @return The graphic object.
     */
    public Ellipse2D.Double person() {
        return this.person;
    }


    /**
     * Gets the x coordinate.
     * 
     * @return The x coordinate.
     */
    public double xCoord() {
        return this.x;
    }


    /**
     * Gets the y coordinate.
     * 
     * @return The y coordinate.
     */
    public double yCoord() {
        return this.y;
    }


    /**
     * Gets the x coordinate
     * of the destination.
     * 
     * @return The x coordinate.
     */
    public double xTarget() {
        return this.xTarget;
    }


    /**
     * Gets the y coordinate
     * of the destination.
     * 
     * @return The y coordinate.
     */
    public double yTarget() {
        return this.yTarget;
    }


    /**
     * Gets the radius.
     * 
     * @return The radius.
     */
    public double radius() {
        return this.radius;
    }


    /**
     * Gets the mass.
     * 
     * @return The mass.
     */
    public double mass() {
        return this.mass;
    }


    /**
     * Gets the velocity.
     * 
     * @return The velocity.
     */
    public double velocity() {
        return this.velocity;
    }


    /**
     * Sets the x target coordinate
     * to the specified integer.
     * 
     * @param x
     *            The new x coordinate.
     */
    public void setXTarget(int x) {
        this.xTarget = x;
    }


    /**
     * Sets the y target coordinate
     * to the specified integer.
     * 
     * @param y
     *            The new y coordinate.
     */
    public void setYTarget(int y) {
        this.yTarget = y;
    }
}
