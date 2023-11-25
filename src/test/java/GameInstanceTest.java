import game_algorithm.GameMapGenerator;
import game_entities.Vertex;
import game_scene.LandingPageView;
import game_scene.VertexController;
import game_states.GameState;
import game_states.GameStateController;
import game_states.Location;
import game_states.Move;
import org.junit.Before;
import org.junit.Test;
import visuals.StringResources;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.jupiter.api.Assertions.*;
public class GameInstanceTest {
    public static final String MAP_FILE_PATH = "src/main/java/MazeData_testing.csv";
    public static final String SP_OUTPUT_PATH = "src/main/java/shortest_path_at_beginning_testing.csv";

    @Test
    public void test_GameInstanceConstructor() throws IOException {
        TomJerryGame tjg = new TomJerryGame(MAP_FILE_PATH, SP_OUTPUT_PATH); //target function
        assertEquals(30,tjg.mazeMap.size());
    }

    @Test
    public void test_readData_invalidmap() throws IOException {
        TomJerryGame tjg = new TomJerryGame(MAP_FILE_PATH, SP_OUTPUT_PATH);
        ArrayList<ArrayList<Integer>> mazeMap = tjg.readData("", ","); //target function
        assertNull(mazeMap);
    }

    @Test
    public void test_selectDifficulty() throws IOException {
        TomJerryGame tjg = new TomJerryGame(MAP_FILE_PATH, SP_OUTPUT_PATH);
        String[] difficultyModes = {"Easy", "Medium", "Hard","xxx"};
        for(int i = 0;i<difficultyModes.length;i++){
            String mode = difficultyModes[i];
            tjg.setDifficulty(true,mode); //target function
        }
    }

    @Test
    public void test_TomMovesOneStep() throws IOException {
        TomJerryGame tjg = new TomJerryGame(MAP_FILE_PATH, SP_OUTPUT_PATH);
        String[] directions = {"up", "down", "right","left"};
        for(int i = 0;i<directions.length;i++){
            String dir = directions[i];
            tjg.TomMovesOneStep(6,true,dir); //target function
        }
    }

    @Test
    public void test_showHints() throws IOException {
        TomJerryGame tjg = new TomJerryGame(MAP_FILE_PATH, SP_OUTPUT_PATH);
        tjg.showHintsOnMap(true,true); //target function
    }

    @Test
    public void test_TomMoves() throws IOException{
        /**Test condition check after move*/
        TomJerryGame tjg = new TomJerryGame(MAP_FILE_PATH, SP_OUTPUT_PATH);
        GameStateController sc = tjg.stateController;
        Location jerry = sc.JerryLocation;
        HashMap<Location, Vertex> l2vc = (HashMap<Location, Vertex>) sc.location2vertex;
        Location rightOfJerry = new Location(jerry.row(),jerry.col()+1);
        Location leftOfJerry = new Location(jerry.row(),jerry.col()-1);
        Location upOfJerry = new Location(jerry.row()-1,jerry.col());
        Location downOfJerry = new Location(jerry.row()+1,jerry.col());

        if(l2vc.containsKey(rightOfJerry)&&l2vc.get(rightOfJerry).isClear())
            sc.TomLocation = rightOfJerry;
        else if(l2vc.containsKey(leftOfJerry)&&l2vc.get(leftOfJerry).isClear())
            sc.TomLocation = leftOfJerry;
        if(l2vc.containsKey(upOfJerry)&&l2vc.get(upOfJerry).isClear())
            sc.TomLocation = upOfJerry;
        if(l2vc.containsKey(downOfJerry)&&l2vc.get(downOfJerry).isClear())
            sc.TomLocation = downOfJerry;

        tjg.TomMoves(); //target funtion
        assertEquals(GameState.TOM_WIN,sc.gameStateOutcome());

        /**Test condition check before making any move*/
        tjg = new TomJerryGame(MAP_FILE_PATH, SP_OUTPUT_PATH);
        sc = tjg.stateController;
        sc.TomLocation = sc.JerryLocation;
        tjg.TomMoves(); //target function
        assertEquals(GameState.TOM_WIN,sc.gameStateOutcome());


    }

    @Test
    public void test_JerryMoves() throws IOException, InterruptedException, UnsupportedAudioFileException, LineUnavailableException {

       /**Test Invalid Move + 2 valid moves*/
        TomJerryGame tjg = new TomJerryGame(MAP_FILE_PATH, SP_OUTPUT_PATH);
        GameStateController sc = tjg.stateController;
        Location jerry = sc.JerryLocation;
        HashMap<Location, Vertex> l2vc = (HashMap<Location, Vertex>) sc.location2vertex;
        ArrayList<Location> pathOfJerry = tjg.shortestPathGenerator.calculate_shortest_path(tjg.entry,tjg.exit);
        Location l1 = pathOfJerry.get(1);
        Location l2 = pathOfJerry.get(2);
        sc.TomLocation = l2;

        LinkedBlockingQueue<Move> submitted_move = new LinkedBlockingQueue<>();
        submitted_move.put(new Move.Left(1)); //invalid move
        if(l1.row()==jerry.row()){
            if(l1.col()==jerry.col()+1){
                submitted_move.put(new Move.Right(1));
            }
            else{
                submitted_move.put(new Move.Left(1));
            }
        }
        else{
            if(l1.row()==jerry.row()+1){
                submitted_move.put(new Move.Down(1));
            }
            else{
                submitted_move.put(new Move.Up(1));
            }
        }

        if(l2.row()==l1.row()){
            if(l2.col()==l1.col()+1){
                submitted_move.put(new Move.Right(1));
            }
            else{
                submitted_move.put(new Move.Left(1));
            }
        }
        else{
            if(l2.row()==l1.row()+1){
                submitted_move.put(new Move.Down(1));
            }
            else{
                submitted_move.put(new Move.Up(1));
            }
        }
        tjg.jerrySpeed = 2;
        tjg.JerryMoves(true,true,submitted_move);//target function
        assertEquals(GameState.TOM_WIN,sc.gameStateOutcome());

        /**Test exhausted remaining moves*/
        tjg = new TomJerryGame(MAP_FILE_PATH, SP_OUTPUT_PATH);
        sc = tjg.stateController;
        jerry = sc.JerryLocation;
        l2vc = (HashMap<Location, Vertex>) sc.location2vertex;
        Location rightOfJerry = new Location(jerry.row(),jerry.col()+1);
        Location leftOfJerry = new Location(jerry.row(),jerry.col()-1);
        Location upOfJerry = new Location(jerry.row()-1,jerry.col());
        Location downOfJerry = new Location(jerry.row()+1,jerry.col());

        Move move_for_testing = null;
        if(l2vc.containsKey(rightOfJerry)&&l2vc.get(rightOfJerry).isClear()){
            sc.TomLocation = rightOfJerry;
            move_for_testing = new Move.Right(1);
        }
        else if(l2vc.containsKey(leftOfJerry)&&l2vc.get(leftOfJerry).isClear()){
            sc.TomLocation = leftOfJerry;
            move_for_testing = new Move.Left(1);
        }
        if(l2vc.containsKey(upOfJerry)&&l2vc.get(upOfJerry).isClear()) {
            sc.TomLocation = upOfJerry;
            move_for_testing = new Move.Up(1);
        }
        if(l2vc.containsKey(downOfJerry)&&l2vc.get(downOfJerry).isClear()) {
            sc.TomLocation = downOfJerry;
            move_for_testing = new Move.Down(1);
        }

        submitted_move = new LinkedBlockingQueue<>();
        submitted_move.put(new Move.Left(1)); //to test the "invalid mode" hint
        submitted_move.put(move_for_testing);

        tjg.jerrySpeed = 1;
        tjg.JerryMoves(true,true,submitted_move);//target function
        assertEquals(GameState.TOM_WIN,sc.gameStateOutcome());

        /**Test ending condition at beginning of while(remaning_moves>0)*/
        tjg = new TomJerryGame(MAP_FILE_PATH, SP_OUTPUT_PATH);
        sc = tjg.stateController;
        sc.JerryLocation = sc.exitLocation;

        submitted_move = new LinkedBlockingQueue<>();
        submitted_move.put(new Move.Right(1));
        submitted_move.put(move_for_testing);

        tjg.jerrySpeed = 1;
        tjg.JerryMoves(true,true,submitted_move);//target function
        assertEquals(GameState.TOM_WIN,sc.gameStateOutcome());

    }

    @Test
    public void test_run() throws IOException, InterruptedException, UnsupportedAudioFileException, LineUnavailableException {
        TomJerryGame tjg = new TomJerryGame(MAP_FILE_PATH, SP_OUTPUT_PATH);
        GameStateController sc = tjg.stateController;

        //test:Jerry Wins (starting from two steps away from the Exit point)
        ArrayList<Location> shortestpath = tjg.shortestPathGenerator.calculate_shortest_path(sc.entryLocation,sc.exitLocation);
        Location secondLast = shortestpath.get(shortestpath.size()-3);
        Location last = shortestpath.get(shortestpath.size()-2);
        sc.TomLocation = sc.entryLocation;
        sc.JerryLocation = secondLast;
        LinkedBlockingQueue<Move> to_be_submitted = new LinkedBlockingQueue<>();
        System.out.println("Jerry starts at "+sc.JerryLocation.row()+" "+sc.JerryLocation.col());
        System.out.println("Exit is at "+sc.exitLocation.row()+" "+sc.exitLocation.col());
        if(last.row()== secondLast.row()){
            if(last.col()== secondLast.col()+1){
                System.out.println("Jerry programmed to move right");
                to_be_submitted.put(new Move.Right(1));
            }
            else if(last.col()== secondLast.col()-1){
                System.out.println("Jerry programmed to move left");
                to_be_submitted.put(new Move.Left(1));
            }
        }
        else{
            if(last.row()== secondLast.row()+1){
                System.out.println("Jerry programmed to move down");
                to_be_submitted.put(new Move.Down(1));
            }
            else if(last.row()== secondLast.row()-1){
                System.out.println("Jerry programmed to move up");
                to_be_submitted.put(new Move.Up(1));
            }
        }

        if(sc.exitLocation.row()== last.row()){
            if(sc.exitLocation.col()== last.col()+1){
                System.out.println("Jerry programmed to move right");
                to_be_submitted.put(new Move.Right(1));
            }
            else if(sc.exitLocation.col()== last.col()-1){
                System.out.println("Jerry programmed to move left");
                to_be_submitted.put(new Move.Left(1));
            }
        }
        else{
            if(sc.exitLocation.row()== last.row()+1){
                System.out.println("Jerry programmed to move down");
                to_be_submitted.put(new Move.Down(1));
            }
            else if(sc.exitLocation.row()== last.row()-1){
                System.out.println("Jerry programmed to move up");
                to_be_submitted.put(new Move.Up(1));
            }
        }
        tjg.run(new LandingPageView(),true,false,to_be_submitted); //target function
        assertEquals(GameState.JERRY_WIN, tjg.stateController.gameStateOutcome());

        System.out.println("Done with first test");
        //test tom wins, put tom near Entry, let Jerry hit Tom
        tjg = new TomJerryGame(MAP_FILE_PATH, SP_OUTPUT_PATH);
        sc = tjg.stateController;
        shortestpath = tjg.shortestPathGenerator.calculate_shortest_path(sc.entryLocation,sc.exitLocation);
        secondLast = shortestpath.get(shortestpath.size()-3);
        last = shortestpath.get(shortestpath.size()-2);
        sc.JerryLocation = secondLast;
        to_be_submitted = new LinkedBlockingQueue<>();
        System.out.println("Jerry starts at "+sc.JerryLocation.row()+" "+sc.JerryLocation.col());
        System.out.println("Exit is at "+sc.exitLocation.row()+" "+sc.exitLocation.col());
        if(last.row()== secondLast.row()){
            if(last.col()== secondLast.col()+1){
                System.out.println("Jerry programmed to move right");
                to_be_submitted.put(new Move.Right(1));
            }
            else if(last.col()== secondLast.col()-1){
                System.out.println("Jerry programmed to move left");
                to_be_submitted.put(new Move.Left(1));
            }
        }
        else{
            if(last.row()== secondLast.row()+1){
                System.out.println("Jerry programmed to move down");
                to_be_submitted.put(new Move.Down(1));
            }
            else if(last.row()== secondLast.row()-1){
                System.out.println("Jerry programmed to move up");
                to_be_submitted.put(new Move.Up(1));
            }
        }

        if(sc.exitLocation.row()== last.row()){
            if(sc.exitLocation.col()== last.col()+1){
                System.out.println("Jerry programmed to move right");
                to_be_submitted.put(new Move.Right(1));
            }
            else if(sc.exitLocation.col()== last.col()-1){
                System.out.println("Jerry programmed to move left");
                to_be_submitted.put(new Move.Left(1));
            }
        }
        else{
            if(sc.exitLocation.row()== last.row()+1){
                System.out.println("Jerry programmed to move down");
                to_be_submitted.put(new Move.Down(1));
            }
            else if(sc.exitLocation.row()== last.row()-1){
                System.out.println("Jerry programmed to move up");
                to_be_submitted.put(new Move.Up(1));
            }
        }
        tjg.run(new LandingPageView(),true,false,to_be_submitted); //target function
        assertEquals(GameState.TOM_WIN, tjg.stateController.gameStateOutcome());

    }
}
