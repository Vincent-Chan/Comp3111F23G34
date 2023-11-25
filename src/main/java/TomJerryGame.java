import game_algorithm.GameMapGenerator;
import game_algorithm.ShortestPathGenerator;
import game_entities.characterID;
import game_scene.LandingPageController;
import game_scene.LandingPageView;
import game_scene.MazeMapController;
import game_scene.WindowsView;
import game_states.GameState;
import game_states.GameStateController;
import game_states.Location;
import game_states.Move;
import visuals.StringResources;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Each instance of this class is an independent game with its own maze map, game states, and GUI components
 * */
public class TomJerryGame {
    public final int SIDE_LENGTH =  30;
    private String MAP_FILE_PATH = "src/main/java/MazeData.csv";
    private String SP_OUTPUT_PATH = "src/main/java/shortest_path_at_beginning.csv";
    public ArrayList<ArrayList<Integer>> mazeMap;
    public Location entry;
    public Location exit;

    public WindowsView windowsView;
    public MazeMapController mazeMapController;
    public GameStateController stateController;

    public ShortestPathGenerator shortestPathGenerator;
    public String difficulty;

    public int jerrySpeed = 3;
    public int tomSpeed = 3;

    public TomJerryGame(String map_file_path, String sp_output_path) throws IOException {
        this.MAP_FILE_PATH = map_file_path;
        this.SP_OUTPUT_PATH = sp_output_path;

        /**
         * Initializes the map and path generators
         * Store the generated map as csv file*/
        GameMapGenerator gameMapGenerator = new GameMapGenerator(MAP_FILE_PATH);
        gameMapGenerator.to_csv(gameMapGenerator.PrimMazeGenerator());
        shortestPathGenerator = new ShortestPathGenerator(MAP_FILE_PATH,SP_OUTPUT_PATH);

        /**For sake of consistency, load the just-saved map, initializes the entry and exit location*/
        mazeMap = readData(MAP_FILE_PATH, ",");

        for(int i = 0;i<SIDE_LENGTH;i++){
            for(int j = 0;j<SIDE_LENGTH;j++){
                if(mazeMap.get(i).get(j)==2){
                    entry = new Location(i,j);
                }

                else if (mazeMap.get(i).get(j)==3)
                    exit = new Location(i,j);
            }
        }

        /**Initialize all GUI controllers and views */

        windowsView = new WindowsView(mazeMap, entry, exit);
        mazeMapController = windowsView.getMapViewer().getController();
        mazeMapController.insertImage(entry, StringResources.jerry);
        mazeMapController.insertImage(exit, StringResources.tom);
        windowsView.setTextBillboard(StringResources.gamestarts);

        stateController = new GameStateController(mazeMap, entry, exit);

    }
    /**Read maze map data from a specified csv file
     *
     * @param file  the path of the csv file containing the maze map data
     * @param separator the separator used by the csv file
     *
     * @throws  IOException if the filepath points to invalid csv file
     * */
    public ArrayList<ArrayList<Integer>> readData(String file, String separator) throws IOException {
        int count = 0;
        ArrayList<ArrayList<Integer>> content = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = "";
            while ((line = br.readLine()) != null) {
                ArrayList<Integer> intList = new ArrayList<>();
                String[] strlist = line.split(separator);
                for(int i = 0;i< strlist.length;i++){
                    intList.add(Integer.parseInt(strlist[i].substring(1,2))); //input is ["1","1","1"], needs to get rid of ""
                }
                content.add(intList);
            }
        } catch (FileNotFoundException e) {
            return null;
        }
        return content;
    }

    /**Choose mode of difficulty
     * <ui>
     *     <li>Easy: PLAYER_SPEED = 3, COMPUTER_SPEED = 3</li>
     *     <li>Medium: PLAYER_SPEED = 4, COMPUTER_SPEED = 6</li>
     *     <li>Hard: PLAYER_SPEED = 6, COMPUTER_SPEED = 9</li>
     * </ui>
     * */
    public boolean setDifficulty(boolean unittesting, String unittesting_fixed_difficulty){
        String[] difficultyModes = {"Easy", "Medium", "Hard"};

        // Show the message box and get the selected difficulty mode
        JOptionPane optionPane = new JOptionPane("Please select the difficulty.\n The harder the game, the more steps Tom can run in each turn!",JOptionPane.PLAIN_MESSAGE,JOptionPane.DEFAULT_OPTION,new ImageIcon(StringResources.select_difficulty),difficultyModes, difficultyModes[0]);
        JDialog dialog = optionPane.createDialog("Option Dialog");
        if(unittesting){
            dialog.setModal(false);
            optionPane.setValue(unittesting_fixed_difficulty);
        }
        dialog.setVisible(true);

        difficulty = (String)optionPane.getValue();
        if(difficulty==null)
            return false;
        if(difficulty.equals("Easy")){
            jerrySpeed = 3;
            tomSpeed = 3;
        }
        else if(difficulty.equals("Medium")){
            jerrySpeed = 4;
            tomSpeed = 6;
        }
        else if(difficulty.equals("Hard")){
            jerrySpeed = 6;
            tomSpeed = 9;
        }

        JOptionPane optionPane2 = new JOptionPane("Selected Difficulty Mode: " + difficulty+" \n In each round, you can control Jerry to run "+jerrySpeed+" blocks and Tom can run "+tomSpeed+" steps.",JOptionPane.INFORMATION_MESSAGE,JOptionPane.DEFAULT_OPTION);
        JDialog dialog2 = optionPane2.createDialog("Difficulty Selected");
        if(unittesting){
            dialog2.setModal(false);
        }
        dialog2.setVisible(true);
        return true;
    }

    /**
     * Calculate the shortest path from Tom to Jerry
     * Move Tom by one step following this path
     *
     * @param remainingMoves the remaining quota of moves by Tom
     * @param unittesting (for unit testing only) to fix the movements of tom
     * @throws IOException when exception occurs in updating the GUI of the map
     * */
    public void TomMovesOneStep(int remainingMoves, boolean unittesting, String unit_test_direction) throws IOException {
        Location oldtom = stateController.getCharacterLocation(characterID.TOM_ID);
        Location oldjerry = stateController.getCharacterLocation(characterID.JERRY_ID);
        ArrayList<Location> shortestPathToJerry = shortestPathGenerator.calculate_shortest_path(oldtom,oldjerry);

        /** infer intermediate move that results in location transition: oldtom->newtom*/
        Location newtom = shortestPathToJerry.get(1);
        if(unittesting){
            if(unit_test_direction.equals("up"))
                newtom = new Location(oldtom.row()-1, oldtom.col());
            else if(unit_test_direction.equals("down"))
                newtom = new Location(oldtom.row()+1, oldtom.col());
            else if(unit_test_direction.equals("right"))
                newtom = new Location(oldtom.row(), oldtom.col()+1);
            else if(unit_test_direction.equals("left"))
                newtom = new Location(oldtom.row(), oldtom.col()-1);}

        Move nextMove = null;
        if(newtom.row() == oldtom.row()+1){
            System.out.println(1);
            nextMove = new Move.Down(characterID.TOM_ID);}
        if(newtom.row() == oldtom.row()-1){
            System.out.println(2);
            nextMove = new Move.Up(characterID.TOM_ID);}
        if(newtom.col() == oldtom.col()+1){
            System.out.println(3);
            nextMove = new Move.Right(characterID.TOM_ID);}
        if(newtom.col() == oldtom.col()-1){
            System.out.println(4);
            nextMove = new Move.Left(characterID.TOM_ID);}
        stateController.moveCharacter(characterID.TOM_ID,nextMove);
        Location newjerry = stateController.getCharacterLocation(characterID.JERRY_ID);
        mazeMapController.renderMap(newtom,newjerry,oldtom,oldjerry,unittesting);
        windowsView.setTextBillboard(StringResources.showRemainingMoves(1, remainingMoves -1));
        windowsView.revalidate();
        windowsView.repaint();}

    /**
     * Called when it is Tom's turn to move
     * */
    public void TomMoves() throws IOException {
        int remainingMoves =tomSpeed;
        windowsView.setTextBillboard(StringResources.showRemainingMoves(characterID.TOM_ID,remainingMoves));

        while (remainingMoves>0){
            if(stateController.gameStateOutcome()!=GameState.CONTINUE){
                break;}
            this.TomMovesOneStep(remainingMoves,false,"");
            remainingMoves--;
            if(stateController.gameStateOutcome()!=GameState.CONTINUE){
                break;}}}
    public void JerryMoves(boolean highlighted, boolean unittesting, LinkedBlockingQueue<Move> unit_test_movements) throws InterruptedException, IOException, UnsupportedAudioFileException, LineUnavailableException {
        int remainingMoves = jerrySpeed;
        windowsView.setTextBillboard(StringResources.showRemainingMoves(1, remainingMoves));
        while (remainingMoves > 0) {
            if (stateController.gameStateOutcome() != GameState.CONTINUE) {
                break;
            }
            Location oldtom = stateController.getCharacterLocation(0);
            Location oldjerry = stateController.getCharacterLocation(1);
            LinkedBlockingQueue<Move> actionQueue = windowsView.getControlPanelView().getControlPanelController().getActionQueue();
            if (unittesting)
                actionQueue.put(unit_test_movements.take());
            Move nextMove = actionQueue.take();

            /**check validity of the move*/
            boolean moveResult = stateController.moveCharacter(1, nextMove);
            if (moveResult) {
                Location newtom = stateController.getCharacterLocation(0);
                Location newjerry = stateController.getCharacterLocation(1);
                remainingMoves--;
                if (highlighted) {
                    mazeMapController.removeHighlightPath();
                    highlighted = false;
                }
                mazeMapController.renderMap(newtom, newjerry, oldtom, oldjerry, unittesting);
                windowsView.setTextBillboard(StringResources.showRemainingMoves(1, remainingMoves));
                windowsView.revalidate();
                windowsView.repaint();
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(StringResources.movement_button_bgm));
                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.setFramePosition(0);
                clip.start();

                /**clear any left-over actions in actionqueue if this is the last chance for user to move in current roun
                 * e.g. if user can move 3 steps in a round, but he presses the button for 4 times,
                 *      then there is 1 step left-over
                 *      need to clear this step before proceeding to next round
                 * */
                if (remainingMoves == 0) {
                    if (unittesting)
                        actionQueue.put(new Move.Up(1));
                    actionQueue.clear();
                }
                if (stateController.gameStateOutcome() != GameState.CONTINUE) {
                    break;
                }}
            else {
                JOptionPane pane = new JOptionPane("Invalid Move! Be sure to stay in map and avoid the barriers\nThis will not exhaust your quota for moving in this round", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, new ImageIcon(StringResources.invalid_move));
                final JDialog dialog = pane.createDialog("Invalid Move!");
                if (unittesting)
                    dialog.setModal(false);
                dialog.setVisible(true);
            }
        }
    }

    /**
     * Show the shortest path from Jerry to the exit AND the reachable positions by Tom
     * Shown at end of every even-number rounds
     *
     * @param do_SP highlight the shortest path
     * @param do_TRL highlight the reachable locations by Tom
     * */
    public void showHintsOnMap(boolean do_SP, boolean do_TRL){
        ArrayList<Location> SP = new ArrayList<>();
        ArrayList<Location> reachableByTom = new ArrayList<>();
        if(do_SP) {
            /**Highlight the shortest path from Jerry to exit*/
            SP = shortestPathGenerator.calculate_shortest_path(stateController.getCharacterLocation(characterID.JERRY_ID), exit);
            SP.remove(0);
            mazeMapController.highlightPath(SP, StringResources.sp_component);
        }
        if(do_TRL){
            /**Highlight the reachable locations by Tom*/
            reachableByTom = stateController.reachablePositions(characterID.TOM_ID, tomSpeed);
            mazeMapController.highlightPath(reachableByTom, StringResources.tom_reachable_location);
        }
        if(do_SP && do_TRL){
            /**If a vertex is both on the shortest path AND reachable by Tom, change it to another color*/
            for(Location l:SP){
                if(reachableByTom.contains(l)){
                    mazeMapController.getLocationVertexControllerMap().get(l).changeVertexColor(StringResources.SP_union_tom_reachable_location);
                }
            }
        }
    }
    public void run(LandingPageView landingPageView,boolean unittesting,boolean unittesting_from_main, LinkedBlockingQueue<Move> moves_for_testing) throws IOException, InterruptedException, UnsupportedAudioFileException, LineUnavailableException {
        if(unittesting_from_main)
            return;

        /**Set all the bgm elements ready*/
        AudioInputStream audioStream_jl = AudioSystem.getAudioInputStream(new File(StringResources.jerry_caught_bgm));
        Clip clip_jerry_lose = AudioSystem.getClip();
        AudioInputStream audioStream_main = AudioSystem.getAudioInputStream(new File(StringResources.main_game_bgm));
        Clip clip_main = AudioSystem.getClip();
        AudioInputStream audioStream_jw = AudioSystem.getAudioInputStream(new File(StringResources.jerry_win_bgm));
        Clip clip_jerry_win = AudioSystem.getClip();
        /**Player presses "Start Game"*/
        if (!this.setDifficulty(unittesting,"Easy"))
            return;
        if(unittesting){
            tomSpeed = 1;
            jerrySpeed = 1;
        }
        /**Starts to play main game bgm*/
        clip_main.open(audioStream_main);
        clip_main.loop(Clip.LOOP_CONTINUOUSLY);

        windowsView.setVisible(true);
        if(unittesting)
            windowsView.setVisible(false);
        landingPageView.setVisible(false);

        JOptionPane pane1 = new JOptionPane("To make your life easier, we have prepared you with several hints",JOptionPane.INFORMATION_MESSAGE,JOptionPane.DEFAULT_OPTION,new ImageIcon(StringResources.show_sp_hint_image));
        final JDialog dialog = pane1.createDialog("Guide");
        if(unittesting)
            dialog.setModal(false);
        dialog.setVisible(true);


        /**Guidance: demo of highlighting the shortest path*/
        showHintsOnMap(true,false);
        JOptionPane pane2 = new JOptionPane("We will highlight the shortest path to the exit in GREEN",JOptionPane.INFORMATION_MESSAGE,JOptionPane.DEFAULT_OPTION,new ImageIcon(StringResources.show_sp_hint_image));
        final JDialog dialog2 = pane2.createDialog("Guide");
        if(unittesting)
            dialog2.setModal(false);
        dialog2.setVisible(true);


        /**Guidance: demo of highlighting tom reachable locations*/
        mazeMapController.removeHighlightPath();
        showHintsOnMap(false,true);
        JOptionPane pane3 = new JOptionPane("We will highlight the reachable locations by Tom in his next round in YELLOW",JOptionPane.INFORMATION_MESSAGE,JOptionPane.DEFAULT_OPTION,new ImageIcon(StringResources.show_sp_hint_image));
        final JDialog dialog3 = pane3.createDialog("Guide");
        if(unittesting)
            dialog3.setModal(false);
        dialog3.setVisible(true);

        /**Guidance: demo of highlighting both shortest path and tom reachable locations*/
        mazeMapController.removeHighlightPath();
        showHintsOnMap(true,true);
        mazeMapController.removeHighlightPath();
        showHintsOnMap(false,true);
        JOptionPane pane4 = new JOptionPane("If a location is both on the shortest path and reachable by Tom, we will show it in RED, beware of these locations!",JOptionPane.INFORMATION_MESSAGE,JOptionPane.DEFAULT_OPTION,new ImageIcon(StringResources.show_sp_hint_image));
        final JDialog dialog4 = pane4.createDialog("Guide");
        if(unittesting)
            dialog4.setModal(false);
        dialog4.setVisible(true);
        boolean highlighted = true;

        int turn = 1; // turn%2 -> id of current GameCharacter that can move
        /**Game loop with ending condition being checked upon the record of every new state*/
        while(stateController.gameStateOutcome()== GameState.CONTINUE){
            turn = turn%2;
            if(turn%2==1){
                highlighted = true;
                showHintsOnMap(true, true);}
            if(turn == characterID.TOM_ID){
                this.TomMoves();}
            else{
                /**Process jerry's moves*/
                this.JerryMoves(highlighted,unittesting,moves_for_testing);
                highlighted = false;}

            if(stateController.gameStateOutcome()!=GameState.CONTINUE){
                break;}
            turn++;}

        /**A Game Instance Ends*/
        if(stateController.gameStateOutcome() == GameState.JERRY_WIN){
            clip_main.stop(); //stop main bgm
            windowsView.revalidate();
            windowsView.repaint();
            JOptionPane pane5 = new JOptionPane(StringResources.jerryWinsMessage,JOptionPane.INFORMATION_MESSAGE,JOptionPane.DEFAULT_OPTION,new ImageIcon(StringResources.show_sp_hint_image));
            final JDialog dialog5 = pane5.createDialog("Guide");


            clip_jerry_win.open(audioStream_jw);
            clip_jerry_win.setFramePosition(0);
            clip_jerry_win.start();


            if(unittesting)
                dialog5.setModal(false);
            dialog5.setVisible(true);
        }
        else{
            clip_main.stop(); //stop main bgm
            windowsView.revalidate();
            windowsView.repaint();
            JOptionPane pane6 = new JOptionPane(StringResources.tomWinsMessage,JOptionPane.INFORMATION_MESSAGE,JOptionPane.DEFAULT_OPTION,new ImageIcon(StringResources.show_sp_hint_image));
            final JDialog dialog6 = pane6.createDialog("Guide");


            clip_jerry_lose.open(audioStream_jl);
            clip_jerry_lose.setFramePosition(0);
            clip_jerry_lose.start();

            if(unittesting)
                dialog6.setModal(false);
            dialog6.setVisible(true);
        }
        /**Clear existing data*/
        LinkedBlockingQueue<Move> actionQueue = windowsView.getControlPanelView().getControlPanelController().getActionQueue();
        actionQueue.clear();
        windowsView.setVisible(false);

        if(clip_jerry_win.isRunning())
            clip_jerry_win.stop();
        if(clip_jerry_lose.isRunning())
            clip_jerry_lose.stop();
    }


}
