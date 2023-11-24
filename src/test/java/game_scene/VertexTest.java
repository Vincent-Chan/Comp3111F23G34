package game_scene;

import game_states.Location;
import org.junit.Test;
import visuals.StringResources;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class VertexTest {

    @Test
    public void test_ConstructorVertexController(){
        VertexController vc = new VertexController(StringResources.barrier,2,3); //target
        assertInstanceOf(VertexController.class, vc);

    }
    @Test
    public void test_changeVertexColor() {
        VertexController vc = new VertexController(StringResources.barrier,2,3);
        VertexViewer vv = vc.getVertex();

        assertEquals(Color.DARK_GRAY, vv.getBackground());
        vc.changeVertexColor(StringResources.clear_vertex); //target function
        assertEquals(Color.WHITE,vv.getBackground());
    }

    @Test
    public void test_getVertex() {
        VertexController vc = new VertexController(StringResources.barrier,2,3);
        VertexViewer vv = vc.getVertex(); //target function
        assertInstanceOf(VertexViewer.class, vv);
    }

    @Test
    public void test_insertImage() throws IOException {
        Location l = new Location(2,3);
        MazeMapController mmc = generateMazeMapController();
        VertexController vc = mmc.getLocationVertexControllerMap().get(l);
        vc.insertImage(StringResources.tom); //target function
    }

    @Test
    public void test_removeImage() throws IOException {
        Location l = new Location(2,3);
        MazeMapController mmc = generateMazeMapController();
        VertexController vc = mmc.getLocationVertexControllerMap().get(l);
        vc.insertImage(StringResources.tom);
        vc.removeImage(); //target function
    }

    @Test
    public void test_vertexViewerchangeColor() {
        VertexViewer vv = new VertexViewer(Color.WHITE);
        assertEquals(Color.WHITE,vv.getBackground());
        vv.ChangeColor(Color.DARK_GRAY); //target function
        assertEquals(Color.DARK_GRAY, vv.getBackground());
    }

    @Test
    public void test_constructorVertexView(){
        VertexViewer vv = new VertexViewer(Color.WHITE); //target function
        assertInstanceOf(VertexViewer.class,vv);
    }

    private MazeMapController generateMazeMapController() throws IOException {
        Location entry = new Location(12,0);
        Location exit = new Location(12,29);

        ArrayList<ArrayList<Integer>> mazeMap = new ArrayList<ArrayList<Integer>>();
        for(int i = 0;i<30;i++){
            ArrayList<Integer> row = new ArrayList<>();
            for(int j = 0;j<30;j++){
                if(i== entry.row()){
                    row.add(0);
                }
                else{
                    row.add(1);
                }
            }
            mazeMap.add(row);
        }

        WindowsView wv = new WindowsView(mazeMap,entry,exit);
        MazeMapController mazeMapController = wv.getMapViewer().getController();

        return mazeMapController;
    }
}