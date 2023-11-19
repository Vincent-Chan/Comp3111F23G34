import game_algorithm.GameMapGenerator;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
public class GameInstanceTest {
    public static final String MAP_FILE_PATH = "src/main/java/MazeData_testing.csv";
    public static final String SP_OUTPUT_PATH = "src/main/java/shortest_path_at_beginning_testing.csv";

    @Test
    public void test_GameInstanceConstructor() throws IOException {
        TomJerryGame tjg = new TomJerryGame(MAP_FILE_PATH, SP_OUTPUT_PATH); //target
        assertEquals(30,tjg.mazeMap.size());
    }

    @Test
    public void test_readData_invalidmap() throws IOException {
        TomJerryGame tjg = new TomJerryGame(MAP_FILE_PATH, SP_OUTPUT_PATH);
        ArrayList<ArrayList<Integer>> mazeMap = tjg.readData("", ","); //target
        assertNull(mazeMap);
    }

    @Test
    public void test_selectDifficulty() throws IOException {
        TomJerryGame tjg = new TomJerryGame(MAP_FILE_PATH, SP_OUTPUT_PATH);
        String[] difficultyModes = {"Easy", "Medium", "Hard","xxx"};
        for(int i = 0;i<difficultyModes.length;i++){
            String mode = difficultyModes[i];
            tjg.setDifficulty(true,mode);//target
        }



    }
}
