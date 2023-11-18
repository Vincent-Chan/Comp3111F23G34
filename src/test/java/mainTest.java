import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class mainTest {

    @Test
    public void test_readData() throws IOException {
        ArrayList<ArrayList<Integer>> mazemap = main.readData("src/main/java/MazeData.csv", ","); //target
        assertEquals(30,mazemap.size());
        ArrayList<ArrayList<Integer>> mazemap_null = main.readData("src/main/java/xxx.csv", ","); //target
        assertEquals(null, mazemap_null);
    }

    @Test
    public void test_main() throws IOException {
        String args[] = new String[0];
        main.main(args); //target
    }
}