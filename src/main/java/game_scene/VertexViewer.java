package game_scene;
import javax.swing.*;
import java.awt.*;

/**
 * The user view of a vertex on the map
 */
public class VertexViewer extends JPanel {

    /**
     * Constructor of this class, constructed with a default color
     * @param d the default color this view should display
     */
    public VertexViewer(Color d){
        this.setBackground(d);
    }

    /**
     * Change the color of this vertex view
     *
     * @param d  the color to change to
     * */
    public void ChangeColor(Color d){
        this.setBackground(d);
    }

}