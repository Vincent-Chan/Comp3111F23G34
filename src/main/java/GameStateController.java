package game_states;
import game_entities.*;
import visuals.StringResources;

import java.util.*;

/**
 * The monitor of the game state.
 * As the game goes, the GameStateController takes a snapshot of the game after each move by recording the updated locations of Tom and Jerry.
 * <b>The original game map is not modified.</b>
 */
public class GameStateController {

    /**The hashmap storing the vertex entity at each location*/
    public  Map<Location, Vertex> location2vertex = new HashMap<>();

    /**The latest location of Tom*/
    public Location TomLocation;

    /**The latest location of Tom*/
    public Location JerryLocation;

    /**The location of entry point, IMMUTABLE*/
    public Location entryLocation;

    /**The location of exit point, IMMUTABLE*/
    public Location exitLocation;

    /**The side length of the game map*/
    public static final int SIDE_LENGTH = 30;

    /**
     * Constructor of this class
     *
     * @param MazeMap the underlying game map that initiates the first snapshot of game state
     * @param start the entry location of the map (starting point of Jerry)
     * @param end the exit location of the map (starting point of Tom)
     * */
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

    /**
     * Determines whether a proposed move request is valid
     *
     * <p>A move is NOT valid if either:
     * <ul>
     * <li>The resulted move puts the character at a location out of the map</li>
     * <li>The resulted move puts the character at a location occupied by a barrier</li>
     *</ul>
     * @param charId initiator of the move request, 0 for Tom, 1 for Jerry
     * @param move the requested move
     * */
    public boolean canMove(int charId, Move move){
        Location from = charId==0?TomLocation:JerryLocation;

        // out-of-bound
        if(location2vertex.get(move.nextPosition(from))==null){
            return false;
        }
        return location2vertex.get(move.nextPosition(from)).isClear();
    }

    /**
     * Honor the move request by a character after checking its validity.
     * <p> Update the records of the locations of Tom and Jerry
     *
     * @param charId the initiator of the move request
     * @param move the move request
     * */
    public boolean moveCharacter(int charId, Move move){
        Location from = charId==0?TomLocation
                :JerryLocation;
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

    /**
     * Fetch the latest location of a character
     *
     * @param charId the target character to get location from
     * @return the location of the specified character
     */
    public Location getCharacterLocation(int charId){
        return charId==0
            ? TomLocation
                :JerryLocation;}

    /**
     * Determine the current state of the game
     * <p> The game is ended if either one of these conditions happen:
     * <ul>
     * <li>Tom reaches Jerry's Location</li>
     * <li>Jerry reaches exit point Location</li>
     * </ul>
     * Otherwise, the game goes on
     * */
    public GameState gameStateOutcome(){
        if(TomLocation.equals(JerryLocation)){
            return GameState.TOM_WIN;
        }
        if(JerryLocation.equals(exitLocation)){
            return GameState.JERRY_WIN;
        }
        return GameState.CONTINUE;
    }
    /**
     * Given a quota of allowed_num_move, return all reachable positions by the given character
     *
     * @param characterID the character concerned
     * @param allowed_num_move the number of moves that can be made
     * */
    public ArrayList<Location> reachablePositions(int characterID, int allowed_num_move){
        final int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        ArrayList<Location> reachableLocations = new ArrayList<>();
        HashSet<Location> visited = new HashSet<>();
        Queue<Location> queue = new LinkedList<>();

        Location startLocation = getCharacterLocation(characterID);
        queue.offer(startLocation);
        visited.add(startLocation);

        int currentLevel = 0;
        int nodesLeftInCurrentLevel = 1;
        int nodesInNextLevel = 0;

        while (!queue.isEmpty() && currentLevel <= allowed_num_move) {
            Location currentLocation = queue.poll();

            reachableLocations.add(currentLocation);
            nodesLeftInCurrentLevel--;

            for (int[] direction : DIRECTIONS) {
                int newRow = currentLocation.row() + direction[0];
                int newCol = currentLocation.col() + direction[1];

                Location newLocation = new Location(newRow, newCol);

                if (newLocation.row()<SIDE_LENGTH && newLocation.row()>=0
                        &&newLocation.col()<SIDE_LENGTH && newLocation.col()>=0
                        &&location2vertex.get(newLocation).isClear() && !visited.contains(newLocation)) {
                    queue.offer(newLocation);
                    visited.add(newLocation);
                    nodesInNextLevel++;
                }
            }

            if (nodesLeftInCurrentLevel == 0) {
                nodesLeftInCurrentLevel = nodesInNextLevel;
                nodesInNextLevel = 0;
                currentLevel++;
            }
        }

        return reachableLocations;
    }
}


