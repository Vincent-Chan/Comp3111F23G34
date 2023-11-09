package game_scene;

import game_states.Location;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

//https://stackoverflow.com/questions/51746451/how-do-i-make-a-jpanel-grid

public class MazeMapViewer extends JPanel {
    public static final int SIDE_LENGTH = 30;
    private MazeMapController controller;
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

    // for marking the row and column indices
    private JLabel createAxisLabel(String item){
        JLabel label = new JLabel(item);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setPreferredSize(new Dimension(50, 50));
        label.setFont(new Font("Arial", Font.BOLD, 7));
        return label;
    }

    public MazeMapController getController(){return controller;}
}
