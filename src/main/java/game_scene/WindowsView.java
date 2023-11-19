package game_scene;
import game_states.Location;
import visuals.StringResources;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The user view of the Windows that is invoked after the user clicks "Start Game" on the Landing Page
 */
public class WindowsView extends JFrame {

    /**
     * The user view of the maze map, which will be put on this WindowsView instance
     */
    private MazeMapViewer mapViewer;

    /**
     * The control panel view, which will be put on this WindowsView instance
     */
    private ControlPanelView controlPanelView;

    /**
     * The textual reminder of the remaining moves available for the player
     * */
    private JLabel textBillboard;

    /**
     * Constructor of this class
     *
     * @param MazeMap the maze map used to build the mapViewer
     * @param entry the entry location of the maze map
     * @param exit the exit location of the maze map
     * */
    public WindowsView(ArrayList<ArrayList<Integer>> MazeMap, Location entry, Location exit) throws IOException {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setName("Tom vs. Jerry");

        setLayout(new BorderLayout());

        this.textBillboard = new JLabel(StringResources.gamestarts);
        textBillboard.setFont(new Font("Serif", Font.PLAIN, 28));
        textBillboard.setHorizontalAlignment(SwingConstants.LEFT);
        textBillboard.setVerticalAlignment(SwingConstants.BOTTOM);
        add(textBillboard, BorderLayout.SOUTH);

        this.mapViewer = new MazeMapViewer(MazeMap, entry, exit);
        getContentPane().add(mapViewer, BorderLayout.CENTER);//add underlaying maze map

        this.controlPanelView = new ControlPanelView();
        getContentPane().add(this.controlPanelView, BorderLayout.WEST);
        pack();

        int width = controlPanelView.getPreferredSize().width + mapViewer.getPreferredSize().width + 50;
        int height = Math.max(controlPanelView.getPreferredSize().height, mapViewer.getPreferredSize().height) + 40;
        setSize(width, height);

    }

    /**
     * Change the textual reminder to display
     *
     * @param text the text to display
     * */
    public void setTextBillboard(String text){
        textBillboard.setText(text);
        this.revalidate();
        this.repaint();
    }

    /**
     * Accessor method for mapViewer
     *
     * @return the maze map view contained in the current window
     * */
    public MazeMapViewer getMapViewer(){return mapViewer;}

    /**
     * Accessor method for controlPanelView
     *
     * @return the control panel view contained in the current window
     * */
    public ControlPanelView getControlPanelView(){return controlPanelView;}
}