package game_scene;
import game_states.Location;
import visuals.StringResources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class MazeMapController {
    private HashMap<Location, VertexController> location2vertexController;
    private Stack<Location> highlightedVertexLocation = new Stack<>();
    private Location entry;
    private Location exit;
    public static final int SIDE_LENGTH = 30;
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
    public HashMap<Location, VertexController> getLocationVertexControllerMap(){return location2vertexController;}

    public void insertImage(Location location, String imagepath) throws IOException {
        this.location2vertexController.get(location).insertImage(imagepath);
    }


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

    public void highlightPath(ArrayList<Location> pathComponents){
        for(Location location:pathComponents){
            location2vertexController.get(location).changeVertexColor(StringResources.sp_component);
            highlightedVertexLocation.add(location);
        }
    }

    public void removeHighlightPath(){
        while(!highlightedVertexLocation.empty()){
            location2vertexController.get(highlightedVertexLocation.pop()).changeVertexColor(StringResources.clear_vertex);
        }
    }
}
