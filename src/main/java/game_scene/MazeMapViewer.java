package game_scene;

import game_states.Location;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The user view of the maze map
 */
public class MazeMapViewer extends JPanel {

    /**The side length of the map*/
    public static final int SIDE_LENGTH = 30;

    /**The controller of this maze map view*/
    private MazeMapController controller;

    /**
     * The constructor of this class
     *
     * @param MazeMap The underlying maze map data that this class will display
     * @param entry The entry location of the maze map
     * @param exit The exit location of the maze map
     * */
    public MazeMapViewer(ArrayList<ArrayList<Integer>> MazeMap, Location entry, Location exit){

        controller = new MazeMapController(MazeMap, entry, exit);
        HashMap<Location, VertexController> location2vertexController = controller.getLocationVertexControllerMap();

        setLayout(new GridLayout(SIDE_LENGTH+1,SIDE_LENGTH+2));
        add(new JLabel(""));
        for (int col = 1; col <= SIDE_LENGTH; col++) { //uppermost row, for the col-axis labels
            add(createAxisLabel(String.valueOf(col)));
        }
        add(new JLabel(""));

        for(int i = 1;i<=SIDE_LENGTH;i++){
            if(entry.row()==i-1){
                add(createAxisLabel("ENTRY"));
            }else{
                add(createAxisLabel(String.valueOf(i)));
            }

            for(int j=1;j<=SIDE_LENGTH;j++){
                add(location2vertexController.get(new Location(i-1,j-1)).getVertex());
            }

            if(exit.row()==i-1){
                add(createAxisLabel("EXIT"));
            }else{
                add(createAxisLabel(String.valueOf(i)));
            }
        }
    }

    /**
     * Helper function for displaying a text
     *
     * @param item: the text to be displayed
     * */
    public JLabel createAxisLabel(String item){
        JLabel label = new JLabel(item);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setPreferredSize(new Dimension(50, 50));
        label.setFont(new Font("Arial", Font.BOLD, 7));
        return label;
    }


    /**
     * Accessor function
     *
     * @return the controller of this maze map view
     */
    public MazeMapController getController(){return controller;}
}
