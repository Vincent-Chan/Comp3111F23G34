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
public class GameMapGenerator {
    public Random rand;
    private final int ROW = 30;
    private final int COL = 30;
    private String maze_csv_file;
    public GameMapGenerator(String maze_csv_file){
        rand = new Random();
        this.maze_csv_file = maze_csv_file;
    }
    public char[][] PrimMazeGenerator()
    {
        int no_of_barrier_need_to_removed = 18 ;

        // Random rand = new Random() ;

        // set the seed number as 3111 seems to generate a very good maze
        // long seed_number = 3111 ;
        // rand.setSeed(seed_number) ;

        // if maze[i][j] = '0', it is a clear path in white color
        // if maze[i][j] = '1', it is a barrier in grey color
        // if maze[i][j] = '2', it is the starting point for the mouse Jerry
        // if maze[i][j] = '3', it is the exit point where the mouse Jerry should go to


        // corresponds to North, East, South, West
        final int[] row_step = {-1, 0, 1, 0} ;
        final int[] col_step = {0, 1, 0, -1} ;

        char[][] maze = new char [ROW][COL] ;

        for (int i = 0; i < ROW; ++i)
        {
            for (int j = 0; j < COL; ++j)
            {
                maze[i][j] = '1' ;
            }
        }

        Location start = new Location(rand.nextInt(ROW), 0) ;
        maze[start.row()][start.col()] = '2' ;

        ArrayList<Location> frontier = new ArrayList<Location> () ;
        HashMap<Location, Location> node2parent = new HashMap<>();
        for (int i = 0; i < row_step.length; ++i)
        {
            if ( (start.row() + row_step[i]) >= 0 &&
                    (start.row() + row_step[i]) < ROW &&
                    (start.col() + col_step[i]) >= 0 &&
                    (start.col() + col_step[i]) < COL )
            {
                if (maze[start.row() + row_step[i]][start.col() + col_step[i]] == '0')
                {
                    continue ;
                }

                Location newloc = new Location(start.row() + row_step[i], start.col() + col_step[i]);
                frontier.add(newloc) ;
                node2parent.put(newloc,start);


            }
        }

        Location last = null;
        Location current = null ;
        Location opposite = null ;


        while (frontier.isEmpty() == false)
        {
            current = frontier.remove( rand.nextInt( frontier.size() ) ) ;
            opposite = current.opposite_node(node2parent.get(current)) ;

            if (opposite.row() >= 0 && opposite.row() < ROW && opposite.col() >= 0 && opposite.col() < COL)
            {
                if (maze[current.row()][current.col()] == '1' && maze[opposite.row()][opposite.col()] == '1') {

                    maze[current.row()][current.col()] = '0';
                    maze[opposite.row()][opposite.col()] = '0';

                    last = opposite;

                    for (int i = 0; i < row_step.length; ++i) {
                        if ((opposite.row() + row_step[i]) >= 0 &&
                                (opposite.row() + row_step[i]) < ROW &&
                                (opposite.col() + col_step[i]) >= 0 &&
                                (opposite.col() + col_step[i]) < COL) {
                            if (maze[opposite.row() + row_step[i]][opposite.col() + col_step[i]] == '0') {
                                continue;
                            }
                            Location newloc = new Location(opposite.row() + row_step[i], opposite.col() + col_step[i]);
                            frontier.add(newloc);
                            node2parent.put(newloc, opposite);
                        }
                    }
                }
            }


            if (frontier.isEmpty() == true)
            {
                maze[last.row()][last.col()] = '0' ;
            }

        }


        boolean flag_exit_placed = false ;

        while (flag_exit_placed == false)
        {
            int temp = rand.nextInt(ROW) ;

            if (maze[temp][COL-2] == '0')
            {
                maze[temp][COL-1] = '3' ;
                flag_exit_placed = true ;
            }
        }

        while (no_of_barrier_need_to_removed  > 0)
        {
            int temp_row = rand.nextInt(ROW) ;
            int temp_col = rand.nextInt(COL) ;

            if (maze[temp_row][temp_col] == '1')
            {
                maze[temp_row][temp_col] = '0' ;
                --no_of_barrier_need_to_removed ;
            }
        }

        return maze ;

    }

    public void to_csv(char[][] maze_data) throws IOException{
        // Use "throws IOException" instead of try ... catch ...

        File file = new File(this.maze_csv_file);


        FileWriter outputfile = new FileWriter(file);

        CSVWriter writer = new CSVWriter(outputfile);

        String[] temporary_string_arr = new String[maze_data[0].length] ;

        List<String[]> data = new ArrayList<String[]>() ;


        for (int i = 0; i < maze_data.length; ++i)
        {
            for (int j = 0; j < maze_data[0].length; ++j)
            {
                temporary_string_arr[j] = Character.toString(maze_data[i][j]) ;
            }

            data.add(temporary_string_arr) ;

            writer.writeAll(data);
            data.clear() ;
        }

        writer.close() ;


    }
}


