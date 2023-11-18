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
import java.awt.*;
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
    public static void main(String[] args) throws IOException {

        LandingPageView landingPageView= new LandingPageView();
        landingPageView.setVisible(true);
        LandingPageController landingPageController = landingPageView.getController();
        LinkedBlockingQueue<String> queue = landingPageController.getButtonHitRecords();

        while(true){
            String startButtonPressedRecord;
            try{
                startButtonPressedRecord = queue.take();
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

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
                System.out.println();
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
            int PLAYER_SPEED = 3;
            int COMPUTER_SPEED = 3;

            ShortestPathGenerator shortestPathGenerator = new ShortestPathGenerator(MAP_FILE_PATH,SP_OUTPUT_PATH);
            JOptionPane.showMessageDialog(null,StringResources.show_sp_hint , "Hint", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(StringResources.show_sp_hint_image));
            ArrayList<Location> SP = shortestPathGenerator.calculate_shortest_path(entry,exit);
            mazeMapController.highlightPath(SP);
            shortestPathGenerator.output_file(SP);
            boolean highlighted = true; // flag, false->not yet highlighted the shortest path from entry to exit

            /**Game loop with ending condition being checked upon the record of every new state*/
            while(stateController.gameStateOutcome()==GameState.CONTINUE){
                turn = turn%2;

                if(turn == characterID.TOM_ID){
                    // Tom's turn, move along the shortest path
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
                    //player's turn
                    System.out.println("Jerry's turn");
                    int remainingMoves =PLAYER_SPEED;
                    windowsView.setTextBillboard(StringResources.showRemainingMoves(1,remainingMoves));
                    while (remainingMoves>0){
                        if(stateController.gameStateOutcome()!=GameState.CONTINUE){
                            break;
                        }
                        Location oldtom = stateController.getCharacterLocation(0);
                        Location oldjerry = stateController.getCharacterLocation(1);

                        Move nextMove;
                        try{
                            LinkedBlockingQueue<Move> actionQueue = windowsView.getControlPanelView().getControlPanelController().getActionQueue();
                            System.out.println("Hashcode of actionqueue in main.java"+actionQueue.hashCode());
                            System.out.println(actionQueue.size());
                            nextMove = actionQueue.take();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println("new move fetched: "+nextMove);
                        boolean moveResult = stateController.moveCharacter(1, nextMove);

                        //check validity of the move
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

            /**An Game Instance Ends*/
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
