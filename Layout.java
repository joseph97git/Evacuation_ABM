import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.*;
import java.io.IOException;
import java.util.Random;
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

    // thickness of walls
    private static final double THICKNESS = 25;
    private Agent[] agents;
    private Obstacle[] walls;
    private Rectangle2D.Double bluePrint;
    private Exit exit;
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
     * @throws IOException 
     */
    public Layout(int width, int depth) throws IOException {
        this.width = width;
        this.depth = depth;
        this.timer = new Timer(5, this);
        
        // set block size of agents (e.g. 5 => 25 agent block)
        int blockSize = 6;
        this.agents = new Agent[blockSize * blockSize];
        
        this.walls = new Obstacle[5];

        double shiftOne = 0; // shift exit down
        int shiftH = 0; // shift agent block left right
        int shiftV = 0; // shift agent block up down
        // *** create agents loop ***
        int count = 0;
        for (int i = 100 + shiftH; i < (blockSize * 100)  
            + (100 + shiftH); i+=100) {
            for (int j = 100 + shiftV; j < (blockSize * 100) 
                + (100 + shiftV); j+=100) {
                // generate randomness
                int max = 10;
                int min = 1;
                Random rand = new Random();
                double randx = (rand.nextInt(max - min + 1) + min) 
                    * rand.nextDouble();
                double randy = (rand.nextInt(max - min + 1) + min)
                    * rand.nextDouble();
                int flip = rand.nextInt(3) - 1;
                // create position
                double[] pos = new double[2];
                pos[0] = (double)i + (randx) * flip;
                pos[1] = (double)j + (randy) * flip;
                // create velocity
                double[] vel = new double[2];
                vel[0] = 0.0;
                vel[1] = 0.0;
                Agent agent = new Agent(count, pos, 15, 80.0, 
                    vel, this.agents, this.walls);
                this.agents[count] = agent;
                count++;
            }
        }
        
        // *** set dimensions ***
        // classroom proportion width:depth = 1.2647:1
        // single room
        double xCoord = ((this.width - (this.depth - 80) * 1.2647)) / 2;
        double yCoord = 25;
        double widthRoom = (this.depth - 80) * 1.2647;
        double height = this.depth - 97;
        
        // *** create blue print ***
        Rectangle2D.Double room = new Rectangle2D.Double(xCoord, // Center the room
            yCoord, widthRoom, height);
        this.bluePrint = room;
        
        Obstacle leftWall = new Obstacle(xCoord - THICKNESS, yCoord - THICKNESS,
            THICKNESS, height + 2 * THICKNESS);
        Obstacle topWall = new Obstacle(xCoord, yCoord - THICKNESS, width
            + THICKNESS - 100, THICKNESS);
        Obstacle bottomWall = new Obstacle(xCoord, yCoord + height, width
            + THICKNESS - 100, THICKNESS);
        
        // *** shift right wall exit. ***
        Obstacle rightWall_1 = new Obstacle(xCoord + width - 100, yCoord, THICKNESS,
            room.getY() + room.getWidth() / 18.889 - yCoord + shiftOne);
        Obstacle rightWall_2 = new Obstacle(xCoord + width - 100, room.getY() + room
            .getWidth() / 18.889 + room.getHeight() / 7.5055 + shiftOne, THICKNESS, height
                - ((room.getY() + room.getWidth() / 18.889 - yCoord) + (room
                    .getHeight() / 7.5055)) - shiftOne);
        
        this.walls[0] = leftWall;
        this.walls[1] = topWall;
        this.walls[2] = bottomWall;
        this.walls[3] = rightWall_1;
        this.walls[4] = rightWall_2;
        
        // *** create exit ***
        // Door width:room width = 1:200
        // Door depth:room depth = 1:7.5055
        // Distance from corner to the edge of the door:room depth = 1:18.889
        this.exit = new Exit(room.getWidth() + (this.width - (this.depth - 80)
            * 1.2647) / 2 - room.getWidth() / 200 / 2, room.getY() + room
                .getWidth() / 18.889 + shiftOne, room.getWidth() / 200, room.getHeight()
                    / 7.5055);
        
        // *** set targets ***
        double[] target = new double[2];
        target[0] = this.exit.xCoord() + 25 + 200;
        target[1] = this.exit.yCoord() + this.exit.length() / 2;
        for (int q = 0; q < this.agents.length; q++) {
            this.agents[q].setTarget(target);
        }

        // *** scale agents ***
        // radius of a person : door depth = 1:6.04
        // update agents' radii based on the proportions
        for (int p = 0; p < this.agents.length; p++) {
            this.agents[p].updateR(this.exit.length() / 3.02);
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
            try {
                if (this.agents[i] != null) {
                    this.agents[i].updateVectors();
                }
            }
            catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        repaint();
    }


    /**
     * Creates the type of layout.
     * 
     * @param graphics
     */
    public void create(Graphics2D graphics) {
        
        // paint walls
        graphics.setPaint(Color.BLACK);
        graphics.draw(this.bluePrint);
        graphics.fill(this.walls[0].wall());
        graphics.fill(this.walls[1].wall());
        graphics.fill(this.walls[2].wall());
        graphics.fill(this.walls[3].wall());
        graphics.fill(this.walls[4].wall());
        
        // paint exit
        graphics.setPaint(Color.GREEN);
        graphics.fill(this.exit.door());

        // paint agents
        graphics.setPaint(Color.BLUE);
        for (int i = 0; i < this.agents.length; i++) {
            graphics.fill(this.agents[i].person());
        }
    }

}
