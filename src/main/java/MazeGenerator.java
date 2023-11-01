import java.util.ArrayList;
import java.util.Random;

public class MazeGenerator {
    public static char[][] PrimMazeGenerator()
    {
        int no_of_barrier_need_to_removed = 18 ;

        Random rand = new Random() ;

        // set the seed number as 3111 seems to generate a very good maze
        // long seed_number = 3111 ;
        // rand.setSeed(seed_number) ;

        // if maze[i][j] = '0', it is a clear path in white color
        // if maze[i][j] = '1', it is a barrier in grey color
        // if maze[i][j] = '2', it is the starting point for the mouse Jerry
        // if maze[i][j] = '3', it is the exit point where the mouse Jerry should go to

        final int r = 30 ;
        final int c = 30 ;

        // corresponds to North, East, South, West
        final int[] row_step = {-1, 0, 1, 0} ;
        final int[] col_step = {0, 1, 0, -1} ;

        char[][] maze = new char [r][c] ;

        for (int i = 0; i < r; ++i)
        {
            for (int j = 0; j < c; ++j)
            {
                maze[i][j] = '1' ;
            }
        }

        Vertex start = new Vertex(rand.nextInt(r), 0, null) ;
        maze[start.row][start.col] = '2' ;

        ArrayList<Vertex> frontier = new ArrayList<Vertex> () ;

        for (int i = 0; i < row_step.length; ++i)
        {
            if ( (start.row + row_step[i]) >= 0 &&
                    (start.row + row_step[i]) < r &&
                    (start.col + col_step[i]) >= 0 &&
                    (start.col + col_step[i]) < c )
            {
                if (maze[start.row + row_step[i]][start.col + col_step[i]] == '0')
                {
                    continue ;
                }

                frontier.add(new Vertex(start.row + row_step[i], start.col + col_step[i], start)) ;

            }
        }

        Vertex last = null;
        Vertex current = null ;
        Vertex opposite = null ;

        while (frontier.isEmpty() == false)
        {
            current = frontier.remove( rand.nextInt( frontier.size() ) ) ;
            opposite = current.opposite_node() ;

            if (opposite.row >= 0 && opposite.row < r && opposite.col >= 0 && opposite.col < c)
            {
                if (maze[current.row][current.col] == '1' && maze[opposite.row][opposite.col] == '1') {

                    maze[current.row][current.col] = '0';
                    maze[opposite.row][opposite.col] = '0';

                    last = opposite;

                    for (int i = 0; i < row_step.length; ++i) {
                        if ((opposite.row + row_step[i]) >= 0 &&
                                (opposite.row + row_step[i]) < r &&
                                (opposite.col + col_step[i]) >= 0 &&
                                (opposite.col + col_step[i]) < c) {
                            if (maze[opposite.row + row_step[i]][opposite.col + col_step[i]] == '0') {
                                continue;
                            }

                            frontier.add(new Vertex(opposite.row + row_step[i], opposite.col + col_step[i], opposite));
                        }
                    }
                }
            }


            if (frontier.isEmpty() == true)
            {
                maze[last.row][last.col] = '0' ;
            }

        }


        boolean flag_exit_placed = false ;

        while (flag_exit_placed == false)
        {
            int temp = rand.nextInt(r) ;

            if (maze[temp][c-2] == '0')
            {
                maze[temp][c-1] = '3' ;
                flag_exit_placed = true ;
            }
        }

        while (no_of_barrier_need_to_removed  > 0)
        {
            int temp_row = rand.nextInt(r) ;
            int temp_col = rand.nextInt(c) ;

            if (maze[temp_row][temp_col] == '1')
            {
                maze[temp_row][temp_col] = '0' ;
                --no_of_barrier_need_to_removed ;
            }
        }

        return maze ;

    }

}
