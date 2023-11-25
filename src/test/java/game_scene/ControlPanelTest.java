package game_scene;

import game_states.Move;
import game_states.MoveCode;
import org.junit.Test;
import visuals.StringResources;

import javax.swing.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.jupiter.api.Assertions.*;
public class ControlPanelTest {

    @Test
    public void testControlPanelViewConstructor() throws IOException {
        ControlPanelView cpv = new ControlPanelView();//target function
    }

    @Test
    public void testgetControlPanelController() throws IOException {
        ControlPanelView cpv = new ControlPanelView();
        ControlPanelController ctrler = cpv.getControlPanelController(); //target function
        assertNotNull(ctrler);
    }

    @Test
    public void testControlPanelControllerConstructor(){
        HashMap<MoveCode, JButton> move2Button = generateMove2ButtonMap();
        ControlPanelController ctrler = new ControlPanelController(move2Button); //target function
        assertNotNull(ctrler);
    }
    @Test
    public void testgetControlPanelControllerActionQueue(){
        HashMap<MoveCode, JButton> move2Button = generateMove2ButtonMap();
        ControlPanelController ctrler = new ControlPanelController(move2Button);
        LinkedBlockingQueue<Move> queue = ctrler.getActionQueue();//target function
        assertNotNull(queue);
    }

    @Test
    public void testControlPanelProcessButtonPressing() throws InterruptedException {
        HashMap<MoveCode, JButton> move2Button = generateMove2ButtonMap();
        ControlPanelController ctrler = new ControlPanelController(move2Button); //target function
        LinkedBlockingQueue<Move> queue = ctrler.getActionQueue();
        JButton upbutton = move2Button.get(MoveCode.UP);
        upbutton.doClick();
        assertInstanceOf(Move.Up.class,queue.take());
        JButton downbutton = move2Button.get(MoveCode.DOWN);
        downbutton.doClick();
        assertInstanceOf(Move.Down.class,queue.take());
        JButton rightbutton = move2Button.get(MoveCode.RIGHT);
        rightbutton.doClick();
        assertInstanceOf(Move.Right.class,queue.take());
        JButton leftbutton = move2Button.get(MoveCode.LEFT);
        leftbutton.doClick();
        assertInstanceOf(Move.Left.class,queue.take());
    }

    @Test
    public void testActionListenerWithInterruptedException() {
        // Create a mocked HashMap with the button
        HashMap<MoveCode, JButton> mockedButtonMap = new HashMap<>();
        mockedButtonMap.put(MoveCode.UP, new JButton());
        mockedButtonMap.put(MoveCode.RIGHT, new JButton());
        mockedButtonMap.put(MoveCode.LEFT, new JButton());
        mockedButtonMap.put(MoveCode.DOWN, new JButton());

        // Create an instance of ControlPanelController with the mocked HashMap
        ControlPanelController controller = new ControlPanelController(mockedButtonMap); //target function

        // Create a blocking queue that throws InterruptedException on put()
        BlockingQueue<Move> mockedActionQueue = new LinkedBlockingQueue<Move>() {
            @Override
            public void put(Move move) throws InterruptedException {
                throw new InterruptedException();
            }
        };

        // Set the mocked action queue for the controller
        LinkedBlockingQueue<Move> originalqueue = null;
        if(ControlPanelController.actionQueue!=null){
            originalqueue = ControlPanelController.actionQueue;
        }
        ControlPanelController.actionQueue = (LinkedBlockingQueue<Move>) mockedActionQueue;

        // Trigger the action listener for one of the buttons and assert that it throws RuntimeException
        assertThrows(RuntimeException.class, () -> {
            controller.move2Button.get(MoveCode.UP).doClick();
        });

        if(originalqueue!=null)
            ControlPanelController.actionQueue = originalqueue;
        else
            ControlPanelController.actionQueue = new LinkedBlockingQueue<Move>();
    }
    private HashMap<MoveCode, JButton> generateMove2ButtonMap(){
        HashMap<MoveCode, JButton> move2Button = new HashMap<MoveCode, JButton>();
        JButton upButton = new JButton("↑");
        JButton rightButton = new JButton("→");
        JButton leftButton = new JButton("←");
        JButton downButton = new JButton("↓");
        move2Button.put(MoveCode.UP, upButton);
        move2Button.put(MoveCode.DOWN, downButton);
        move2Button.put(MoveCode.RIGHT, rightButton);
        move2Button.put(MoveCode.LEFT, leftButton);
        return move2Button;
    }
}
