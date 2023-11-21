package game_scene;

import game_states.Move;
import game_states.MoveCode;
import org.junit.Test;

import javax.swing.*;

import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.jupiter.api.Assertions.*;

public class LandingPageTest {
    @Test
    public void test_constructorLandingPageView() {
        LandingPageView lpv = new LandingPageView(); //target
        assertInstanceOf(LandingPageView.class, lpv);
    }
    @Test
    public void test_getController() {
        LandingPageView lpv = new LandingPageView();
        LandingPageController lpc = lpv.getController(); //target
        assertInstanceOf(LandingPageController.class, lpc);
    }
    @Test
    public void test_constructorLandingPageController() {
        JButton start = new JButton();
        JFrame frame = new JFrame();
        LandingPageController lpc = new LandingPageController(start, frame);//target
        assertInstanceOf(LandingPageController.class, lpc);
    }

    @Test
    public void test_hitButton(){
        JButton start = new JButton();
        JFrame frame = new JFrame();
        LandingPageController lpc = new LandingPageController(start, frame);
        start.doClick();
    }
    @Test
    public void test_getButtonHitRecords() throws InterruptedException {
        JButton start = new JButton();
        JFrame frame = new JFrame();
        LandingPageController lpc = new LandingPageController(start, frame);
        start.doClick();
        LinkedBlockingQueue<String> q = lpc.getButtonHitRecords(); //target
        String s = q.take();
        assertEquals("Button Clicked", s);
    }

    @Test
    public void testActionListenerWithInterruptedException() {
        // Create a mocked start button
        JButton mockedStartButton = new JButton();

        // Create a mocked JFrame
        JFrame mockedLandingPage = new JFrame();

        // Create an instance of LandingPageController with the mocked start button and JFrame
        LandingPageController controller = new LandingPageController(mockedStartButton, mockedLandingPage);

        // Create a blocking queue that throws InterruptedException on put()
        BlockingQueue<String> mockedButtonHitRecords = new LinkedBlockingQueue<String>() {
            @Override
            public void put(String s) throws InterruptedException {
                throw new InterruptedException();
            }
        };

        // Set the mocked button hit records for the controller
        LinkedBlockingQueue<String> originalButtonHitRecords = null;
        if(LandingPageController.buttonHitRecords!=null)
            originalButtonHitRecords = LandingPageController.buttonHitRecords;

        LandingPageController.buttonHitRecords= (LinkedBlockingQueue<String>) mockedButtonHitRecords;

        // Trigger the action listener for the start button and assert that it throws RuntimeException
        assertThrows(RuntimeException.class, () -> {
            mockedStartButton.doClick();
        });

        if(originalButtonHitRecords!=null){
            LandingPageController.buttonHitRecords = originalButtonHitRecords;
        }
        else{
            LandingPageController.buttonHitRecords = new LinkedBlockingQueue<String>();
        }

    }
}