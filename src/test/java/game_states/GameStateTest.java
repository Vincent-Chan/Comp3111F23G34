package game_states;

import game_entities.characterID;
import org.junit.Test;
import visuals.StringResources;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class GameStateTest {
    public static Location entry = new Location(12,0);
    public static Location exit = new Location(12,29);
    @Test
    public void test_constructorGameStateController(){
        ArrayList<ArrayList<Integer>> mzp = generateMazeMap(entry,exit);
        GameStateController gsc = new GameStateController(mzp,entry,exit); //target function
        assertInstanceOf(GameStateController.class, gsc);
    }

    @Test
    public void test_constructorMoveLeft(){
        Move left_move = new Move.Left(0); //target function
        assertInstanceOf(Move.Left.class, left_move);
    }
    @Test
    public void test_constructorMoveRight(){
        Move right_move = new Move.Right(0); //target function
        assertInstanceOf(Move.Right.class, right_move);
    }
    @Test
    public void test_constructorMoveUp(){
        Move up_move = new Move.Up(0); //target function
        assertInstanceOf(Move.Up.class, up_move);
    }
    @Test
    public void test_constructorMoveDown(){
        Move down_move = new Move.Down(0); //target function
        assertInstanceOf(Move.Down.class, down_move);
    }
    @Test
    public void test_nextPosition(){
        Location original = new Location(5,5);

        Location right = new Location(5,6);
        Location left = new Location(5,4);
        Location up = new Location(4,5);
        Location down = new Location(6,5);

        Move left_move = new Move.Left(0);
        Location real_left = left_move.nextPosition(original); //target function
        Move right_move = new Move.Right(0);
        Location real_right = right_move.nextPosition(original); //target function
        Move up_move = new Move.Up(0);
        Location real_up = up_move.nextPosition(original); //target function
        Move down_move = new Move.Down(0);
        Location real_down = down_move.nextPosition(original); //target function

        assertEquals(left,real_left);
        assertEquals(up,real_up);
        assertEquals(right,real_right);
        assertEquals(down,real_down);


    }
    @Test
    public void test_canMove() {
        ArrayList<ArrayList<Integer>> mzp = generateMazeMap(entry,exit);
        GameStateController gsc = new GameStateController(mzp,entry,exit);
        Move j_r= new Move.Right(characterID.JERRY_ID);
        Move t_r= new Move.Right(characterID.TOM_ID);
        boolean jcm = gsc.canMove(characterID.JERRY_ID,j_r); //target function
        boolean tcm = gsc.canMove(characterID.TOM_ID,t_r);
        assertEquals(true, jcm);
        assertEquals(false, tcm);
    }

    @Test
    public void test_moveCharacter() {
        ArrayList<ArrayList<Integer>> mzp = generateMazeMap(entry,exit);
        GameStateController gsc = new GameStateController(mzp,entry,exit);
        Move j_r= new Move.Right(characterID.JERRY_ID);
        Move t_r= new Move.Right(characterID.TOM_ID);
        Move t_l = new Move.Left(characterID.TOM_ID);

        boolean t_r_result = gsc.moveCharacter(characterID.TOM_ID, t_r); //target function
        assertEquals(false, t_r_result);

        gsc.moveCharacter(characterID.JERRY_ID,j_r); //target function
        Location expected = new Location(entry.row(), entry.col()+1);
        Location actual = gsc.getCharacterLocation(characterID.JERRY_ID);
        assertEquals(expected, actual);

        gsc.moveCharacter(characterID.TOM_ID,t_l); //target function
        gsc.moveCharacter(characterID.TOM_ID,t_r); //target function
        Location actual_tom = gsc.getCharacterLocation(characterID.TOM_ID);
        assertEquals(exit, actual_tom);


    }

    @Test
    public void test_getCharacterLocation() {
        ArrayList<ArrayList<Integer>> mzp = generateMazeMap(entry,exit);
        GameStateController gsc = new GameStateController(mzp,entry,exit);

        Move t_l = new Move.Left(characterID.TOM_ID);
        gsc.moveCharacter(characterID.TOM_ID,t_l);
        Location expected_tom = new Location(exit.row(), exit.col()-1);
        Location actual_tom = gsc.getCharacterLocation(characterID.TOM_ID); //target function
        assertEquals(expected_tom, actual_tom);

        Move j_r = new Move.Right(characterID.JERRY_ID);
        gsc.moveCharacter(characterID.JERRY_ID,j_r);
        Location expected_jerry = new Location(entry.row(), entry.col()+1);
        Location actual_jerry = gsc.getCharacterLocation(characterID.JERRY_ID);//target function
        assertEquals(expected_jerry, actual_jerry);
    }

    @Test
    public void test_gameStateOutcome() {
        ArrayList<ArrayList<Integer>> mzp = generateMazeMap(entry,exit);
        GameStateController gsc = new GameStateController(mzp,entry,exit);

        GameState real = gsc.gameStateOutcome(); //target function
        assertEquals(GameState.CONTINUE, real);

        Move t_l = new Move.Left(characterID.TOM_ID);
        gsc.moveCharacter(characterID.TOM_ID,t_l);
        Location jerry_location = gsc.getCharacterLocation(characterID.JERRY_ID);
        Location tom_location = gsc.getCharacterLocation(characterID.TOM_ID);
        System.out.println(jerry_location);
        System.out.println(tom_location);
        while(!jerry_location.equals(tom_location)){
            Move j_r = new Move.Right(characterID.JERRY_ID);
            gsc.moveCharacter(characterID.JERRY_ID,j_r);
            jerry_location = gsc.getCharacterLocation(characterID.JERRY_ID);
        }

        real = gsc.gameStateOutcome();//target
        assertEquals(GameState.TOM_WIN, real);


        Move j_r = new Move.Right(characterID.JERRY_ID);
        gsc.moveCharacter(characterID.JERRY_ID,j_r);
        real = gsc.gameStateOutcome();//target
        assertEquals(GameState.JERRY_WIN, real);

    }

    @Test
    public void test_reachableLocationByTom(){
        ArrayList<ArrayList<Integer>> mzp = generateMazeMap(entry,exit);
        GameStateController gsc = new GameStateController(mzp,entry,exit);
        Location tomlocation = gsc.getCharacterLocation(characterID.TOM_ID);
        Location jerrylocation = gsc.getCharacterLocation(characterID.JERRY_ID);
        ArrayList<Location> TomReachable = new ArrayList<>();
        TomReachable.add(tomlocation);
        ArrayList<Location> JerryReachable = new ArrayList<>();
        JerryReachable.add(jerrylocation);
        int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] direction : DIRECTIONS){
            if(gsc.location2vertex.containsKey(new Location(tomlocation.row()+direction[0], tomlocation.col()+direction[1])) && gsc.location2vertex.get(new Location(tomlocation.row()+direction[0], tomlocation.col()+direction[1])).isClear()){
                TomReachable.add(new Location(tomlocation.row()+direction[0], tomlocation.col()+direction[1]));
            }
            if(gsc.location2vertex.containsKey(new Location(jerrylocation.row()+direction[0], jerrylocation.col()+direction[1])) && gsc.location2vertex.get(new Location(jerrylocation.row()+direction[0], jerrylocation.col()+direction[1])).isClear()){
                JerryReachable.add(new Location(jerrylocation.row()+direction[0], jerrylocation.col()+direction[1]));
            }
        }
        ArrayList<Location> realTomReachable = gsc.reachablePositions(0,1); //target function
        ArrayList<Location> realJerryReachable = gsc.reachablePositions(1,1); //target function
        assertArrayEquals(TomReachable.toArray(), realTomReachable.toArray());
        assertArrayEquals(JerryReachable.toArray(), realJerryReachable.toArray());

    }

    private ArrayList<ArrayList<Integer>> generateMazeMap(Location entry, Location exit){
        ArrayList<ArrayList<Integer>> mazeMap = new ArrayList<ArrayList<Integer>>();
        for(int i = 0;i<30;i++){
            ArrayList<Integer> row = new ArrayList<>();
            for(int j = 0;j<30;j++){
                if(i== entry.row() || i==exit.row()){
                    row.add(0);
                }
                else{
                    row.add(1);
                }
            }
            mazeMap.add(row);
        }

        return mazeMap;
    }
}
