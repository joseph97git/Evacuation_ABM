import javax.swing.*;
import java.awt.geom.*;
import java.util.ArrayList;

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
    private double[] velocity;
    private Agent[] other;
    private Ellipse2D.Double person;


    /**
     * Creates a new agent object.
     */

    public Agent(int id, double x, double y, double r, double m, double [] v, Agent[] other) {
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
    public double[] velocity() {
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
    
    
    
    /**
     * @param The wall giving force to Agent
     * @return an x and y component of force vector
     */
    public double[]  wallForce(Wall wall) {
    	double[] result = new double[2];
    	double distance = Math.sqrt(Math.pow(wall.xCoord() - this.xCoord(), 2) + Math.pow(wall.yCoord() - this.yCoord(), 2));
    	if (distance < this.radius())
    	{

    	double xtComp = (wall.getX() - this.getX()) / distance;
    	double ytComp = (wall.getY() - this.getY()) / distance;
    	double[] tiW = new double[] {xtComp,ytComp}; //vector tangent of direction of wall
    	double[] niW = new double[] {ytComp,xtComp * -1}; //vector perpendicular of direction of wall
   	double A = 1.0; //constant
    	double B = 1.0 ; //constant
    	double k = 1.0; //constant
    	
    	result[0] = ((A*Math.exp(this.radius() - distance))/B + k*(this.radius()- distance))*niW[0];	//part1 x component	
    	result[1] = ((A*Math.exp(this.radius() - distance))/B + k*(this.radius()- distance))*niW[1];  //part1 y component
    	
    	double part2 = k*(this.radius() - distance)*(this.velocity()[0] * tiW[0] + this.velocity()[1] * tiW[1]); //part2 of formula
    	result[0] = result[0]+part2*tiW[0]; //combining x components
    	result[1] = result[1]+part2*tiW[1]; //combining y components
    	}
    	else
    	{
    		result[0] = 0;
    		result[1] = 0;
    	}

    	return result;  //returns {x,y} vector array
    	
    }
    
}
