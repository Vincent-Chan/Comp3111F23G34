package game_algorithm;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class GameMapGeneratorTest {

    // Function A Unit Testing
    @Test
    public void GameMapGeneratorTest1()
    {
        GameMapGenerator mapGen = new GameMapGenerator("MazeData.csv") ;
    }

    // Function A Unit Testing
    @Test
    public void PrimMazeGeneratorTest1()
    {
        GameMapGenerator map_gen1 = new GameMapGenerator("MazeData.csv") ;
        map_gen1.rand.setSeed(3111) ;
        char[][] actual_maze1 = map_gen1.PrimMazeGenerator() ;

        char[][] expected_maze1 = {{'1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '0', '1', '1', '1', '0' },
            {'0', '0', '0', '1', '0', '0', '0', '1', '0', '1', '0', '0', '0', '0', '0', '0', '0', '1', '0', '1', '0', '0', '0', '0', '0', '1', '0', '0', '0', '1' },
            {'1', '1', '0', '1', '0', '1', '1', '1', '0', '1', '0', '1', '0', '1', '1', '0', '1', '1', '0', '1', '1', '1', '0', '1', '1', '1', '1', '1', '0', '1' },
            {'0', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '1', '0', '1', '0', '0', '0', '1', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '1' },
            {'1', '1', '0', '1', '0', '1', '0', '1', '1', '1', '1', '1', '1', '1', '1', '1', '0', '1', '0', '1', '0', '1', '0', '1', '1', '0', '1', '1', '1', '0' },
            {'0', '0', '0', '1', '0', '1', '0', '0', '0', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '1', '0', '1', '0', '0', '0', '0', '0', '1' },
            {'1', '1', '0', '1', '0', '1', '1', '1', '1', '1', '1', '1', '0', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '0', '1', '1', '1', '1', '1' },
            {'0', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '1' },
            {'0', '1', '1', '1', '0', '1', '1', '1', '1', '1', '0', '1', '0', '1', '1', '1', '0', '1', '0', '1', '0', '1', '1', '1', '0', '1', '1', '1', '0', '1' },
            {'0', '0', '0', '0', '0', '1', '0', '0', '0', '1', '0', '0', '0', '0', '0', '1', '0', '1', '0', '1', '0', '1', '0', '0', '0', '1', '0', '0', '0', '3' },
            {'0', '1', '1', '1', '0', '1', '0', '1', '1', '1', '0', '1', '1', '1', '0', '1', '0', '1', '1', '1', '1', '1', '1', '1', '0', '1', '0', '1', '0', '1' },
            {'2', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '1', '0', '1', '0', '0', '0', '1', '0', '1', '0', '1', '0', '0', '0', '1', '0', '1' },
            {'0', '1', '0', '0', '1', '1', '0', '1', '1', '1', '1', '1', '1', '1', '0', '0', '1', '1', '0', '1', '0', '1', '0', '1', '0', '1', '0', '1', '0', '1' },
            {'0', '1', '0', '1', '0', '0', '0', '0', '0', '0', '0', '1', '0', '1', '0', '0', '0', '1', '0', '0', '0', '1', '0', '0', '0', '1', '0', '1', '0', '1' },
            {'1', '1', '0', '1', '0', '1', '0', '1', '1', '1', '1', '1', '0', '1', '0', '1', '0', '1', '1', '1', '0', '1', '0', '1', '1', '1', '0', '1', '0', '1' },
            {'0', '0', '0', '1', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '1', '0', '1', '0', '0', '0', '1', '0', '1', '0', '0', '0', '1', '0', '1' },
            {'1', '1', '0', '1', '0', '1', '0', '1', '0', '1', '1', '1', '1', '1', '0', '1', '1', '1', '0', '1', '1', '1', '1', '1', '1', '1', '1', '1', '0', '1' },
            {'0', '0', '0', '1', '0', '1', '0', '1', '0', '0', '0', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '1', '0', '1', '0', '0', '0', '0', '0', '1' },
            {'0', '1', '0', '1', '1', '1', '1', '1', '0', '0', '0', '1', '1', '1', '0', '1', '1', '1', '1', '1', '1', '1', '0', '1', '1', '0', '1', '1', '0', '1' },
            {'0', '1', '0', '1', '0', '0', '0', '0', '0', '1', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '1', '0', '1', '0', '0', '0', '1', '0', '1' },
            {'1', '1', '0', '1', '0', '1', '0', '1', '0', '1', '0', '1', '1', '1', '0', '1', '1', '1', '1', '1', '1', '1', '0', '1', '0', '1', '1', '1', '1', '1' },
            {'0', '0', '0', '1', '0', '1', '0', '1', '0', '1', '0', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '1', '0', '1' },
            {'0', '1', '1', '1', '0', '1', '1', '1', '1', '1', '0', '1', '0', '0', '0', '1', '1', '1', '0', '1', '0', '0', '1', '1', '1', '1', '1', '1', '0', '1' },
            {'0', '0', '0', '1', '0', '0', '0', '0', '0', '1', '0', '1', '0', '1', '0', '0', '0', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '1' },
            {'0', '1', '1', '1', '0', '1', '1', '1', '1', '1', '0', '1', '0', '1', '0', '1', '0', '1', '0', '1', '1', '1', '1', '1', '0', '1', '0', '1', '0', '1' },
            {'0', '1', '0', '0', '0', '0', '0', '0', '0', '1', '0', '1', '0', '1', '0', '1', '0', '1', '0', '0', '0', '0', '0', '1', '0', '1', '0', '1', '0', '1' },
            {'1', '1', '1', '1', '0', '1', '0', '1', '1', '1', '0', '1', '0', '1', '1', '1', '1', '1', '0', '1', '1', '1', '1', '1', '0', '1', '1', '1', '1', '1' },
            {'0', '0', '0', '0', '0', '1', '0', '1', '0', '0', '0', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '1', '0', '0', '0', '0', '0', '1' },
            {'0', '1', '0', '1', '0', '1', '0', '1', '1', '1', '0', '1', '0', '1', '1', '1', '0', '1', '0', '1', '1', '1', '1', '1', '0', '1', '1', '1', '0', '1' },
            {'0', '1', '0', '1', '0', '1', '0', '0', '0', '1', '0', '1', '0', '1', '0', '0', '0', '1', '0', '0', '0', '1', '0', '0', '0', '0', '0', '1', '0', '1' }};


        assertArrayEquals(expected_maze1, actual_maze1) ;
    }

    // Function A Unit Testing
    @Test
    public void to_csvTest1() throws IOException
    {
        GameMapGenerator map_gen_csv1 = new GameMapGenerator("MazeData.csv") ;
        map_gen_csv1.rand.setSeed(3111) ;
        char[][] actual_csv_maze1 = map_gen_csv1.PrimMazeGenerator() ;

        map_gen_csv1.to_csv(actual_csv_maze1) ;
    }


}