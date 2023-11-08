package game_scene;
import javax.swing.*;
import java.awt.*;
public class VertexViewer extends JPanel {

    private static final long serialVersionUID = 1L;

    public VertexViewer(Color d){
        this.setBackground(d);
    }


    public void ChangeColor(Color d){
        this.setBackground(d);
    }

}