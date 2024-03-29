package game_algorithm;
import game_states.Location;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class ShortestPathGeneratorTest {

    public GameMapGenerator map_gen1 = new GameMapGenerator("src/main/java/MazeData.csv");
    @Before
    public void init_map() throws IOException
    {
        map_gen1.rand.setSeed(3111) ;
        char[][] maze_data = map_gen1.PrimMazeGenerator();
        map_gen1.to_csv(maze_data);
    }

    @Test
    public void test_constructor() {
        Location entry = new Location(11,0);
        Location exit = new Location(9,29);
        ShortestPathGenerator obj = new ShortestPathGenerator("src/main/java/MazeData.csv", "src/main/java/shortest_path_at_beginning.csv"); //target function
    }

    @Test
    public void test_calculate_shortest_path() {
        // normal path
        Location entry1 = new Location(11,0);
        Location exit1 = new Location(9,29);
        ShortestPathGenerator obj1 = new ShortestPathGenerator("src/main/java/MazeData.csv", "src/main/java/shortest_path_at_beginning.csv");
        ArrayList<Location> actual_output1 = obj1.calculate_shortest_path(entry1, exit1); // target function
        ArrayList<Location> expected_output1 = new ArrayList<Location>();
        int[][] temp = new int[][]{{11,0},{10,0},{9,0},{9,1},{9,2},{9,3},{9,4},{8,4},{7,4},{7,5},{7,6},{7,7},{7,8},{7,9},{7,10},{7,11},{7,12},{7,13},{7,14},{7,15},{7,16},{7,17},{7,18},{7,19},{7,20},{7,21},{7,22},{7,23},{7,24},{7,25},{7,26},{7,27},{7,28},{8,28},{9,28},{9,29}};
        for (int i = 0; i < 36; i++)
        {
            expected_output1.add(new Location(temp[i][0], temp[i][1]));
        }
        assertArrayEquals(actual_output1.toArray(), expected_output1.toArray());

        // Same entry and exit point
        Location entry2 = new Location(11,0);
        Location exit2 = new Location(11,0);
        ShortestPathGenerator obj2 = new ShortestPathGenerator("src/main/java/MazeData.csv", "src/main/java/shortest_path_at_beginning.csv");
        ArrayList<Location> actual_output2 = obj2.calculate_shortest_path(entry2, exit2); // target function
        ArrayList<Location> expected_output2 = null;
        assertEquals(actual_output2, expected_output2);

        // No path
        Location entry3 = new Location(11,0);
        Location exit3 = new Location(0,29);
        ShortestPathGenerator obj3 = new ShortestPathGenerator("src/main/java/MazeData.csv", "src/main/java/shortest_path_at_beginning.csv");
        ArrayList<Location> actual_output3 = obj3.calculate_shortest_path(entry3, exit3); // target function
        ArrayList<Location> expected_output3 = null;
        assertEquals(actual_output3, expected_output3);
    }

    @Test(expected = RuntimeException.class)
    public void test_output_file()
    {
        // valid directory
        Location entry1 = new Location(11,0);
        Location exit1 = new Location(9,29);
        ShortestPathGenerator obj1 = new ShortestPathGenerator("src/main/java/MazeData.csv", "src/main/java/shortest_path_at_beginning.csv");
        ArrayList<Location> actual_output1 = obj1.calculate_shortest_path(entry1, exit1);
        ArrayList<Location> Shortest_path_vertices = new ArrayList<Location>();
        int[][] temp1 = new int[][]{{11,0},{10,0},{9,0},{9,1},{9,2},{9,3},{9,4},{8,4},{7,4},{7,5},{7,6},{7,7},{7,8},{7,9},{7,10},{7,11},{7,12},{7,13},{7,14},{7,15},{7,16},{7,17},{7,18},{7,19},{7,20},{7,21},{7,22},{7,23},{7,24},{7,25},{7,26},{7,27},{7,28},{8,28},{9,28},{9,29}};
        for (int i = 0; i < 36; i++)
        {
            Shortest_path_vertices.add(new Location(temp1[i][0], temp1[i][1]));
        }
        obj1.output_file(Shortest_path_vertices); // target function

        // invalid directory
        ShortestPathGenerator obj2 = new ShortestPathGenerator("src/main/java/MazeData.csv","srcd/main/java/shortest_path_at_beginning.csv");
        obj2.output_file(Shortest_path_vertices); // target function
    }

    @Test
    public void test_output_array() {
        Location entry1 = new Location(11,0);
        Location exit1 = new Location(9,29);
        ShortestPathGenerator obj1 = new ShortestPathGenerator("src/main/java/MazeData.csv", "src/main/java/shortest_path_at_beginning.csv");
        obj1.calculate_shortest_path(entry1, exit1);

        int[][][] previous = new int[][][]
                {{{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0}},
                        {{1,1},{1,2},{2,2},{0,0},{2,4},{1,4},{1,5},{0,0},{2,8},{0,0},{2,10},{1,10},{1,11},{1,12},{1,13},{1,14},{1,15},{0,0},{2,18},{0,0},{1,21},{1,22},{2,22},{1,22},{1,23},{0,0},{0,0},{0,0},{0,0},{0,0}},
                        {{0,0},{0,0},{3,2},{0,0},{3,4},{0,0},{0,0},{0,0},{3,8},{0,0},{3,10},{0,0},{1,12},{0,0},{0,0},{1,15},{0,0},{0,0},{3,18},{0,0},{0,0},{0,0},{3,22},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0}},
                        {{3,1},{3,2},{4,2},{0,0},{4,4},{3,4},{3,5},{3,6},{3,7},{3,8},{3,9},{0,0},{2,12},{0,0},{3,15},{3,16},{4,16},{0,0},{4,18},{0,0},{4,20},{3,20},{3,21},{3,22},{3,23},{3,24},{3,25},{3,26},{0,0},{0,0}},
                        {{0,0},{0,0},{5,2},{0,0},{5,4},{0,0},{3,6},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{5,16},{0,0},{5,18},{0,0},{5,20},{0,0},{3,22},{0,0},{0,0},{5,25},{0,0},{0,0},{0,0},{0,0}},
                        {{5,1},{5,2},{6,2},{0,0},{6,4},{0,0},{4,6},{5,6},{5,7},{5,8},{5,9},{0,0},{6,12},{5,12},{5,13},{5,14},{5,15},{5,16},{5,17},{5,18},{5,19},{0,0},{4,22},{0,0},{6,24},{5,24},{5,25},{5,26},{5,27},{0,0}},
                        {{0,0},{0,0},{7,2},{0,0},{7,4},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{7,12},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{7,24},{0,0},{0,0},{0,0},{0,0},{0,0}},
                        {{8,0},{7,0},{7,1},{0,0},{8,4},{7,4},{7,5},{7,6},{7,7},{7,8},{7,9},{7,10},{7,11},{7,12},{7,13},{7,14},{7,15},{7,16},{7,17},{7,18},{7,19},{7,20},{7,21},{7,22},{7,23},{7,24},{7,25},{7,26},{7,27},{0,0}},
                        {{9,0},{0,0},{0,0},{0,0},{9,4},{0,0},{0,0},{0,0},{0,0},{0,0},{7,10},{0,0},{7,12},{0,0},{0,0},{0,0},{7,16},{0,0},{7,18},{0,0},{7,20},{0,0},{0,0},{0,0},{7,24},{0,0},{0,0},{0,0},{7,28},{0,0}},
                        {{10,0},{9,0},{9,1},{9,2},{9,3},{0,0},{10,6},{9,6},{9,7},{0,0},{8,10},{9,10},{9,11},{9,12},{9,13},{0,0},{8,16},{0,0},{8,18},{0,0},{8,20},{0,0},{9,23},{9,24},{8,24},{0,0},{0,0},{9,28},{8,28},{9,28}},
                        {{11,0},{0,0},{0,0},{0,0},{9,4},{0,0},{11,6},{0,0},{0,0},{0,0},{11,10},{0,0},{0,0},{0,0},{9,14},{0,0},{9,16},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{9,24},{0,0},{11,26},{0,0},{9,28},{0,0}},
                        {{-1,-1},{11,0},{11,1},{0,0},{10,4},{11,4},{11,5},{11,6},{11,7},{11,8},{11,9},{11,10},{11,11},{0,0},{10,14},{0,0},{10,16},{11,16},{11,17},{0,0},{12,20},{0,0},{0,0},{0,0},{10,24},{11,24},{11,25},{0,0},{0,0},{0,0}},
                        {{11,0},{0,0},{11,2},{12,2},{0,0},{0,0},{11,6},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{11,14},{12,14},{0,0},{0,0},{11,18},{0,0},{13,20},{0,0},{0,0},{0,0},{11,24},{0,0},{11,26},{0,0},{0,0},{0,0}},
                        {{12,0},{0,0},{12,2},{0,0},{13,5},{13,6},{12,6},{13,6},{13,7},{13,8},{13,9},{0,0},{14,12},{0,0},{12,14},{13,14},{13,15},{0,0},{12,18},{13,18},{13,19},{0,0},{0,0},{13,24},{12,24},{0,0},{0,0},{0,0},{0,0},{0,0}},
                        {{0,0},{0,0},{13,2},{0,0},{13,4},{0,0},{13,6},{0,0},{0,0},{0,0},{0,0},{0,0},{15,12},{0,0},{15,14},{0,0},{13,16},{0,0},{0,0},{0,0},{13,20},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0}},
                        {{15,1},{15,2},{14,2},{0,0},{14,4},{0,0},{14,6},{15,6},{15,7},{15,8},{15,9},{15,10},{15,11},{15,12},{15,13},{0,0},{14,16},{0,0},{16,18},{15,18},{14,20},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0}},
                        {{0,0},{0,0},{15,2},{0,0},{15,4},{0,0},{15,6},{0,0},{15,8},{0,0},{0,0},{0,0},{0,0},{0,0},{15,14},{0,0},{0,0},{0,0},{17,18},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0}},
                        {{17,1},{17,2},{16,2},{0,0},{16,4},{0,0},{16,6},{0,0},{16,8},{17,8},{17,9},{17,10},{17,11},{0,0},{16,14},{17,14},{17,15},{17,16},{17,17},{17,18},{17,19},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0}},
                        {{17,0},{0,0},{17,2},{0,0},{0,0},{0,0},{0,0},{0,0},{17,8},{18,8},{18,9},{0,0},{0,0},{0,0},{17,14},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0}},
                        {{18,0},{0,0},{18,2},{0,0},{19,5},{19,6},{19,7},{19,8},{18,8},{0,0},{18,10},{0,0},{19,13},{19,14},{18,14},{19,14},{19,15},{19,16},{19,17},{19,18},{19,19},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0}},
                        {{0,0},{0,0},{19,2},{0,0},{19,4},{0,0},{19,6},{0,0},{19,8},{0,0},{19,10},{0,0},{0,0},{0,0},{19,14},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0}},
                        {{21,1},{21,2},{20,2},{0,0},{20,4},{0,0},{20,6},{0,0},{20,8},{0,0},{20,10},{21,10},{21,11},{0,0},{20,14},{21,14},{21,15},{21,16},{21,17},{21,18},{21,19},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0}},
                        {{21,0},{0,0},{0,0},{0,0},{21,4},{0,0},{0,0},{0,0},{0,0},{0,0},{21,10},{0,0},{21,12},{22,12},{22,13},{0,0},{0,0},{0,0},{21,18},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0}},
                        {{22,0},{23,0},{23,1},{0,0},{22,4},{23,4},{23,5},{23,6},{23,7},{0,0},{22,10},{0,0},{22,12},{0,0},{22,14},{23,14},{23,15},{23,16},{23,17},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0}},
                        {{23,0},{0,0},{0,0},{0,0},{23,4},{0,0},{0,0},{0,0},{0,0},{0,0},{23,10},{0,0},{23,12},{0,0},{23,14},{0,0},{23,16},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0}},
                        {{24,0},{0,0},{25,3},{25,4},{24,4},{25,4},{25,5},{25,6},{25,7},{0,0},{24,10},{0,0},{24,12},{0,0},{24,14},{0,0},{24,16},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0}},
                        {{0,0},{0,0},{0,0},{0,0},{25,4},{0,0},{25,6},{0,0},{0,0},{0,0},{25,10},{0,0},{25,12},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0}},
                        {{0,0},{0,0},{27,3},{27,4},{26,4},{0,0},{26,6},{0,0},{27,9},{27,10},{26,10},{27,10},{27,11},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0}},
                        {{0,0},{0,0},{0,0},{0,0},{27,4},{0,0},{0,0},{0,0},{0,0},{0,0},{27,10},{0,0},{27,12},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0}},
                        {{0,0},{0,0},{0,0},{0,0},{28,4},{0,0},{0,0},{0,0},{0,0},{0,0},{28,10},{0,0},{28,12},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0}}};

        ArrayList<Location> Shortest_path_vertices = new ArrayList<Location>();
        int[][] temp1 = new int[][]{{11,0},{10,0},{9,0},{9,1},{9,2},{9,3},{9,4},{8,4},{7,4},{7,5},{7,6},{7,7},{7,8},{7,9},{7,10},{7,11},{7,12},{7,13},{7,14},{7,15},{7,16},{7,17},{7,18},{7,19},{7,20},{7,21},{7,22},{7,23},{7,24},{7,25},{7,26},{7,27},{7,28},{8,28},{9,28},{9,29}};
        for (int i = 0; i < 36; i++)
        {
            Shortest_path_vertices.add(new Location(temp1[i][0], temp1[i][1]));
        }
        ArrayList<Location> temp = new ArrayList<>();
        assertArrayEquals(obj1.output_array(entry1, exit1, previous, temp).toArray(), Shortest_path_vertices.toArray()); // target function
    }

    @Test(expected = RuntimeException.class)
    public void test_load_csv()
    {
        // valid directory
        Location entry1 = new Location(11,0);
        Location exit1 = new Location(9,29);
        ShortestPathGenerator obj1 = new ShortestPathGenerator("src/main/java/MazeData.csv", "src/main/java/shortest_path_at_beginning.csv");
        obj1.load_csv("src/main/java/MazeData.csv");

        // invalid directory
        Location entry2 = new Location(11,0);
        Location exit2 = new Location(9,29);
        ShortestPathGenerator obj2 = new ShortestPathGenerator("srcc/main/java/MazeData.csv", "src/main/java/shortest_path_at_beginning.csv");
        obj2.load_csv("srcc/main/java/MazeData.csv");
    }

}