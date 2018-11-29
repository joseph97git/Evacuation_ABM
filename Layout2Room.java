import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.*;
import javax.swing.*;

/**
 * Base layout of building.
 * Default Layout: single room,
 * one door, one agent.
 * 
 * @author Joseph Kim
 * @version 10.22.18
 */
public class Layout2Room extends JPanel implements ActionListener {

    /**
     * Prevent different serial versions.
     */
    private static final long serialVersionUID = 1L;

    private static final double THICKNESS = 25;
    private Agent[] agents;
    private int width;
    private int depth;
    private Timer timer;


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
    public Layout2Room(int width, int depth) {
        this.width = width;
        this.depth = depth;
        this.timer = new Timer(5, this);
        agents = new Agent[4];
        
        //agent 1
        double[] v_init1 = new double[2];
        v_init1[0] = 0.0;
        v_init1[1] = 0.0;

        double[] pos1 = new double[2];
        pos1[0] = 30.0;
        pos1[1] = 50.0;
       
        //agent 2
        double[] v_init2 = new double[2];
        v_init2[0] = 0.0;
        v_init2[1] = 0.0;

        double[] pos2 = new double[2];
        pos2[0] = 60.0;
        pos2[1] = 50.0;
        
      //agent 3
        double[] v_init3 = new double[2];
        v_init3[0] = 0.0;
        v_init3[1] = 0.0;

        double[] pos3 = new double[2];
        pos3[0] = 700.0;
        pos3[1] = 50.0;
       
        //agent 4
        double[] v_init4 = new double[2];
        v_init4[0] = 0.0;
        v_init4[1] = 0.0;

        double[] pos4 = new double[2];
        pos4[0] = 600.0;
        pos4[1] = 50.0;

        Agent agent1 = new Agent(0, pos1, 15, 80.0, v_init1, this.agents, null);
        Agent agent2 = new Agent(1, pos2, 15, 80.0, v_init2, this.agents, null);
        Agent agent3 = new Agent(2, pos3, 15, 80.0, v_init3, this.agents, null);
        Agent agent4 = new Agent(3, pos4, 15, 80.0, v_init4, this.agents, null);

        this.agents[0] = agent1;
        this.agents[1] = agent2;
        this.agents[2] = agent3;
        this.agents[3] = agent4;
        
        double xCoord2 = ((this.width - (this.depth - 80) * 1.2647))*5 ;
        double yCoord2 = 25;
        double width2 = ((this.depth - 80) * 1.2647)/3;
        double height2 = (this.depth - 97)/3;
        Rectangle2D room2 = new Rectangle2D.Double(xCoord2+THICKNESS, // Center the room
            yCoord2, width2, height2);
      

        
        double xCoord = 25;
        double yCoord = 25;
        double width1 = ((this.depth - 80) * 1.2647)/3;
        double height = (this.depth - 97)/3;
        Rectangle2D room = new Rectangle2D.Double(xCoord, // Center the room
            yCoord, width1, height);                  
        
        Exit exit1 = new Exit(room.getWidth() + ((this.width - (this.depth - 80)
                * 1.2647) / 2 - room.getWidth() / 200 / 2)-THICKNESS, room.getY() + room
                    .getWidth() / 18.889, room.getWidth() / 200, room.getHeight()
                        / 7.5055);
        
        Exit exit2 = new Exit(room2.getX(), room2.getY() + room2
                .getWidth() / 18.889, room2.getWidth() / 200, room2.getHeight()
                    / 7.5055);

               
     // 4 agents and 2 targets
        double[] target = new double[2];
        target[0] = exit1.xCoord();
        target[1] = exit1.yCoord() + exit1.length() / 2;
        this.agents[0].setTarget(target);
        this.agents[1].setTarget(target);
        
        double[] target2 = new double[2];
        target2[0] = exit2.xCoord();
        target2[1] = exit2.yCoord() + exit2.length() / 2;
        this.agents[2].setTarget(target2);
        this.agents[3].setTarget(target2);

        // radius of a person : door depth = 1:6.04
        // update agents' radii based on the proportions
        this.agents[0].updateR(exit1.length() / 6.04);
        this.agents[1].updateR(exit1.length() / 6.04);
        this.agents[2].updateR(exit2.length() / 6.04);
        this.agents[3].updateR(exit2.length() / 6.04);

    }


    /**
     * Runs the room evacuation.
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
        // first room
        double xCoord = 25;
        double yCoord = 25;
        double width = ((this.depth - 80) * 1.2647)/3;
        double height = (this.depth - 97)/3;
        
        Rectangle2D room = new Rectangle2D.Double(xCoord, // Center the room
            yCoord, width, height);
        Obstacle leftWall = new Obstacle(xCoord - THICKNESS, yCoord - THICKNESS,
            THICKNESS, height + 2 * THICKNESS);
        Obstacle topWall = new Obstacle(xCoord, yCoord - THICKNESS, width
            + THICKNESS, THICKNESS);
        Obstacle bottomWall = new Obstacle(xCoord, yCoord + height, width
            + THICKNESS, THICKNESS);
        Obstacle rightWall_1 = new Obstacle(xCoord + width, yCoord, THICKNESS,
            room.getY() + room.getWidth() / 18.889 - yCoord);
        Obstacle rightWall_2 = new Obstacle(xCoord + width, room.getY() + room
            .getWidth() / 18.889 + room.getHeight() / 7.5055, THICKNESS, height
                - ((room.getY() + room.getWidth() / 18.889 - yCoord) + (room
                    .getHeight() / 7.5055)));
        graphics.setPaint(Color.BLACK);
        graphics.draw(room);
        graphics.fill(leftWall.wall());
        graphics.fill(topWall.wall());
        graphics.fill(bottomWall.wall());
        graphics.fill(rightWall_1.wall());
        graphics.fill(rightWall_2.wall());

        // Door width:room width = 1:200
        // Door depth:room depth = 1:7.5055
        // Distance from corner to the edge of the door:room depth = 1:18.889
        // exit
        Exit exit1 = new Exit(room.getWidth() + ((this.width - (this.depth - 80)
            * 1.2647) / 2 - room.getWidth() / 200 / 2)-THICKNESS, room.getY() + room
                .getWidth() / 18.889, room.getWidth() / 200, room.getHeight()
                    / 7.5055);
        graphics.setPaint(Color.GREEN);
        graphics.fill(exit1.door());
        
        
        //Room number 2
        double xCoord2 = ((this.width - (this.depth - 80) * 1.2647))*5 ;
        double yCoord2 = 25;
        double width2 = ((this.depth - 80) * 1.2647)/3;
        double height2 = (this.depth - 97)/3;
        Rectangle2D room2 = new Rectangle2D.Double(xCoord2+THICKNESS, // Center the room
            yCoord2, width2, height2);
        Obstacle rightWall2 = new Obstacle(xCoord2 + width2, yCoord2 - THICKNESS,
            THICKNESS, height2 + 2 * THICKNESS);
        Obstacle topWall2 = new Obstacle(xCoord2, yCoord2 - THICKNESS, width2
            + THICKNESS, THICKNESS); 
        Obstacle bottomWall2 = new Obstacle(xCoord2, yCoord2 + height2, width2
            + THICKNESS, THICKNESS);
        Obstacle leftWall_12 = new Obstacle(xCoord2, yCoord2, THICKNESS,
            room2.getY() + room2.getWidth() / 18.889 - yCoord2);
        Obstacle leftWall_22 = new Obstacle(xCoord2, room2.getY() + room2
            .getWidth() / 18.889 + room2.getHeight() / 7.5055, THICKNESS, height2
                - ((room2.getY() + room2.getWidth() / 18.889 - yCoord2) + (room2
                    .getHeight() / 7.5055)));
        
        graphics.setPaint(Color.BLACK);
        graphics.draw(room2);
        graphics.fill(rightWall2.wall());
        graphics.fill(topWall2.wall());
        graphics.fill(bottomWall2.wall());
        graphics.fill(leftWall_12.wall());
        graphics.fill(leftWall_22.wall());

        // Door width:room width = 1:200
        // Door depth:room depth = 1:7.5055
        // Distance from corner to the edge of the door:room depth = 1:18.889
        // exit
        Exit exit2 = new Exit(room2.getX(), room2.getY() + room2
                .getWidth() / 18.889, room2.getWidth() / 200, room2.getHeight()
                    / 7.5055);
        graphics.setPaint(Color.GREEN);
        graphics.fill(exit2.door());


              

        graphics.setPaint(Color.BLUE);
        graphics.fill(this.agents[0].person());
        graphics.fill(this.agents[1].person());
        graphics.setPaint(Color.RED);
        graphics.fill(this.agents[2].person());
        graphics.fill(this.agents[3].person());
        
                
        //hallway and main exit
        Exit mainExit = new Exit(xCoord2 - 100, 600.0+THICKNESS,room2.getHeight()
                / 7.5055,room2.getWidth() / 200);
        
        graphics.setPaint(Color.GREEN);
        graphics.fill(mainExit.door());
        
        
        graphics.setPaint(Color.BLACK);
        Obstacle botHallWall_1 = new Obstacle((int)xCoord-THICKNESS, 600, (int)mainExit.xCoord()-xCoord+THICKNESS,THICKNESS);
        Obstacle botHallWall_2 = new Obstacle((int)xCoord2-100+(int)(room2.getHeight()/ 7.5055), 600, 95, THICKNESS);
        Obstacle leftHallWall_1 = new Obstacle((int)xCoord-THICKNESS, (int)height+2*(int)THICKNESS,
        		THICKNESS, 600-(int)height-50);
        Obstacle rightHallWall_2 = new Obstacle((int)xCoord2, (int)height+2*(int)THICKNESS, THICKNESS, 600-(int)height-50);
        
        graphics.fill(botHallWall_1.wall());
        graphics.fill(botHallWall_2.wall());
        graphics.fill(leftHallWall_1.wall());
        graphics.fill(rightHallWall_2.wall());
        
        double[] hallTarget = new double[2];
        hallTarget[0] = mainExit.xCoord()+15;
        hallTarget[1] = mainExit.yCoord();   
        
        this.agents[0].setExit(hallTarget);
        this.agents[1].setExit(hallTarget);
        this.agents[2].setExit(hallTarget);
        this.agents[3].setExit(hallTarget);    

        
        
        
    }

}
