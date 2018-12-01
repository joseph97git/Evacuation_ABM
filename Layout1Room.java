import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.*;
import javax.swing.*;
import java.util.Random;

/**
 * Base layout of building.
 * Default Layout: single room,
 * one door, one agent.
 * 
 * @author Joseph Kim
 * @version 10.22.18
 */
public class Layout1Room extends JPanel implements ActionListener {

    /**
     * Prevent different serial versions.
     */
    private static final long serialVersionUID = 1L;

    private static final double THICKNESS = 25;
    private Agent[] agents;
    private int width;
    private int depth;
    private Timer timer;
    private Exit exit1, mainExit;
    private double xCoord, yCoord, rmWidth, rmHeight;
    private Rectangle2D room;

    /**
     * Creates a new SingleRoom object.
     * 
     * @param width
     *            The width of the room, if you are
     *            standing in the doorway looking in.
     * @param depth
     *            The depth of the room, if you are
     *            standing in the doorway looking in.
     */
    public Layout1Room(int width, int depth) {
        this.width = width;
        this.depth = depth;
        this.timer = new Timer(5, this);
        agents = new Agent[30];
        int n = agents.length;

        // Room coordinate
        this.xCoord = 25;
        this.yCoord = 25;
        this.rmWidth = (this.depth - 80) * 1.2647;
        this.rmHeight = this.depth - 97;
        this.room = new Rectangle2D.Double(xCoord, // Center the room
            yCoord, rmWidth, rmHeight);
        
        double exit1X = room.getWidth() + (this.width - (this.depth - 80) * 1.2647) / 2 - room.getWidth() / 200 / 2 - 114;
        double exit1Y = room.getY() + room.getWidth() / 18.889;
        double exit1W = room.getWidth() / 200;
        double exit1H = room.getHeight() / 7.5055;
        this.exit1 = new Exit(exit1X, exit1Y, exit1W, exit1H);
        
        // Hallway exit
        double exit2H = room.getWidth() / 200;
        double exit2W = room.getHeight() / 7.5055;
        double exit2X = this.xCoord + this.rmWidth + THICKNESS + 
        		((this.width - THICKNESS*2) - (this.xCoord + this.rmWidth + THICKNESS))/2 - ((room.getHeight() / 7.5055) / 2); 
        double exit2Y = this.yCoord + this.rmHeight + THICKNESS/2 - exit2H/2;
        this.mainExit = new Exit(exit2X, exit2Y, exit2W, exit2H);
        
        //exits set to target for agents
        double[] target = new double[2];
        target[0] = this.exit1.xCoord();
        target[1] = this.exit1.yCoord() + this.exit1.length() / 2;
        
		double[] target2 = new double[2];
        target2[0] = mainExit.xCoord() + mainExit.width() / 2;
        target2[1] = mainExit.yCoord();
        
//        double min = 100;
//        double max = 700;
//        double[][] pre_pos = new double[n][2];
//        pre_pos[0][0] = 0.0;
//        pre_pos[0][1] = 0.0;
//        
        for (int i = 0; i < n; i++) {
//        	Random r = new Random();
        	
        	double[] vel = new double[2];
            vel[0] = 0.0;
            vel[1] = 0.0;
            
//            double[] cur_pos = new double[2];
//            cur_pos[0] = min + (max - min)*r.nextDouble();
//            cur_pos[1] = min + (max - min)*r.nextDouble();
             
            double[] pos = new double[2];
            pos[0] = 600*Math.random() + 100;
            pos[1] = 600*Math.random() + 100;
            
//            for (int j = 0; j < pre_pos.length; j++) {
//            	if ((cur_pos[0] >= pre_pos[j][0] - 100 && cur_pos[0] <= pre_pos[j][0] + 100) && 
//            			(cur_pos[1] >= pre_pos[j][1] - 100 && cur_pos[1] <= pre_pos[j][1] + 100)) {
//            		cur_pos[0] = min + (max - min)*r.nextDouble();
//                    cur_pos[1] = min + (max - min)*r.nextDouble();
//            	}
//            }
            
        	this.agents[i] = new Agent(i, pos, 15, 800.0, vel, this.agents, null);
        	this.agents[i].setTarget(target);
        	this.agents[i].updateR(exit1.length() / 3.02);
        	this.agents[i].setExit(target2);
//        	pre_pos[i][0] = cur_pos[0];
//        	pre_pos[i][1] = cur_pos[1];
        	
        }
        
    }


    /**
     * Runs the single room evacuation.
     */
    public void run() {
        JFrame window = new JFrame("Evacuation ABM");
        window.add(this);
        window.setVisible(true);
        window.setSize(this.width, this.depth);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    /**
     * Paints the environment and
     * everything within it.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g1 = (Graphics2D)g;
        this.create(g1);
        this.timer.start();
    }


    /**
     * Calls parent and every child's
     * paintComponent method.
     */
    public void actionPerformed(ActionEvent e) {
        // update position
        for (int i = 0; i < this.agents.length; i++) {
            this.agents[i].updateVectors();
        }
        repaint();
    }


    /**
     * Creates the type of layout.
     * 
     * @param graphics
     */
    public void create(Graphics2D graphics) {
        // Classroom proportion width:depth = 1.2647:1
        // single room with a hall way
        // double xCoord = ((this.width - (this.depth - 80) * 1.2647)) / 2;
    	
    	// Classroom 
    	
        Obstacle leftWall = new Obstacle(xCoord - THICKNESS, yCoord - THICKNESS,
            THICKNESS, rmHeight + 2 * THICKNESS);
        Obstacle topWall = new Obstacle(xCoord, yCoord - THICKNESS, rmWidth
            + THICKNESS, THICKNESS);
        Obstacle bottomWall = new Obstacle(xCoord, yCoord + rmHeight, rmWidth
            + THICKNESS, THICKNESS);
        Obstacle rightWall_1 = new Obstacle(xCoord + rmWidth, yCoord, THICKNESS,
            room.getY() + room.getWidth() / 18.889 - yCoord);
        Obstacle rightWall_2 = new Obstacle(xCoord + rmWidth, room.getY() + room
            .getWidth() / 18.889 + room.getHeight() / 7.5055, THICKNESS, rmHeight
                - ((room.getY() + room.getWidth() / 18.889 - yCoord) + (room
                    .getHeight() / 7.5055)));
        
        graphics.setPaint(Color.BLACK);
        //graphics.draw(room);
        graphics.fill(leftWall.wall());
        graphics.fill(topWall.wall());
        graphics.fill(bottomWall.wall());
        graphics.fill(rightWall_1.wall());
        graphics.fill(rightWall_2.wall());
        
        // Hallway with exit
        
        Obstacle hallwayWall = new Obstacle(this.width - THICKNESS*2, this.yCoord - THICKNESS, THICKNESS, this.rmHeight + 2* THICKNESS);
        Obstacle bottomWall_1 = new Obstacle(this.xCoord + this.rmWidth + THICKNESS, 
        		this.yCoord + this.rmHeight, 
        		((this.width - THICKNESS*2) - (this.xCoord + this.rmWidth + THICKNESS))/2 - ((room.getHeight() / 7.5055) / 2), 
        		THICKNESS);
        Obstacle bottomWall_2 = new Obstacle(this.xCoord + this.rmWidth + THICKNESS + 
        		((this.width - THICKNESS*2) - (this.xCoord + this.rmWidth + THICKNESS))/2 
        		- ((room.getHeight() / 7.5055) / 2) + room.getHeight() / 7.5055, 
        		this.yCoord + this.rmHeight, 
        		((this.width - THICKNESS*2) - (this.xCoord + this.rmWidth + THICKNESS))/2 - ((room.getHeight() / 7.5055) / 2), 
        		THICKNESS);
        
        graphics.setPaint(Color.BLACK);
        graphics.fill(hallwayWall.wall());
        graphics.fill(bottomWall_1.wall());
        graphics.fill(bottomWall_2.wall());
      
        // Door width:room width = 1:200
        // Door depth:room depth = 1:7.5055
        // Distance from corner to the edge of the door:room depth = 1:18.889

        graphics.setPaint(Color.GREEN);
        graphics.fill(exit1.door());
        graphics.fill(mainExit.door());
        
        
        
        // Populate agents
        for (int i=0; i < this.agents.length; i++) {
        	graphics.setPaint(Color.BLUE);
        	graphics.fill(this.agents[i].person());
        }
        
//        for (int i=0; i < this.agents.length; i++) {
//        	if ((this.agents[i].p()[0] >= exit1X) &&
//        			(this.agents[i].p()[1] >= exit1Y)) {  
//                this.agents[i].setTarget(target2);
//        	}
//        }
        
        
        
//        this.agents[0].setTarget(target);
//        this.agents[1].setTarget(target);
//
//        // radius of a person : door depth = 1:3.04
//        // update agents' radii based on the proportions
//        this.agents[0].updateR(exit1.length() / 6.04);
//        this.agents[1].updateR(exit1.length() / 6.04);
//
//        graphics.setPaint(Color.BLUE);
//        graphics.fill(this.agents[0].person());
//        graphics.fill(this.agents[1].person());
        
          double[] target2 = new double[2];
          target2[0] = mainExit.xCoord() + mainExit.width() / 2;
          target2[1] = mainExit.yCoord();  
    }

}
