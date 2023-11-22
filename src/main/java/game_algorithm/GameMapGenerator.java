/**
 * Before running the main function,
 * please remember to search for com.opencsv:openscv in "Dependencies",
 * and add com.opencsv:openscv to your dependency.
 * Also, remember to reload Maven so that the game can be run successfully.
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
 * The class GameMapGenerator involves 4 data members,
 * in which 1 of them is public and the remaining 3 of them are private.
 *
 * rand: store a java.util.Random class instance
 * ROW: store the number of rows in the maze
 * COL: stores the number of columns in the maze
 * maze_csv_file: stores the filename of the csv file
 *
 *
 *
 * This class also involves 1 constructor and 2 member functions.
 *
 * The constructor GameMapGenerator() construct a new Random instance and put it in the class's variable rand.
 * It also put the string stored in the input parameter "maze_csv_file" to the class's variable maze_csv_file.
 *
 *
 * The function PrimMazeGenerator() will create a random maze of size ROW*COL at anytime,
 * and will return the maze in a 2D char array.
 * PrimMazeGenerator() accepts no input parameter.
 * The return data type of PrimMazeGenerator() is a 2D char array, which stores the maze data.
 *
 * The function to_csv() will write the data of the maze to a csv file.
 * to_csv() accepts one input parameter of 2D char array type.
 * The return data type of to_csv() is void.
 *
 *
 *
 * To create a random maze map, programmers should create a GameMapGenerator class instance,
 * and then call the PrimMazeGenerator() method to generate a maze of 2D char array.
 *
 * To write the maze data to a csv file, programmers should call the to_csv() method by passing the
 * maze of 2D char array as the input parameter. Programmers then can see the maze data in the csv file
 * that they have specified.
 */

public class GameMapGenerator {

    /**
     * rand: stores a java.util.Random class instance
     */
    public Random rand;

    /**
     * ROW: a constant integer that stores the number of rows in the maze, which is always 30.
     */
    private final int ROW = 30;

    /**
     * COL: a constant integer that stores the number of columns in the maze, which is always 30.
     */
    private final int COL = 30;

    /**
     * maze_csv_file: stores the filename of the csv file in String data type
     */
    private String maze_csv_file;

    /**
     * GameMapGenerator() is a constructor for GameMapGenerator.
     * It initializes the data members "rand" by creating a new instance of java.util.Random class
     * It initializes the data members "this.maze_csv_file" by assigning maze_csv_file to it.
     *
     * @param maze_csv_file: The filename of the csv file that stores the maze data.
     */
    public GameMapGenerator(String maze_csv_file){
        rand = new Random();
        this.maze_csv_file = maze_csv_file;
    }

    /**
     * PrimMazeGenerator() is a member function for GameMapGenerator class.
     * It randomly generates a maze map at any time,
     * and return the data of the maze map as 2D char array.
     *
     * This maze generator uses the idea of Prim's Minimum Spanning Tree (MST) algorithm.
     *
     * @return maze: It stores the data of the randomly generated maze map.
     */
    public char[][] PrimMazeGenerator()
    {
        /**
         * no_of_barrier_need_to_removed: Number of barriers we want to break after using Prim's MST algorithm
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
         * row_step: A constant 1D integer array that represents the movement on row
         * col_step: A constant 1D integer array that represents the movement on column
         *
         * (row_step, col_step) = (-1, 0): corresponds to go upward (North)
         * (row_step, col_step) = (0, 1): corresponds to go rightward (East)
         * (row_step, col_step) = (1, 0): corresponds to go downward (South)
         * (row_step, col_step) = (0, -1): corresponds to go leftward (West)
         */
        // corresponds to North, East, South, West
        final int[] row_step = {-1, 0, 1, 0} ;
        final int[] col_step = {0, 1, 0, -1} ;


        /**
         * maze: It will store the data of the maze map after running the core part of the Prim's MST algorithm
         */
        char[][] maze = new char [ROW][COL] ;

        /**
         * Initialize the whole maze map with '1', i.e. barrier
         */
        for (int i = 0; i < ROW; ++i)
        {
            for (int j = 0; j < COL; ++j)
            {
                maze[i][j] = '1' ;
            }
        }

        /**
         * Randomly select one of the block at the leftmost column of the maze as the starting point (i.e. starting node)
         * Denote that starting point as '2'
         */
        Location start = new Location(rand.nextInt(ROW), 0) ;
        maze[start.row()][start.col()] = '2' ;

        /**
         * frontier: An array list that stores the frontier nodes when creating the path using Prim's MST algorithm
         * node2parent: A HashMap to the parent node of a child node
         */
        ArrayList<Location> frontier = new ArrayList<Location> () ;
        HashMap<Location, Location> node2parent = new HashMap<>();

        /**
         * Exhaust through North, East, South, West (neighbour) node from the starting point
         */
        for (int i = 0; i < row_step.length; ++i)
        {
            /**
             * Check whether the neighbour node from the starting point is within a valid range of the 2D char array
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
                 * If the neighbour node from the starting point is a barrier,
                 * create a Location class instance for that node and store it in newloc
                 * Add this neighbor node to frontier
                 * Declare that newloc is the child node of the starting node in the HashMap node2parent
                 */
                Location newloc = new Location(start.row() + row_step[i], start.col() + col_step[i]);
                frontier.add(newloc) ;
                node2parent.put(newloc,start);

            }
        }

        /**
         * last: The node that we finally go to at last in this iteration after we break the barriers (i.e. "current" and "opposite")
         * current: The current node
         * opposite: The node that is adjacent to "current" and opposite to the parent node of "current"
         *
         * Initialize last as null
         * Initialize current as null
         * Initialize opposite as null
         */
        Location last = null;
        Location current = null ;
        Location opposite = null ;

        /**
         * Exhaust through all the frontier nodes
         */
        while (frontier.isEmpty() == false)
        {
            /**
             * - Randomly select one of the nodes from the frontier and save it in "current"
             * - The opposite node ("opposite") of the current node ("current") is exactly the adjacent node of "current"
             * that it is opposite to the parent node of "current"
             */
            current = frontier.remove( rand.nextInt( frontier.size() ) ) ;
            opposite = current.opposite_node(node2parent.get(current)) ;

            /**
             * Check whether the opposite node ("opposite") is still within the valid range of the maze
             */
            if (opposite.row() >= 0 && opposite.row() < ROW && opposite.col() >= 0 && opposite.col() < COL)
            {
                /**
                 * Break the barriers into clear path only if both current node and opposite node are barriers
                 */
                if (maze[current.row()][current.col()] == '1' && maze[opposite.row()][opposite.col()] == '1') {
                    /**
                     * Set both current node and opposite node from barriers to clear path
                     */
                    maze[current.row()][current.col()] = '0';
                    maze[opposite.row()][opposite.col()] = '0';

                    /**
                     * Update the last node as opposite node
                     */
                    last = opposite;

                    /**
                     * Exhaust through North, East, South, West (neighbour) node from the opposite node
                     */
                    for (int i = 0; i < row_step.length; ++i) {
                        /**
                         * Check whether the neighbour node of the opposite node ("opposite") is still within the valid range of the maze
                         */
                        if ((opposite.row() + row_step[i]) >= 0 &&
                                (opposite.row() + row_step[i]) < ROW &&
                                (opposite.col() + col_step[i]) >= 0 &&
                                (opposite.col() + col_step[i]) < COL) {
                            /**
                             * If the neighbour node of the opposite node is already a clear path,
                             * do nothing and go to the next iteration
                             */
                            if (maze[opposite.row() + row_step[i]][opposite.col() + col_step[i]] == '0') {
                                continue;
                            }

                            /**
                             * If the neighbour node from the opposite point is a barrier,
                             * create a Location class instance for that node and store it in newloc
                             * Add this neighbor node to frontier
                             * Declare that newloc is the child node of the opposite node in the HashMap node2parent
                             */
                            Location newloc = new Location(opposite.row() + row_step[i], opposite.col() + col_step[i]);
                            frontier.add(newloc);
                            node2parent.put(newloc, opposite);
                        }
                    }
                }
            }

            /**
             * If we exhaust through all the frontier node,
             * set the last node as a clear path
             */
            if (frontier.isEmpty() == true)
            {
                maze[last.row()][last.col()] = '0' ;
            }

        }

        /**
         * flag_exit_placed: a flag indicating whether the exit point has been located on the map or not
         */
        boolean flag_exit_placed = false ;

        while (flag_exit_placed == false)
        {
            /**
             * The exit point should be on the rightmost column of the maze
             *
             * randomly select a row index number
             */
            int temp = rand.nextInt(ROW) ;

            /**
             * If the adjacent node (i.e. maze[temp][COL-2]) located on the left of the node maze[ROW][COL-1] is a clear path,
             * set the node maze[ROW][COL-1] as the exit point,
             * and update flag_exit_placed to true
             */
            if (maze[temp][COL-2] == '0')
            {
                maze[temp][COL-1] = '3' ;
                flag_exit_placed = true ;
            }
        }

        /**
         * Before we return the maze map data, randomly break no_of_barrier_need_to_removed of barriers
         */
        while (no_of_barrier_need_to_removed  > 0)
        {
            /**
             * Randomly select row index and store it in temp_row
             * Randomly select column index and store it in temp_col
             */
            int temp_row = rand.nextInt(ROW) ;
            int temp_col = rand.nextInt(COL) ;

            /**
             * If the node maze[temp_row][temp_col] is a barrier,
             * break the barrier by setting it to a clear path,
             * and reduce no_of_barrier_need_to_removed by 1
             */
            if (maze[temp_row][temp_col] == '1')
            {
                maze[temp_row][temp_col] = '0' ;
                --no_of_barrier_need_to_removed ;
            }
        }

        /**
         * Finally, return the maze map data as a 2D char array
         */
        return maze ;

    }

    /**
     * to_csv() is a member function for GameMapGenerator class.
     * It writes the maze map data generated by PrimMazeGenerator() to a csv file with the filename stored in this.maze_csv_file
     *
     * @param maze_data: the maze map data that are stored in a 2D char array
     * @throws IOException: In case we cannot successfully create a csv file and write the maze data into the csv file,
     * the program would throw Input/Output Exception Error
     */
    public void to_csv(char[][] maze_data) throws IOException{
        // Use "throws IOException" instead of try ... catch ...

        /**
         * Create a File class instance,
         * and store it in file
         */
        File file = new File(this.maze_csv_file);

        /**
         * Create a FileWriter class instance,
         * and store it in outputfile
         */
        FileWriter outputfile = new FileWriter(file);

        /**
         * Create a CSVWriter class instance,
         * and store it in writer
         */
        CSVWriter writer = new CSVWriter(outputfile);

        /**
         * Create a 1D String array (temporary_string_arr) with size = maze_data[0].length = 30
         */
        String[] temporary_string_arr = new String[maze_data[0].length] ;

        /**
         * Create a List (data) that stores 1D String array
         */
        List<String[]> data = new ArrayList<String[]>() ;


        /**
         * Use nested for loops to loop through each element in the maze map
         */
        for (int i = 0; i < maze_data.length; ++i)
        {
            for (int j = 0; j < maze_data[0].length; ++j)
            {
                /**
                 * Store each row of maze map data into the 1D String array (temporary_string_arr)
                 */
                temporary_string_arr[j] = Character.toString(maze_data[i][j]) ;
            }

            /**
             * Add the 1D String array (temporary_string_arr) into the List (data)
             */
            data.add(temporary_string_arr) ;

            /**
             * Write the List (data) that contains the i-th row of maze data to the csv file,
             * and then clear all information that stored inside the List (data)
             */
            writer.writeAll(data);
            data.clear() ;
        }

        /**
         * Close the writer
         */
        writer.close() ;

    }
}

