import org.junit.Test;

import static org.junit.Assert.*;

public class MazeDataToCSVTest {

    @Test
    void toCSVTest1()
    {
        MazeGenerator.rand.setSeed(3111) ;

        char[][] maze1 = MazeGenerator.PrimMazeGenerator() ;

        MazeDataToCSV.toCSV(maze1) ;
    }

}