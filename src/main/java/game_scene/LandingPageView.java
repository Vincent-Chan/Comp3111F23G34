package game_scene;

import visuals.StringResources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The viewer of the landing page which user sees first after starting the program
 */
public class LandingPageView extends JFrame {
    /**The panel for holding the start button*/
    private JPanel startButtonPanel;

    /**The controller of this landing page*/
    private LandingPageController controller;

    /**The panel for holding all GUI elements this class creates*/
    private JPanel panel;

    /**The start button for starting the game*/
    private JButton startButton;

    /**The logo will be displayed on the panel*/
    private JLabel logo;


    /**
     * The constructor of this class
     */
    public LandingPageView(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        //the panel layered above the landing page
        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        //set logo
        ImageIcon logoIcon = new ImageIcon(StringResources.logo_image); // Replace with your logo image path
        logo = new JLabel(logoIcon);
        panel.add(logo, BorderLayout.CENTER);

        //set start button
        startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.PLAIN, 24));
        this.controller = new LandingPageController(startButton, this);


        startButtonPanel = new JPanel();
        startButtonPanel.add(startButton);
        panel.add(startButtonPanel, BorderLayout.SOUTH);

        setContentPane(panel);

    }

    /**
     * @return the controller of this landing page
     * */
    public LandingPageController getController(){return controller;}



}
