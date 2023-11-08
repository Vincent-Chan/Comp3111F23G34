package game_states;
import game_entities.*;

import java.util.*;

/**
 * The state of the Sokoban Game.
 * Each game state represents an ongoing game.
 * As the game goes, the game state changes while players are moving while the original game map stays the unmodified.
 * <b>The game state should not modify the original game map.</b>
 * <p>
 * GameStateController consists of things changing as the game goes, such as:
 * <li>Current locations of all crates.</li>
 * <li>A move history.</li>
 * <li>Current location of player.</li>
 * <li>Undo quota left.</li>
 */
public class GameStateController {


    private  Map<Location, Vertex> location2vertex = new HashMap<>();
    private Location TomLocation;
    private Location JerryLocation;
    private Location entryLocation;
    private Location exitLocation;
    public static final int SIDE_LENGTH = 30;
    public GameStateController(ArrayList<ArrayList<Integer>> MazeMap, Location start, Location end) {
        this.entryLocation = start;
        this.exitLocation = end;
        TomLocation = new Location(end.row(),end.col());
        JerryLocation = new Location(start.row(),start.col());

        for (int i = 0; i < SIDE_LENGTH; i++) {
            for (int j = 0; j < SIDE_LENGTH; j++) {
                if (MazeMap.get(i).get(j) == 1)
                    location2vertex.put(new Location(i, j), new Vertex(false));
                else
                    location2vertex.put(new Location(i, j), new Vertex(true));
            }
        }

    }
    public boolean canMove(int charId, Move move){
        Location from = charId==0?TomLocation:JerryLocation;

        // out-of-bound
        if(location2vertex.get(move.nextPosition(from))==null){
            return false;
        }
        return location2vertex.get(move.nextPosition(from)).isClear();
    }

    public boolean moveCharacter(int charId, Move move){
        Location from = charId==0?TomLocation:JerryLocation;
        Location to = move.nextPosition(from);
        if(!canMove(charId, move))
            return false;
        if(charId==0){
            TomLocation = new Location(to.row(), to.col());
        }
        else{
            JerryLocation = new Location(to.row(), to.col());;
        }
        return true;
    }

    public Location getCharacterLocation(int charId){return charId==0? TomLocation:JerryLocation;}

    public GameState gameStateOutcome(){

        if(JerryLocation.equals(exitLocation)){
            return GameState.JERRY_WIN;
        }
        if(TomLocation.equals(JerryLocation)){
            return GameState.TOM_WIN;
        }

        return GameState.CONTINUE;
    }
}


