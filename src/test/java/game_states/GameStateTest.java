package game_states;

import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;

public class GameStateTest {

    @Test
    public void detectEvent1(){
        ArrayList<ArrayList<Integer>> mazeMap = new ArrayList<ArrayList<Integer>>();
        Location entry = new Location(25,1);
        Location exit = new Location(12,30);
        for(int i = 0;i<30;i++){
            ArrayList<Integer> row = new ArrayList<>();
            for(int j = 0;j<30;j++){
                if(i==12){
                    row.add(0);
                }
                else{
                    row.add(1);
                }
            }
            mazeMap.add(row);
        }

        GameStateController state = new GameStateController(mazeMap,entry,exit);

        Move down = new Move.Down(1);
        boolean expected = false;
        assertEquals(expected, state.canMove(1,down));

    }

    @Test
    public void detectEvent2(){
        ArrayList<ArrayList<Integer>> mazeMap = new ArrayList<ArrayList<Integer>>();
        Location entry = new Location(25,1);
        Location exit = new Location(12,30);
        for(int i = 0;i<30;i++){
            ArrayList<Integer> row = new ArrayList<>();
            for(int j = 0;j<30;j++){
                if(i==25){
                    row.add(0);
                }
                else{
                    row.add(1);
                }
            }
            mazeMap.add(row);
        }

        GameStateController state = new GameStateController(mazeMap,entry,exit);

        int initiator = 1;
        Move right = new Move.Right(initiator);
        Location expected = new Location(state.getCharacterLocation(initiator).row(),state.getCharacterLocation(initiator).col()+1);
        state.moveCharacter(initiator,right);
        System.out.println(state.canMove(initiator,right));
        assertEquals(expected, state.getCharacterLocation(initiator));
    }

}