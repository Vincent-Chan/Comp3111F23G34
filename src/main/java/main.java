import game_algorithm.GameMapGenerator;
import game_scene.LandingPageController;
import game_scene.LandingPageView;
import game_scene.MazeMapController;
import game_scene.WindowsView;
import game_states.GameState;
import game_states.GameStateController;
import game_entities.characterID;
import game_states.Location;
import game_states.Move;
import visuals.StringResources;
import game_algorithm.ShortestPathGenerator;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;


public class main {

    /**Read maze map data from a specified csv file
     *
     * @param file  the path of the csv file containing the maze map data
     * @param separator the separator used by the csv file
     *
     * @throws  IOException if the filepath points to invalid csv file
     * */
    public static ArrayList<ArrayList<Integer>> readData(String file, String separator) throws IOException {
        int count = 0;
        ArrayList<ArrayList<Integer>> content = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = "";
            while ((line = br.readLine()) != null) {
                System.out.println(line);
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

    /**
     * The driver function of the game that performs the following:
     * <ul>
     *     <li>Initiate a landing page showing a button to start the game</li>
     *     <li>If the user clicks the button, the game windows with the maze map and control panel is shown</li>
     *     <li>The user plays the game while the game state controller checks ending condition whenever a new state is recorded</li>
     *     <li>If the game ends, a message box is shown, and the user is directed back to the landing page</li>
     *     <li>The user can choose to start a new game by clicking the start button on the landing page again or close the landing page</li>
     *
     * </ul>
     *
     * */
    public static void main(String[] args) throws Exception {

        LandingPageView landingPageView= new LandingPageView();
        landingPageView.setVisible(true);
        LandingPageController landingPageController = landingPageView.getController();
        LinkedBlockingQueue<String> queue = landingPageController.getButtonHitRecords();

        while(true){
            String startButtonPressedRecord= queue.take();

            /**Reaches this point only if user presses start button*/
            landingPageView.setVisible(false);
            // read map from csv
            int SIDE_LENGTH =  30;
            final String MAP_FILE_PATH = "src/main/java/MazeData.csv";
            final String SP_OUTPUT_PATH = "src/main/java/shortest_path_at_beginning.csv";
            GameMapGenerator gameMapGenerator = new GameMapGenerator(MAP_FILE_PATH);
            gameMapGenerator.to_csv(gameMapGenerator.PrimMazeGenerator());

            // initialize maze map data structures
            ArrayList<ArrayList<Integer>> mazeMap = readData(MAP_FILE_PATH, ",");
            Location entry = null;
            Location exit = null;
            for(int i = 0;i<SIDE_LENGTH;i++){
                for(int j = 0;j<SIDE_LENGTH;j++){
                    if(mazeMap.get(i).get(j)==2){
                        entry = new Location(i,j);
                    }

                    else if (mazeMap.get(i).get(j)==3)
                        exit = new Location(i,j);
                }
            }

            /**Choose mode of difficulty
             * <ui>
             *     <li>Easy: PLAYER_SPEED = 3, COMPUTER_SPEED = 3</li>
             *     <li>Medium: PLAYER_SPEED = 4, COMPUTER_SPEED = 6</li>
             *     <li>Hard: PLAYER_SPEED = 6, COMPUTER_SPEED = 9</li>
             * </ui>
             * */
            int PLAYER_SPEED = 3;
            int COMPUTER_SPEED = 3;
            String[] difficultyModes = {"Easy", "Medium", "Hard"};

            // Show the message box and get the selected difficulty mode
            int choice = JOptionPane.showOptionDialog(null, "Please select the difficulty.\n The harder the game, the more steps Tom can run in each turn!", "Game Launcher",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, new ImageIcon(StringResources.select_difficulty), difficultyModes, difficultyModes[0]);
            if (choice >= 0) {
                String selectedMode = difficultyModes[choice];
                if(selectedMode.equals("Easy")){
                    PLAYER_SPEED = 3;
                    COMPUTER_SPEED = 3;
                }
                else if(selectedMode.equals("Medium")){
                    PLAYER_SPEED = 4;
                    COMPUTER_SPEED = 6;
                }
                else{
                    PLAYER_SPEED = 6;
                    COMPUTER_SPEED = 9;
                }
                JOptionPane.showMessageDialog(null, "Selected Difficulty Mode: " + selectedMode);

            } else {
                // User closed the dialog or clicked outside the options
                System.exit(0);
            }

            /**initialize the GUI components and obtain their controllers*/
            WindowsView windowsView = new WindowsView(mazeMap, entry, exit);
            MazeMapController mazeMapController = windowsView.getMapViewer().getController();
            mazeMapController.insertImage(entry, StringResources.jerry);
            mazeMapController.insertImage(exit, StringResources.tom);
            windowsView.setTextBillboard(StringResources.gamestarts);
            windowsView.setVisible(true);

            GameStateController stateController = new GameStateController(mazeMap, entry, exit);

            int turn = 1; // turn%2 -> id of current GameCharacter that can move

            ShortestPathGenerator shortestPathGenerator = new ShortestPathGenerator(MAP_FILE_PATH,SP_OUTPUT_PATH);
            JOptionPane.showMessageDialog(null,StringResources.show_sp_hint , "Hint", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(StringResources.show_sp_hint_image));
            ArrayList<Location> SP = shortestPathGenerator.calculate_shortest_path(entry,exit);
            mazeMapController.highlightPath(SP, StringResources.sp_component);
            shortestPathGenerator.output_file(SP);
            boolean highlighted = true; // flag, false->not yet highlighted the shortest path from entry to exit

            /**Game loop with ending condition being checked upon the record of every new state*/
            while(stateController.gameStateOutcome()==GameState.CONTINUE){
                turn = turn%2;

                if(turn == characterID.TOM_ID){
                    /** Tom's turn, move along the shortest path*/
                    System.out.println("Tom's turn");

                    int remainingMoves =COMPUTER_SPEED;
                    windowsView.setTextBillboard(StringResources.showRemainingMoves(characterID.TOM_ID,remainingMoves));

                    while (remainingMoves>0){
                        if(stateController.gameStateOutcome()!=GameState.CONTINUE){
                            break;
                        }
                        Location oldtom = stateController.getCharacterLocation(characterID.TOM_ID);
                        Location oldjerry = stateController.getCharacterLocation(characterID.JERRY_ID);

                        ArrayList<Location> shortestPathToJerry = shortestPathGenerator.calculate_shortest_path(oldtom,oldjerry);
                        if(shortestPathToJerry==null){
                            JOptionPane.showMessageDialog(null, "Error in generating shortestPathToJerry", "Message", JOptionPane.INFORMATION_MESSAGE);
                            return;
                        }

                        // infer intermediate move that results in location transition: oldtom->newtom
                        Location newtom = shortestPathToJerry.get(1);
                        Move nextMove = null;
                        if(newtom.row() == oldtom.row()+1){
                            nextMove = new Move.Down(characterID.TOM_ID);
                        }
                        if(newtom.row() == oldtom.row()-1){
                            nextMove = new Move.Up(characterID.TOM_ID);
                        }
                        if(newtom.col() == oldtom.col()+1){
                            nextMove = new Move.Right(characterID.TOM_ID);
                        }
                        if(newtom.col() == oldtom.col()-1){
                            nextMove = new Move.Left(characterID.TOM_ID);
                        }
                        stateController.moveCharacter(characterID.TOM_ID,nextMove);
                        remainingMoves--;

                        Location newjerry = stateController.getCharacterLocation(characterID.JERRY_ID);

                        /**Sleep for a while before rendering, so that user can see how Tom moves step by step*/
                        javax.swing.Timer timer = new javax.swing.Timer(2000, e -> {
                            System.out.println("Current Time is: " + new Date(System.currentTimeMillis()));
                        });
                        timer.setRepeats(false);
                        //timer.
                        timer.start();
                        mazeMapController.renderMap(newtom,newjerry,oldtom,oldjerry);
                        windowsView.setTextBillboard(StringResources.showRemainingMoves(1,remainingMoves));
                        windowsView.revalidate();
                        windowsView.repaint();
                        if(stateController.gameStateOutcome()!=GameState.CONTINUE){
                            break;
                        }
                    }
                }
                else{
                    /**Player's turn*/
                    System.out.println("Jerry's turn");
                    int remainingMoves =PLAYER_SPEED;
                    windowsView.setTextBillboard(StringResources.showRemainingMoves(1,remainingMoves));
                    while (remainingMoves>0){
                        if(stateController.gameStateOutcome()!=GameState.CONTINUE){
                            break;
                        }
                        Location oldtom = stateController.getCharacterLocation(0);
                        Location oldjerry = stateController.getCharacterLocation(1);

                        LinkedBlockingQueue<Move> actionQueue = windowsView.getControlPanelView().getControlPanelController().getActionQueue();
                        System.out.println("Hashcode of actionqueue in main.java"+actionQueue.hashCode());
                        System.out.println(actionQueue.size());
                        Move nextMove = actionQueue.take();
                        System.out.println("new move fetched: "+nextMove);


                        /**check validity of the move*/
                        boolean moveResult = stateController.moveCharacter(1, nextMove);
                        if(moveResult){
                            Location newtom = stateController.getCharacterLocation(0);
                            Location newjerry = stateController.getCharacterLocation(1);
                            if(stateController.gameStateOutcome()!=GameState.CONTINUE){
                                break;
                            }
                            remainingMoves--;
                            if(highlighted){
                                mazeMapController.removeHighlightPath();
                                highlighted = false;
                            }
                            mazeMapController.renderMap(newtom,newjerry,oldtom,oldjerry);
                            windowsView.setTextBillboard(StringResources.showRemainingMoves(1,remainingMoves));
                            windowsView.revalidate();
                            windowsView.repaint();

                            /**clear any left-over actions in actionqueue if this is the last chance for user to move in current roun
                             * e.g. if user can move 3 steps in a round, but he presses the button for 4 times,
                             *      then there is 1 step left-over
                             *      need to clear this step before proceeding to next round
                             * */
                            if(remainingMoves==0){
                                if(actionQueue.size()>0){
                                    actionQueue.clear();
                                }
                            }

                        }
                        else {
                            JOptionPane.showMessageDialog(null, "Invalid Move!", "Message", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(StringResources.invalid_move));
                        }

                        if(stateController.gameStateOutcome()!=GameState.CONTINUE){
                            break;
                        }
                    }
                }
                turn++;

            }

            /**A Game Instance Ends*/
            if(stateController.gameStateOutcome() == GameState.JERRY_WIN){
                windowsView.revalidate();
                windowsView.repaint();
                JOptionPane.showMessageDialog(null, StringResources.jerryWinsMessage, "Message", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(StringResources.jerry_wins));

            }
            else{
                windowsView.revalidate();
                windowsView.repaint();
                JOptionPane.showMessageDialog(null, StringResources.tomWinsMessage, "Message", JOptionPane.INFORMATION_MESSAGE,new ImageIcon(StringResources.tom_catches_jerry));
            }
            windowsView.setVisible(false);
            landingPageView.setVisible(true);
        }
    }
}
