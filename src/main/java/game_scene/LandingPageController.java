package game_scene;
import game_states.Move;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * The controller of the landing page the user sees first when the program starts
 */
public class LandingPageController {
    /**The button for starting the game*/
    private JButton startButton;

    /**The landing page view this controller controls*/
    private JFrame LandingPage;

    /**The FIFO blocking buffer for storing the users' button pressing movements*/
    public static LinkedBlockingQueue<String> buttonHitRecords;

    /**
     * Constructor of this class
     * @param startButton this class will assign action listener for ths startButton
     * @param LandingPage the landing page this class controls
     */
    public LandingPageController(JButton startButton, JFrame LandingPage){
        this.startButton = startButton;
        this.LandingPage = LandingPage;
        this.buttonHitRecords = new LinkedBlockingQueue<>();

        startButton.addActionListener(e -> {
            try {
                buttonHitRecords.put("Button Clicked");
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    /**
     * @return the FIFO blocking buffer for storing the users' button pressing movements
     */
    public LinkedBlockingQueue<String> getButtonHitRecords(){return buttonHitRecords;}

}
