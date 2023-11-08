package game_scene;
import game_states.Location;
import visuals.StringResources;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class WindowsView extends JFrame {
    private MazeMapViewer mapViewer;
    private ControlPanelView controlPanelView;
    private JLabel textBillboard;
    public WindowsView(ArrayList<ArrayList<Integer>> MazeMap, Location entry, Location exit)  {
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //getContentPane().setSize(500,500);
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
        getContentPane().add(new ControlPanelView(), BorderLayout.WEST);
        pack();

        int width = controlPanelView.getPreferredSize().width + mapViewer.getPreferredSize().width + 20;
        int height = Math.max(controlPanelView.getPreferredSize().height, mapViewer.getPreferredSize().height) + 20;
        setSize(width, height);

        setVisible(true);
    }

    public void setTextBillboard(String text){
        textBillboard.setText(text);
        this.revalidate();
        this.repaint();
    }

    public MazeMapViewer getMapViewer(){return mapViewer;}
    public ControlPanelView getControlPanelView(){return controlPanelView;}
}