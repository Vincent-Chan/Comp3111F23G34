package game_scene;
import game_states.Location;
import visuals.StringResources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

/**
 * The controller of the maze map
 */
public class MazeMapController {
    /**The hashmap storing the controller of the vertex at each location on the map*/
    private HashMap<Location, VertexController> location2vertexController;

    /**The stack storing the highlighted vertex when showing the shortest path*/
    private Stack<Location> highlightedVertexLocation = new Stack<>();

    /**The location of the entry point of the map*/
    private Location entry;

    /**The location of the exit point of the map*/
    private Location exit;

    /**The side length of the square map*/
    public static final int SIDE_LENGTH = 30;


    /**
     * Constructor of this class
     *
     * @param MazeMap The maze map data based on which this controller will monitor the game state
     * @param entry The entry point of the maze map
     * @param exit The exit point of the maze map
     */
    public MazeMapController(ArrayList<ArrayList<Integer>> MazeMap, Location entry, Location exit){
        this.entry = entry;
        this.exit = exit;

        location2vertexController = new HashMap<>();

        //initialize a 2D list of vertex controllers
        for(int i = 0;i<SIDE_LENGTH;i++){
            for(int j = 0;j<SIDE_LENGTH;j++){
                if(MazeMap.get(i).get(j)==1)
                    location2vertexController.put(new Location(i,j),new VertexController(StringResources.barrier,i,j));
                else
                    location2vertexController.put(new Location(i,j),new VertexController(StringResources.clear_vertex,i,j));
            }
        }
    }

    /**
     * Accessor function
     * @return the map storing the controller instance of the vertex at eacb specified location
     */
    public HashMap<Location, VertexController> getLocationVertexControllerMap(){return location2vertexController;}


    /**
     * Insert an image on a vertex, e.g. the image of Tom and Jerry will be inserted into the vertices they reside on
     *
     * @param location The location of the vertex to insert image
     * @param imagepath The path of the image to be inserted
     * @throws IOException if the imagepath is invalid
     */
    public void insertImage(Location location, String imagepath) throws IOException {
        this.location2vertexController.get(location).insertImage(imagepath);
    }

    /**
     * Update the appearance of the map based on the latest location of Tom and Jerry
     *
     * @param newjerry the new location that jerry is at, insert Jerry's image into the vertex at this location
     * @param newtom  the new location that tom is at, insert Tom's image into the vertex at this location
     * @param oldjerry  the last location jerry was at, remove Jerry's image from the vertex at this location
     * @param oldtom  the last location tom was at, remove Tom's image from the vertex at this location
     *
     * @throws IOException if there is error in image insertion operation
     * */
    public void renderMap(Location newtom, Location newjerry, Location oldtom, Location oldjerry) throws IOException {
        if(newtom!=oldtom){
            System.out.println("Tom change location, from "+oldtom+" to "+newtom);
            location2vertexController.get(oldtom).removeImage();
            location2vertexController.get(newtom).insertImage(StringResources.tom);
        }
        if(newjerry!=oldjerry){
            System.out.println("Jerry change location, from "+oldjerry+" to "+newjerry);
            location2vertexController.get(oldjerry).removeImage();
            location2vertexController.get(newjerry).insertImage(StringResources.jerry);
        }
    }


    /**
     * Highlight the vertices that form a path, called when showing the shortest path to the user
     *
     * @param pathComponents the vertices on the path to highlight
     */
    public void highlightPath(ArrayList<Location> pathComponents, String color_code){
        for(Location location:pathComponents){
            location2vertexController.get(location).changeVertexColor(color_code);
            highlightedVertexLocation.add(location);
        }
    }

    /**
     * Restore the appearance of the path that was previously highlighted
     */
    public void removeHighlightPath(){
        while(!highlightedVertexLocation.empty()){
            location2vertexController.get(highlightedVertexLocation.pop()).changeVertexColor(StringResources.clear_vertex);
        }
    }
}
