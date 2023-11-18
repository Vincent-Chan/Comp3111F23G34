package game_scene;

import org.junit.Test;

import javax.swing.*;

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
}