package game_scene;
import game_states.Move;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.LinkedBlockingQueue;

public class LandingPageController {
    private JButton startButton;
    private JFrame LandingPage;
    private static LinkedBlockingQueue<String> buttonHitRecords;

    public LandingPageController(JButton startButton, JFrame LandingPage){
        this.startButton = startButton;
        this.LandingPage = LandingPage;
        this.buttonHitRecords = new LinkedBlockingQueue<>();

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    buttonHitRecords.put("Button Clicked");// put message into queue, to be fetched by main program
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
    }

    public LinkedBlockingQueue<String> getButtonHitRecords(){return buttonHitRecords;}

}
