/**
 * <p>Before running the main function,
 * please remember to search for com.opencsv:openscv in "Dependencies",
 * and add com.opencsv:openscv to your dependency.</p>
 * <p>Also, remember to reload Maven so that the game can be run successfully.</p>
 */

package game_algorithm;
import game_states.Location;
import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * <p>The class GameMapGenerator involves 4 data members,
 * in which 1 of them is public and the remaining 3 of them are private.</p>
 *
 * <p>rand: store a java.util.Random class instance</p>
 * <p>ROW: store the number of rows in the maze</p>
 * <p>COL: stores the number of columns in the maze</p>
 * <p>maze_csv_file: stores the filename of the csv file</p>
 *
 *
 *
 * <p>This class also involves 1 constructor and 2 member functions.</p>
 *
 * <p>The constructor GameMapGenerator() construct a new Random instance and put it in the class's variable rand.
 * It also put the string stored in the input parameter "maze_csv_file" to the class's variable maze_csv_file.</p>
 *
 *
 * <p>The function PrimMazeGenerator() will create a random maze of size ROW*COL at anytime,
 * and will return the maze in a 2D char array.</p>
 * <p>PrimMazeGenerator() accepts no input parameter.</p>
 * <p>The return data type of PrimMazeGenerator() is a 2D char array, which stores the maze data.</p>
 *
 * <p>The function to_csv() will write the data of the maze to a csv file.</p>
 * <p>to_csv() accepts one input parameter of 2D char array type.</p>
 * <p>The return data type of to_csv() is void.</p>
 *
 *
 *
 * <p>To create a random maze map, programmers should create a GameMapGenerator class instance,
 * and then call the PrimMazeGenerator() method to generate a maze of 2D char array.</p>
 *
 * <p>To write the maze data to a csv file, programmers should call the to_csv() method by passing the
 * maze of 2D char array as the input parameter. Programmers then can see the maze data in the csv file
 * that they have specified.</p>
 */

public class GameMapGenerator {

    /**
     * <p>rand: stores a java.util.Random class instance</p>
     */
    public Random rand;

    /**
     * <p>ROW: a constant integer that stores the number of rows in the maze, which is always 30.</p>
     */
    private final int ROW = 30;

    /**
     * <p>COL: a constant integer that stores the number of columns in the maze, which is always 30.</p>
     */
    private final int COL = 30;

    /**
     * <p>maze_csv_file: stores the filename of the csv file in String data type</p>
     */
    private String maze_csv_file;

    /**
     * <p>GameMapGenerator() is a constructor for GameMapGenerator.</p>
     *
     * <p>It initializes the data members "rand" by creating a new instance of java.util.Random class</p>
     * <p>It initializes the data members "this.maze_csv_file" by assigning maze_csv_file to it.</p>
     *
     * @param maze_csv_file: The filename of the csv file that stores the maze data.
     */
    public GameMapGenerator(String maze_csv_file){
        rand = new Random();
        this.maze_csv_file = maze_csv_file;
    }

    /**
     * <p>PrimMazeGenerator() is a member function for GameMapGenerator class.</p>
     *
     * <p>It randomly generates a maze map at any time,
     * and return the data of the maze map as 2D char array.</p>
     *
     * <p>This maze generator uses the idea of Prim's Minimum Spanning Tree (MST) algorithm.</p>
     *
     * @return maze: It stores the data of the randomly generated maze map.
     */
    public char[][] PrimMazeGenerator()
    {
        /**
         * <p>no_of_barrier_need_to_removed: Number of barriers we want to break after using Prim's MST algorithm</p>
         */
        int no_of_barrier_need_to_removed = 18 ;

        // Random rand = new Random() ;

        // set the seed number as 3111 seems to generate a very good maze
        // long seed_number = 3111 ;
        // rand.setSeed(seed_number) ;

        // if maze[i][j] = '0', it is a clear path in white color
        // if maze[i][j] = '1', it is a barrier in grey color
        // if maze[i][j] = '2', it is the starting point for the mouse Jerry
        // if maze[i][j] = '3', it is the exit point where the mouse Jerry should go to

        /**
         * <p>row_step: A constant 1D integer array that represents the movement on row</p>
         * <p>col_step: A constant 1D integer array that represents the movement on column</p>
         *
         * <p>(row_step, col_step) = (-1, 0): corresponds to go upward (North)</p>
         * <p>(row_step, col_step) = (0, 1): corresponds to go rightward (East)</p>
         * <p>(row_step, col_step) = (1, 0): corresponds to go downward (South)</p>
         * <p>(row_step, col_step) = (0, -1): corresponds to go leftward (West)</p>
         */
        // corresponds to North, East, South, West
        final int[] row_step = {-1, 0, 1, 0} ;
        final int[] col_step = {0, 1, 0, -1} ;


        /**
         * <p>maze: It will store the data of the maze map after running the core part of the Prim's MST algorithm</p>
         */
        char[][] maze = new char [ROW][COL] ;

        /**
         * <p>Initialize the whole maze map with '1', i.e. barrier</p>
         */
        for (int i = 0; i < ROW; ++i)
        {
            for (int j = 0; j < COL; ++j)
            {
                maze[i][j] = '1' ;
            }
        }

        /**
         * <p>Randomly select one of the block at the leftmost column of the maze as the starting point (i.e. starting node)</p>
         * <p>Denote that starting point as '2'</p>
         */
        Location start = new Location(rand.nextInt(ROW), 0) ;
        maze[start.row()][start.col()] = '2' ;

        /**
         * <p>frontier: An array list that stores the frontier nodes when creating the path using Prim's MST algorithm</p>
         *
         * <p>node2parent: A HashMap to the parent node of a child node</p>
         */
        ArrayList<Location> frontier = new ArrayList<Location> () ;
        HashMap<Location, Location> node2parent = new HashMap<>();

        /**
         * <p>Exhaust through North, East, South, West (neighbour) node from the starting point<p>
         */
        for (int i = 0; i < row_step.length; ++i)
        {
            /**
             * <p>Check whether the neighbour node from the starting point is within a valid range of the 2D char array</p>
             */
            if ( (start.row() + row_step[i]) >= 0 &&
                    (start.row() + row_step[i]) < ROW &&
                    (start.col() + col_step[i]) >= 0 &&
                    (start.col() + col_step[i]) < COL )
            {
                // /**
                // * If the neighbour node from the starting point is already a clear path,
                // * do nothing and go to the next iteration
                // */
                // if (maze[start.row() + row_step[i]][start.col() + col_step[i]] == '0')
                // {
                //     continue ;
                // }

                /**
                 * <p>If the neighbour node from the starting point is a barrier,
                 * create a Location class instance for that node and store it in newloc</p>
                 * <p>Add this neighbor node to frontier</p>
                 * <p>Declare that newloc is the child node of the starting node in the HashMap node2parent</p>
                 */
                Location newloc = new Location(start.row() + row_step[i], start.col() + col_step[i]);
                frontier.add(newloc) ;
                node2parent.put(newloc,start);

            }
        }

        /**
         * <p>last: The node that we finally go to at last in this iteration after we break the barriers (i.e. "current" and "opposite")</p>
         * <p>current: The current node</p>
         * <p>opposite: The node that is adjacent to "current" and opposite to the parent node of "current"</p>
         *
         * <p>Initialize last as null</p>
         * <p>Initialize current as null</p>
         * <p>Initialize opposite as null</p>
         */
        Location last = null;
        Location current = null ;
        Location opposite = null ;

        /**
         * <p>Exhaust through all the frontier nodes</p>
         */
        while (frontier.isEmpty() == false)
        {
            /**
             * <p>- Randomly select one of the nodes from the frontier and save it in "current"</p>
             * <p>- The opposite node ("opposite") of the current node ("current") is exactly the adjacent node of "current"
             * that it is opposite to the parent node of "current"</p>
             */
            current = frontier.remove( rand.nextInt( frontier.size() ) ) ;
            opposite = current.opposite_node(node2parent.get(current)) ;

            /**
             * <p>Check whether the opposite node ("opposite") is still within the valid range of the maze</p>
             */
            if (opposite.row() >= 0 && opposite.row() < ROW && opposite.col() >= 0 && opposite.col() < COL)
            {
                /**
                 * <p>Break the barriers into clear path only if both current node and opposite node are barriers</p>
                 */
                if (maze[current.row()][current.col()] == '1' && maze[opposite.row()][opposite.col()] == '1') {
                    /**
                     * <p>Set both current node and opposite node from barriers to clear path</p>
                     */
                    maze[current.row()][current.col()] = '0';
                    maze[opposite.row()][opposite.col()] = '0';

                    /**
                     * <p>Update the last node as opposite node</p>
                     */
                    last = opposite;

                    /**
                     * <p>Exhaust through North, East, South, West (neighbour) node from the opposite node</p>
                     */
                    for (int i = 0; i < row_step.length; ++i) {
                        /**
                         * <p>Check whether the neighbour node of the opposite node ("opposite") is still within the valid range of the maze</p>
                         */
                        if ((opposite.row() + row_step[i]) >= 0 &&
                                (opposite.row() + row_step[i]) < ROW &&
                                (opposite.col() + col_step[i]) >= 0 &&
                                (opposite.col() + col_step[i]) < COL) {
                            /**
                             * <p>If the neighbour node of the opposite node is already a clear path,
                             * do nothing and go to the next iteration</p>
                             */
                            if (maze[opposite.row() + row_step[i]][opposite.col() + col_step[i]] == '0') {
                                continue;
                            }

                            /**
                             * <p>If the neighbour node from the opposite point is a barrier,
                             * create a Location class instance for that node and store it in newloc</p>
                             * <p>Add this neighbor node to frontier</p>
                             * <p>Declare that newloc is the child node of the opposite node in the HashMap node2parent</p>
                             */
                            Location newloc = new Location(opposite.row() + row_step[i], opposite.col() + col_step[i]);
                            frontier.add(newloc);
                            node2parent.put(newloc, opposite);
                        }
                    }
                }
            }

            /**
             * <p>If we exhaust through all the frontier node,
             * set the last node as a clear path</p>
             */
            if (frontier.isEmpty() == true)
            {
                maze[last.row()][last.col()] = '0' ;
            }

        }

        /**
         * <p>flag_exit_placed: a flag indicating whether the exit point has been located on the map or not</p>
         */
        boolean flag_exit_placed = false ;

        while (flag_exit_placed == false)
        {
            /**
             * <p>The exit point should be on the rightmost column of the maze</p>
             *
             * <p>randomly select a row index number</p>
             */
            int temp = rand.nextInt(ROW) ;

            /**
             * <p>If the adjacent node (i.e. maze[temp][COL-2]) located on the left of the node maze[ROW][COL-1] is a clear path,
             * set the node maze[ROW][COL-1] as the exit point,
             * and update flag_exit_placed to true</p>
             */
            if (maze[temp][COL-2] == '0')
            {
                maze[temp][COL-1] = '3' ;
                flag_exit_placed = true ;
            }
        }

        /**
         * <p>Before we return the maze map data, randomly break no_of_barrier_need_to_removed of barriers</p>
         */
        while (no_of_barrier_need_to_removed  > 0)
        {
            /**
             * <p>Randomly select row index and store it in temp_row</p>
             * <p>Randomly select column index and store it in temp_col</p>
             */
            int temp_row = rand.nextInt(ROW) ;
            int temp_col = rand.nextInt(COL) ;

            /**
             * <p>If the node maze[temp_row][temp_col] is a barrier,
             * break the barrier by setting it to a clear path,
             * and reduce no_of_barrier_need_to_removed by 1</p>
             */
            if (maze[temp_row][temp_col] == '1')
            {
                maze[temp_row][temp_col] = '0' ;
                --no_of_barrier_need_to_removed ;
            }
        }

        /**
         * <p>Finally, return the maze map data as a 2D char array</p>
         */
        return maze ;

    }

    /**
     * <p>to_csv() is a member function for GameMapGenerator class.</p>
     * <p>It writes the maze map data generated by PrimMazeGenerator() to a csv file with the filename stored in this.maze_csv_file</p>
     *
     * @param maze_data the maze map data that are stored in a 2D char array
     * @throws IOException In case we cannot successfully create a csv file and write the maze data into the csv file,
     * the program would throw Input/Output Exception Error
     */
    public void to_csv(char[][] maze_data) throws IOException{
        // Use "throws IOException" instead of try ... catch ...

        /**
         * <p>Create a File class instance,
         * and store it in file</p>
         */
        File file = new File(this.maze_csv_file);

        /**
         * <p>Create a FileWriter class instance,
         * and store it in outputfile</p>
         */
        FileWriter outputfile = new FileWriter(file);

        /**
         * <p>Create a CSVWriter class instance,
         * and store it in writer</p>
         */
        CSVWriter writer = new CSVWriter(outputfile);

        /**
         * <p>Create a 1D String array (temporary_string_arr) with size = maze_data[0].length = 30</p>
         */
        String[] temporary_string_arr = new String[maze_data[0].length] ;

        /**
         * <p>Create a List (data) that stores 1D String array</p>
         */
        List<String[]> data = new ArrayList<String[]>() ;


        /**
         * <p>Use nested for loops to loop through each element in the maze map</p>
         */
        for (int i = 0; i < maze_data.length; ++i)
        {
            for (int j = 0; j < maze_data[0].length; ++j)
            {
                /**
                 * <p>Store each row of maze map data into the 1D String array (temporary_string_arr)</p>
                 */
                temporary_string_arr[j] = Character.toString(maze_data[i][j]) ;
            }

            /**
             * <p>Add the 1D String array (temporary_string_arr) into the List (data)</p>
             */
            data.add(temporary_string_arr) ;

            /**
             * <p>Write the List (data) that contains the i-th row of maze data to the csv file,
             * and then clear all information that stored inside the List (data)</p>
             */
            writer.writeAll(data);
            data.clear() ;
        }

        /**
         * <p>Close the writer</p>
         */
        writer.close() ;

    }
}

