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

    private static final double A = 2000.0; // N
    private static final double B = 0.08; // m
    private static final double tau = 0.5; // s
    private static final double k = 120000.0; // kg * s^{-2}
    private static final double kappa = 240000.0; // kg * m^{-1} * s^{-1}
    private static final double vi = 0.8; // m * s^{-1}
    private static final double delta_t = 0.01; // s
    private static final double scaler = 150;
 
    private int id; // identification
    private double[] p; // position
    private double[] target; // exit
    private double[] mainExit; // building exit
    private double radius;
    private double mass;
    private double[] velocity;
    private Agent[] other; // all agents
    private Obstacle[] walls;
    private Ellipse2D.Double person; // GUI representation


    /**
     * Creates a new agent object.
     */
    public Agent(
        int id,
        double[] p,
        double r,
        double m,
        double[] v,
        Agent[] other,
        Obstacle[] walls) {
        this.id = id;
        this.p = p;
        this.radius = r;
        this.mass = m;
        this.velocity = v;
        this.other = other;
        this.walls = walls;
        this.person = new Ellipse2D.Double(this.p[0] - r, this.p[1] - r, 2 * r, 2 * r);

    }


    /**
     * Updates this agent's position
     * and velocity using Euler forward
     * model.
     */
    public void updateVectors() {
        // velocity time-step
        double[] vtime = new double[2];
        vtime[0] = this.velocity[0] * delta_t;
        vtime[1] = this.velocity[1] * delta_t;
        //if (this.id == 0)
        //    System.out.println(Math.sqrt(this.velocity[0] * this.velocity[0] + this.velocity[1] * this.velocity[1]));
        // force time-step
        double[] ftime = new double[2];
        double[] force = this.f();
        ftime[0] = (force[0] * delta_t / this.mass);
        ftime[1] = (force[1] * delta_t / this.mass);
        // position update
        this.p[0] = (this.p[0] + vtime[0] * scaler);
        this.p[1] = (this.p[1] + vtime[1] * scaler);
        this.person.setFrame(this.p[0] - this.radius, this.p[1] - this.radius, 2 * this.radius, 2 * this.radius);
        this.updateTarget();
        // velocity update
        this.velocity[0] = this.velocity[0] + ftime[0];
        this.velocity[1] = this.velocity[1] + ftime[1];

    }


    /**
     * Calculates the distance between two agents'
     * p of masses.
     * 
     * @param i
     *            The first agent
     * @param j
     *            The second agent
     * @return The distance between them.
     */
    public double dist_ij(double[] i, double[] j) {
        double x1 = i[0];
        double y1 = i[1];
        double x2 = j[0];
        double y2 = j[1];
        double diffXsqrd = (x2 - x1) * (x2 - x1);
        double diffYsqrd = (y2 - y1) * (y2 - y1);
        return Math.sqrt(diffXsqrd + diffYsqrd);
    }


    /**
     * Calculates the forces acting on the agent
     * given a position vector and the current
     * time step.
     * 
     * @return The resulting force vector.
     */
    public double[] f() {
        double[] interactiveForce = new double[2]; // force btw agents
        double[] wallForce = new double[2]; // force with walls
        double[] agentForce = this.f_i(); // force to evacuate
        interactiveForce[0] = 0;
        interactiveForce[1] = 0;
        wallForce[0] = 0;
        wallForce[1] = 0;
        // loop through the other agents
        // compute inner-agent forces
        for (int i = 0; i < this.other.length; i++) {
            if (i != this.id) {
                if (this.isTouching(this, this.other[i])) {
                    double[] bf = this.f_ij(this, this.other[i]);
                    interactiveForce[0] = interactiveForce[0] + bf[0];
                    interactiveForce[1] = interactiveForce[1] + bf[1];
                }
            }
        }
        // loop through the surrounding walls
        // compute wall-agent forces

        // calculate force
        double[] ftot = new double[2];
        ftot[0] = agentForce[0] + interactiveForce[0];
        ftot[1] = agentForce[1] + interactiveForce[1];
        return ftot;
    }
    
    public double[] f_iW(Agent i, Wall w) {
    		double d_iw = this.dist_ij(w.p(), this.p())/scaler;
    		double[] result = new double[2];
    		double[] n_iW = this.n_iW(i, w);
    		double[] t_iW = this.t_iW(i, w);
    		double[] vel = this.velocity();
    		result[0] = (A * Math.exp((this.radius() - d_iw)/B) + kappa*(this.radius()- d_iw))*n_iW[0]/scaler;		
    		result[1] = (A * Math.exp((this.radius() - d_iw)/B) + kappa*(this.radius()- d_iw))*n_iW[1]/scaler;
    		double[] comp2 = new double[2];
    		comp2[0] = k*(this.radius()-d_iw)*(vel[0]*t_iW[0]+ vel[1]*t_iW[1])*t_iW[0]/scaler;
    		comp2[1] = k*(this.radius()-d_iw)*(vel[0]*t_iW[0]+ vel[1]*t_iW[1])*t_iW[1]/scaler;
    		result[0] -= comp2[0];
    		result[1] -= comp2[1];
    				
    		return result;
    		
    }


    /**
     * Calculates the force pushing the
     * agent to the exit. Represents the
     * agent's internal motivation to get
     * to the nearest exit.
     * 
     * @return The motivation force vector.
     */
    public double[] f_i() {
        double x_comp = this.velocity[0];
        double y_comp = this.velocity[1];
        double velNorm = Math.sqrt(x_comp * x_comp + y_comp * y_comp);
        double[] zVec = new double[2];
        zVec[0] = 0.0;
        zVec[1] = 0.0;
        if (velNorm < vi) {
            double[] velNew = new double[2];
            double[] velMax = new double[2];
            double[] f = new double[2];
            double[] ei = this.e_i();
            velMax[0] = vi * (ei[0] - this.p()[0]) + this.p()[0];
            velMax[1] = vi * (ei[1] - this.p()[1]) + this.p()[1];
            velNew[0] = (velMax[0] - (this.velocity[0] + this.p()[0]));
            velNew[1] = (velMax[1] - (this.velocity[1] + this.p()[1]));
            f[0] = (this.mass * (velNew[0] / tau));
            f[1] = (this.mass * (velNew[1] / tau));
            return f;
        }
        return zVec;
    }


    /**
     * Calculates the 'body force' and 'tangential force'
     * between two agents.
     * 
     * @param i
     *            The first agent
     * @param j
     *            The second agent
     * @return The resulting body force vector.
     */
    public double[] f_ij(Agent i, Agent j) {
     // base info
        double r_ij = i.radius() / scaler + j.radius() / scaler;
        double d_ij = this.dist_ij(i.p(), j.p()) / scaler;
        double[] n_ij = this.n_ij(j.p());
        double[] v_ji = this.v_ji(j.velocity());
        double[] t_ij = this.t_ij(n_ij);
        // create scalars
        double normal_scaler = A * Math.exp((r_ij - d_ij) / B) + k * g(r_ij
            - d_ij);
        double tangent_scaler = kappa * g(r_ij - d_ij) * this.delta_v_ji(v_ji,
            t_ij);
        // calculate force components
        double[] bodyForce = new double[2];
        bodyForce[0] = normal_scaler * n_ij[0] / scaler;
        bodyForce[1] = normal_scaler * n_ij[1] / scaler;
        double[] tangentForce = new double[2];
        tangentForce[0] = tangent_scaler * t_ij[0] / scaler;
        tangentForce[1] = tangent_scaler * t_ij[1] / scaler;
        // calculate sum of forces
        double[] f_ij = new double[2];
        f_ij[0] = bodyForce[0] + tangentForce[0];
        f_ij[1] = bodyForce[1] + tangentForce[1];
        return f_ij;
    }


    /**
     * Calculates the 'body force' and 'tangential force'
     * between this agent and the walls.
     * 
     * @param i
     *            This agent
     * @param w
     *            The wall
     * @return The resulting force vector.
     */
    public double[] n_iW(Agent i, Wall w) {
        double[] wallPos = new double[] {w.xCoord(), w.yCoord()};
        double[] thisPos = new double[] {this.getX(), this.getY()};
        
        double d_ij = this.dist_ij(wallPos, thisPos);
        double[] f_iW = new double[2];
        f_iW[0] = (wallPos[0] - thisPos[0])/d_ij;
        f_iW[1] = (wallPos[1] - thisPos[1])/d_ij;
        return f_iW;
    }
    
    /**
     * Calculates the 'body force' and 'perpendicular force'
     * between this agent and the walls.
     * 
     * @param i
     *            This agent
     * @param w
     *            The wall
     * @return The resulting force vector.
     */
    public double[] t_iW(Agent i, Wall w) {
    		w = new Wall(w.xCoord(), w.yCoord());
    		double[] fiW = this.n_iW(this, w);
    		double[] t_iW = new double[] {fiW[1], -fiW[0]};
    		return t_iW;
    }
    
    


    /**
     * Calculates the direction to the
     * target as a unit vector.
     * 
     * @return The resulting unit vector.
     */
    public double[] e_i() {
        double xi = this.p[0];
        double yi = this.p[1];
        double xt = this.target[0];
        double yt = this.target[1];
        double dist_it = this.dist_ij(this.p, this.target);
        double[] unit = new double[2];
        unit[0] = ((xt - xi) / dist_it) + xi;
        unit[1] = ((yt - yi) / dist_it) + yi;
        return unit;
    }


    /**
     * Gets the unit normal vector pointing from
     * agent j to agent i (this agent)
     * 
     * @param j
     *            The p of the other agent.
     * @return The unit normal vector
     */
    public double[] n_ij(double[] j) {
        double d_ij = this.dist_ij(this.p(), j);
        double xi = this.p[0];
        double yi = this.p[1];
        double xj = j[0];
        double yj = j[1];
        double[] unit = new double[2];
        unit[0] = (xi - xj) / d_ij;
        unit[1] = (yi - yj) / d_ij;
        return unit;
    }


    /**
     * Returns the unit tangential vector of this agent
     * relative to the other agent.
     * 
     * @param n_ij
     *            The unit normal vector pointing from
     *            agent j to agent i (this agent).
     * @return unit tangential vector
     */
    public double[] t_ij(double[] n_ij) {
        double[] unit = new double[2];
        unit[0] = -1 * n_ij[1];
        unit[1] = n_ij[0];
        return unit;
    }


    /**
     * Calculates the velocity difference between
     * agent i (this agent) and agent j.
     * 
     * @param v_j
     *            The velocity vector of agent j.
     * @return The velocity difference.
     */
    public double[] v_ji(double[] v_j) {
        double v_xi = this.velocity[0];
        double v_yi = this.velocity[1];
        double[] v_result = new double[2];
        v_result[0] = v_j[0] - v_xi;
        v_result[1] = v_j[1] - v_yi;
        return v_result;
    }


    /**
     * Calculates the tangential velocity difference.
     * 
     * @param v_ji
     *            The velocity difference.
     * @param t_ij
     *            The unit tangent vector.
     * @return The tangential velocity difference.
     */
    public double delta_v_ji(double[] v_ji, double[] t_ij) {
        return (v_ji[0] * t_ij[0]) + (v_ji[1] * t_ij[1]);
    }


    /**
     * Returns 0 if the argument x (r_ij - d_ij) and
     * returns x (r_ij - d_ij) otherwise.
     * 
     * @param x
     *            r_ij - d_ij
     * @return 0, if not touching. x, otherwise.
     */
    private double g(double x) {
        if (x < 0) {
            return 0.0;
        }
        return x;
    }


    /**
     * Checks if two agents are touching.
     * 
     * @param a1
     *            The first agent
     * @param a2
     *            The second agent
     * @return True, if touching. False, if not.
     */
    public boolean isTouching(Agent a1, Agent a2) {
        return dist_ij(a1.p(), a2.p()) <= (a1.radius() + a2.radius());
    }
    
    /**
     * Checks if agent and wall are touching.
     * 
     * @param a1
     *            The first agent
     * @param a2
     *            The second agent
     * @return True, if touching. False, if not.
     */
    public boolean isTouching2(Agent a1, Wall a2) {
        return dist_ij(a1.p(), a2.p()) <= (a1.radius());
    }
    /**
     * update the target to the hallway exit once
     * the agent leaves the room
     */
    public void updateTarget() {
    	double x = dist_ij(this.p, this.target);
    	if(x < 4) {
        	this.setTarget(this.mainExit); 
        	System.out.println("X: " + Double.toString(this.target[0]) + " Y: " + Double.toString(this.target[1]));
        }

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
     * Sets the position of the agent
     */
    public void setPos(double[] pos) {
        this.p = pos;
    }


    /**
     * Gets the p of the agent.
     * 
     * @return The array with p coordinates.
     */
    public double[] p() {
        return this.p;
    }


    /**
     * Gets the target coordinates.
     * 
     * @return The target coordinates.
     */
    public double[] target() {
        return this.target;
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

    public double[] mainExit() {
    	return this.mainExit;
    }

    /**
     * Sets the target coordinates
     * 
     * @param target
     *            The target coordinates
     */
    public void setTarget(double[] target) {
        this.target = target;
    }
    
    public void setExit(double[] e) {
    	this.mainExit = e;
    }
    /**
     * Update radius to fit the proportion
     * 
     * @param r
     * 			The radius of the agent
     */
    public void updateR(double r) {
    	this.radius = r;
    }
}
