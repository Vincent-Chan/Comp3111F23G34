package game_scene;

import game_states.Move;
import game_states.MoveCode;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class ControlPanelController {
    private HashMap<MoveCode, JButton> move2Button;
    private static LinkedBlockingQueue<Move> actionQueue;
    public ControlPanelController(HashMap<MoveCode, JButton> move2Button){
        if(actionQueue==null){
            actionQueue = new LinkedBlockingQueue<>();
        }

        this.move2Button = move2Button;
        System.out.println("Hashcode of actionqueue upon initializing ControlPanelController"+actionQueue.hashCode());
        for(MoveCode move: MoveCode.values()){

            // Add ActionListener(s) to the buttons
            this.move2Button.get(move).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Move m = switch(move){
                        case UP -> new Move.Up(1);
                        case RIGHT -> new Move.Right(1);
                        case LEFT -> new Move.Left(1);
                        case DOWN -> new Move.Down(1);
                    };
                    System.out.println("Action received from button: "+m);
                    try {
                        actionQueue.put(m);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    System.out.print("Current actionQueue"+"("+actionQueue.hashCode()+")");
                    for(int i = 0;i< actionQueue.size();i++){
                        System.out.print(actionQueue.toArray()[i]+"->");
                    }
                    System.out.println();
                }


            });
        }

    }

    public LinkedBlockingQueue<Move> getActionQueue(){return actionQueue;}
}
