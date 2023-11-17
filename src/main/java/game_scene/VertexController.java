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

public class VertexController {
    static final HashMap<String, Color> COLOR_MAP;
    private JLabel picLabel;

    // initialized only once before the first instance of VertexController is initialized
    static {
        COLOR_MAP = new HashMap<>(); // Diamond operator requires Java 1.7+
        COLOR_MAP.put(StringResources.barrier, Color.DARK_GRAY);
        COLOR_MAP.put(StringResources.clear_vertex, Color.WHITE);
        COLOR_MAP.put(StringResources.sp_component,Color.GREEN);
    }

    private VertexViewer vertex;
    private Location location;
    //initialize with a predefined vertex type, (x,y) coordinates
    public VertexController(String vertexType, int x, int y){
        this.vertex = new VertexViewer(COLOR_MAP.get(vertexType));
        this.location = new Location(x,y);
    }

    //change the vertex type on fly as needed
    public void changeVertexColor(String newVertexType){
        this.vertex.ChangeColor(COLOR_MAP.get(newVertexType));
    }

    public VertexViewer getVertex(){return vertex;}

    public void insertImage(String filepath) throws IOException {
        BufferedImage myPicture = ImageIO.read(new File(filepath));
        Image scaledImage = myPicture.getScaledInstance(this.vertex.getWidth(),this.vertex.getHeight(),Image.SCALE_DEFAULT);
        picLabel = new JLabel(new ImageIcon(scaledImage));
        this.vertex.setLayout(new BorderLayout());
        this.vertex.add(picLabel, BorderLayout.CENTER);
        this.vertex.getRootPane().repaint();
        //this.vertex.repaint();
    }

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
