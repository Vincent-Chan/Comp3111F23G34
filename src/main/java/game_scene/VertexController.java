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
import java.util.HashMap;

/**
 * The controller of a vertex on the map
 */
public class VertexController {

    /**The hashmap storing the string message for each color*/
    static final HashMap<String, Color> COLOR_MAP;

    /**A GUI container for displaying the image on the vertex*/
    private JLabel picLabel;

    // initialized only once before the first instance of VertexController is initialized
    static {
        COLOR_MAP = new HashMap<>();
        COLOR_MAP.put(StringResources.barrier, Color.DARK_GRAY);
        COLOR_MAP.put(StringResources.clear_vertex, Color.WHITE);
        COLOR_MAP.put(StringResources.sp_component,Color.GREEN);
        COLOR_MAP.put(StringResources.tom_reachable_location, Color.ORANGE);
        COLOR_MAP.put(StringResources.SP_union_tom_reachable_location, Color.RED);
    }

    /**The vertex viewer that this class controls*/
    private VertexViewer vertex;

    /**The location of the vertex that this class controls*/
    private Location location;


    /**
     * Initialize with a predefined vertex type, (x,y) coordinates
     *
     * @param vertexType  one of {StringResources.barrier, StringResources.clear_vertex, StringResources.sp_component}
     *                    indicates whether the current vertex is a barrier, clear vertex, or highlighted clear vertex
     * @param x,y the location coordinates of the current vertex
     * */
    public VertexController(String vertexType, int x, int y){
        this.vertex = new VertexViewer(COLOR_MAP.get(vertexType));
        this.location = new Location(x,y);
    }

    /**
     * Change the vertex type on fly as needed, used when highlighting vertices on the shortest path
     *
     * @param newVertexType the new vertex type
     */
    public void changeVertexColor(String newVertexType){
        this.vertex.ChangeColor(COLOR_MAP.get(newVertexType));
    }

    /**
     * Accessor method
     *
     * @return the vertex view that this instance controls
     * */
    public VertexViewer getVertex(){return vertex;}


    /**
     * Insert an image onto the vertex controlled, e.g. the images of Tom and Jerry are inserted on the vertices they reside on
     *
     * @param filepath  the path of the image
     *
     * @throws IOException if the image path is not valid
     * */
    public void insertImage(String filepath) throws IOException {
        BufferedImage myPicture = ImageIO.read(new File(filepath));
        Image scaledImage = myPicture.getScaledInstance(this.vertex.getWidth(),this.vertex.getHeight(),Image.SCALE_DEFAULT);
        picLabel = new JLabel(new ImageIcon(scaledImage));
        this.vertex.setLayout(new BorderLayout());
        this.vertex.add(picLabel, BorderLayout.CENTER);
        this.vertex.getRootPane().repaint();
        //this.vertex.repaint();
    }

    /**
     * Remove an image (if any) from the vertex controlled
     * */
    public void removeImage(){
        if(picLabel==null)
            return;
        this.vertex.remove(picLabel);
        picLabel = null;
        //this.vertex.revalidate();
        this.vertex.getRootPane().repaint();
        //this.vertex.repaint();
    }
}
