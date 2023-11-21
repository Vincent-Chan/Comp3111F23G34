package game_scene;

import game_states.Move;
import game_states.MoveCode;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * The controller for the control panel, which contains the keys the player can access
 */
public class ControlPanelController {

    /**The hashmap storing the respective JButton instance of each possible move*/
    public HashMap<MoveCode, JButton> move2Button;

    /**The FIFO blocking buffer for the actions submitted by the user via pressing the buttons*/
    public static LinkedBlockingQueue<Move> actionQueue;


    /**
     * Constructor for this class
     * @param move2Button It is the user's responsibility to pass a well-defined [move->button] table when calling the constructor
     */
    public ControlPanelController(HashMap<MoveCode, JButton> move2Button){
        if(actionQueue==null){
            actionQueue = new LinkedBlockingQueue<>();
        }

        this.move2Button = move2Button;
        for(MoveCode move: MoveCode.values()){
            this.move2Button.get(move).addActionListener(e-> {
                Move m = switch(move){
                    case UP -> new Move.Up(1);
                    case RIGHT -> new Move.Right(1);
                    case LEFT -> new Move.Left(1);
                    case DOWN -> new Move.Down(1);
                };
                try {
                    actionQueue.put(m);
                } catch (InterruptedException ex) {
                    throw new RuntimeException();
                }
            });
        }

    }

    /**
     * @return the FIFO blocking queue that stores all movements of Jerry made by user
     */
    public LinkedBlockingQueue<Move> getActionQueue(){return actionQueue;}
}
