package game_scene;

import game_states.Location;
import org.junit.Test;
import visuals.StringResources;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class MazeMapInterfaceTest {
    public static Location entry = new Location(12,0);
    public static Location exit = new Location(12,29);
    @Test
    public void testMazeMapViewer_constructor() {
        ArrayList<ArrayList<Integer>> mzm = generateMazeMap(entry,exit);
        MazeMapViewer mmv = new MazeMapViewer(mzm,entry,exit); //target
        assertNotNull(mmv);
    }

    @Test
    public void testMazeMapViewer_getController() {
        ArrayList<ArrayList<Integer>> mzm = generateMazeMap(entry,exit);
        MazeMapViewer mmv = new MazeMapViewer(mzm,entry,exit);
        MazeMapController mmc = mmv.getController();//target
        assertInstanceOf(MazeMapController.class,mmc);
    }

    @Test
    public void testMazeMapViewer_createAxisLabel(){
        ArrayList<ArrayList<Integer>> mzm = generateMazeMap(entry,exit);
        MazeMapViewer mmv = new MazeMapViewer(mzm,entry,exit);
        JLabel label = mmv.createAxisLabel("1");
        assertEquals("1",label.getText());//target
    }

    @Test
    public void testMazeMapController_constructor() {
        ArrayList<ArrayList<Integer>> mzm = generateMazeMap(entry,exit);
        MazeMapController mmc = new MazeMapController(mzm,entry,exit); // target
        assertInstanceOf(MazeMapController.class,mmc);
    }


    @Test
    public void testMazeMapController_getLocationVertexControllerMap() {
        ArrayList<ArrayList<Integer>> mzm = generateMazeMap(entry,exit);
        MazeMapController mmc = new MazeMapController(mzm,entry,exit);
        HashMap<Location, VertexController> l2vc = mmc.getLocationVertexControllerMap(); // target
        int map_size = mzm.size()*mzm.get(0).size();
        assertEquals(map_size, l2vc.size());
    }

    @Test
    public void testMazeMapController_highlightPath() {
        ArrayList<ArrayList<Integer>> mzm = generateMazeMap(entry,exit);
        MazeMapController mmc = new MazeMapController(mzm,entry,exit);
        ArrayList<Location> pathComponents = new ArrayList<>();
        pathComponents.add(new Location(2,3));
        pathComponents.add(new Location(3,4));
        mmc.highlightPath(pathComponents, StringResources.sp_component);
        for(Location l:pathComponents){
            Color col = mmc.getLocationVertexControllerMap().get(l).getVertex().getBackground();
            assertEquals(Color.GREEN,col);
        }
    }

    @Test
    public void testMazeMapController_removeHighlightPath() {
        ArrayList<ArrayList<Integer>> mzm = generateMazeMap(entry,exit);
        MazeMapController mmc = new MazeMapController(mzm,entry,exit);
        ArrayList<Location> pathComponents = new ArrayList<>();
        pathComponents.add(new Location(2,3));
        pathComponents.add(new Location(3,4));
        mmc.highlightPath(pathComponents, StringResources.sp_component);
        mmc.removeHighlightPath();
        for(Location l:pathComponents){
            Color col = mmc.getLocationVertexControllerMap().get(l).getVertex().getBackground();
            assertEquals(Color.WHITE,col);
        }
    }

    @Test
    public void testWindowsView_constructor() throws IOException {
        ArrayList<ArrayList<Integer>> mzm = generateMazeMap(entry,exit);
        WindowsView wv = new WindowsView(mzm,entry,exit); //target
    }

    @Test
    public void testWindowsView_setTextBillboard() throws IOException {
        ArrayList<ArrayList<Integer>> mzm = generateMazeMap(entry,exit);
        WindowsView wv = new WindowsView(mzm,entry,exit);
        wv.setTextBillboard("test");//target
    }

    @Test
    public void testWindowsView_getControlPanelView() throws IOException {
        ArrayList<ArrayList<Integer>> mzm = generateMazeMap(entry,exit);
        WindowsView wv = new WindowsView(mzm,entry,exit);
        ControlPanelView cpv = wv.getControlPanelView();//target
        assertInstanceOf(ControlPanelView.class,cpv);
    }

    @Test
    public void testWindowsView_getMapView() throws IOException {
        ArrayList<ArrayList<Integer>> mzm = generateMazeMap(entry,exit);
        WindowsView wv = new WindowsView(mzm,entry,exit);
        MazeMapViewer mv = wv.getMapViewer();//target
        assertInstanceOf(MazeMapViewer.class,mv);
    }

    @Test
    public void testMazeMapController_insertImage() throws IOException {
        ArrayList<ArrayList<Integer>> mzm = generateMazeMap(entry,exit);
        WindowsView wv = new WindowsView(mzm,entry,exit);
        MazeMapController mazeMapController = wv.getMapViewer().getController();
        mazeMapController.insertImage(exit, StringResources.tom);//target
    }

    @Test
    public void testMazeMapController_removeImage() throws IOException{
        ArrayList<ArrayList<Integer>> mzm = generateMazeMap(entry,exit);
        WindowsView wv = new WindowsView(mzm,entry,exit);
        MazeMapController mazeMapController = wv.getMapViewer().getController();
        mazeMapController.renderMap(entry,exit,exit,entry,false);

    }

    private ArrayList<ArrayList<Integer>> generateMazeMap(Location entry, Location exit){
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

        return mazeMap;
    }
}