package game_scene;

import visuals.StringResources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LandingPageView extends JFrame {
    private JPanel startButtonPanel;
    private LandingPageController controller;
    private JPanel panel;
    private JButton startButton;

    private JLabel logo;
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

    public LandingPageController getController(){return controller;}



}
