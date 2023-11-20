package game_scene;

import game_states.Move;
import game_states.MoveCode;
import org.junit.Test;
import visuals.StringResources;

import javax.swing.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.jupiter.api.Assertions.*;
public class ControlPanelTest {

    @Test
    public void testControlPanelViewConstructor() throws IOException {
        ControlPanelView cpv = new ControlPanelView();//target
    }

    @Test
    public void testgetControlPanelController() throws IOException {
        ControlPanelView cpv = new ControlPanelView();
        ControlPanelController ctrler = cpv.getControlPanelController(); //target
        assertNotNull(ctrler);
    }

    @Test
    public void testControlPanelControllerConstructor(){
        HashMap<MoveCode, JButton> move2Button = generateMove2ButtonMap();
        ControlPanelController ctrler = new ControlPanelController(move2Button); //target
        assertNotNull(ctrler);
    }
    @Test
    public void testgetControlPanelControllerActionQueue(){
        HashMap<MoveCode, JButton> move2Button = generateMove2ButtonMap();
        ControlPanelController ctrler = new ControlPanelController(move2Button);
        LinkedBlockingQueue<Move> queue = ctrler.getActionQueue();//target
        assertNotNull(queue);
    }

    @Test
    public void testControlPanelProcessButtonPressing() throws InterruptedException {
        HashMap<MoveCode, JButton> move2Button = generateMove2ButtonMap();
        ControlPanelController ctrler = new ControlPanelController(move2Button);
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
    public void testShowRemainingMoves(){
        StringResources.showRemainingMoves(0,2); //target
        StringResources.showRemainingMoves(1,3); //target
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
