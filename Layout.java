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
public class Layout extends JPanel implements ActionListener {

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
    public Layout(int width, int depth) {
        this.width = width;
        this.depth = depth;
        this.timer = new Timer(5, this);
        agents = new Agent[2];

        double[] v_init1 = new double[2];
        v_init1[0] = 0.0;
        v_init1[1] = 0.0;

        double[] v_init2 = new double[2];
        v_init2[0] = 0.0;
        v_init2[1] = 0.0;

        double[] pos1 = new double[2];
        pos1[0] = 300.0;
        pos1[1] = 300.0 - 100.0;

        double[] pos2 = new double[2];
        pos2[0] = 360.0;
        pos2[1] = 300.0 - 100.0;

        Agent agent1 = new Agent(0, pos1, 15, 80.0, v_init1, this.agents, null);
        Agent agent2 = new Agent(1, pos2, 15, 80.0, v_init2, this.agents, null);

        this.agents[0] = agent1;
        this.agents[1] = agent2;

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
        // single room
        double xCoord = ((this.width - (this.depth - 80) * 1.2647)) / 2;
        double yCoord = 25;
        double width = (this.depth - 80) * 1.2647;
        double height = this.depth - 97;
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
        Exit exit1 = new Exit(room.getWidth() + (this.width - (this.depth - 80)
            * 1.2647) / 2 - room.getWidth() / 200 / 2, room.getY() + room
                .getWidth() / 18.889, room.getWidth() / 200, room.getHeight()
                    / 7.5055);
        graphics.setPaint(Color.GREEN);
        graphics.fill(exit1.door());

        // two agents
        double[] target = new double[2];
        target[0] = exit1.xCoord();
        target[1] = exit1.yCoord() + exit1.length() / 2;
        this.agents[0].setTarget(target);
        this.agents[1].setTarget(target);

        // radius of a person : door depth = 1:6.04
        // update agents' radii based on the proportions
        this.agents[0].updateR(exit1.length() / 6.04);
        this.agents[1].updateR(exit1.length() / 6.04);

        graphics.setPaint(Color.BLUE);
        graphics.fill(this.agents[0].person());
        graphics.fill(this.agents[1].person());
    }

}
