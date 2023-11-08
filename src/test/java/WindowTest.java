import org.junit.Test;

import static org.junit.Assert.*;

public class WindowTest {

    @Test
    void WindowTest1()
    {
        MazeGenerator.rand.setSeed(3111) ;

        char[][] maze_data_1 = MazeGenerator.PrimMazeGenerator() ;

        Window win1 = new Window(maze_data_1) ;

    }

}