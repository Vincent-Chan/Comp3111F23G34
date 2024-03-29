package game_scene;
import game_states.Move;
import game_states.MoveCode;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 *The user view of the control panel
 */
public class ControlPanelView extends JPanel {
    /**The four buttons that control movements to four directions*/
    private JButton upButton, rightButton, leftButton, downButton;

    /**The controller of this user view, to be constructed later*/
    private ControlPanelController controller;


    /**
     * Constructor of this class, also constructs the controller after creating all buttons
     */
    public ControlPanelView() throws IOException {
        setLayout(new GridBagLayout()); // Set the layout to GridBagLayout

        // Create the buttons
        upButton = new JButton("↑");
        rightButton = new JButton("→");
        leftButton = new JButton("←");
        downButton = new JButton("↓");

        HashMap<MoveCode, JButton> move2Button = new HashMap<>();
        move2Button.put(MoveCode.UP, upButton);
        move2Button.put(MoveCode.DOWN, downButton);
        move2Button.put(MoveCode.RIGHT, rightButton);
        move2Button.put(MoveCode.LEFT, leftButton);
        controller = new ControlPanelController(move2Button);

        // Set the font size of the buttons
        Font buttonFont = new Font("Arial", Font.BOLD, 12);
        upButton.setFont(buttonFont);
        rightButton.setFont(buttonFont);
        leftButton.setFont(buttonFont);
        downButton.setFont(buttonFont);

        // Set the preferred size of the buttons
        Dimension buttonSize = new Dimension(25, 25);
        upButton.setPreferredSize(buttonSize);
        rightButton.setPreferredSize(buttonSize);
        leftButton.setPreferredSize(buttonSize);
        downButton.setPreferredSize(buttonSize);

        // Create GridBagConstraints for positioning the buttons
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;

        // Add the buttons to the panel
        c.gridx = 1;
        c.gridy = 0;
        add(upButton, c);

        c.gridx = 2;
        c.gridy = 1;
        add(rightButton, c);

        c.gridx = 0;
        c.gridy = 1;
        add(leftButton, c);

        c.gridx = 1;
        c.gridy = 2;
        add(downButton, c);

        // Create a label to explain what the buttons do
        StringBuilder sb = new StringBuilder(128);
        sb.append("<html>")
            .append("<p>Press the above buttons to control Jerry's movement</p>")
            .append("<p><strong>Hints</strong>:</p>")
            .append("<ul>")
                    .append("<li><span style=\"color: rgb(97, 189, 109);\"><strong>GREEN</strong></span> blocks show the shortest path to exit</li>")
                    .append("<li><span style=\"color: rgb(250, 197, 28);\"><strong>YELLOW</strong></span> blocks show reachable locations by Tom</li>")
                    .append("<li><span style=\"color: rgb(184, 49, 47);\"><strong>RED</strong></span> blocks are the intersection of previous two</li>")
                    .append("</ul>").append("</html>");

        /**sb.append("<html><br>").
         append("Use arrow keys to move").append("<br>").
         append("GREEN blocks show the shortest path to exit").append("<br>").
         append("ORANGE blocks show reachable locations by Tom").append("<br>").
         append("RED blocks are the intersection of previous two").append("</html>");*/
        //JLabel label = new JLabel("Use arrow keys to move \n GREEN blocks show the shortest path to exit \n ORANGE blocks show reachable locations by Tom \n RED blocks are the intersection of previous two", SwingConstants.CENTER);
        JLabel label = new JLabel(sb.toString());
        label.setFont(new Font("Arial", Font.PLAIN, 18));
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 3;
        //BufferedImage bannerpic = ImageIO.read(new File("src/main/java/visuals/banner_hints.png"));
        //JLabel label = new JLabel(new ImageIcon(bannerpic), SwingConstants.CENTER);
        add(label, c);
    }

    /**
     * @return the controller of this view
     */
    public ControlPanelController getControlPanelController(){return controller;}
}
