package game_algorithm;

import game_states.Location;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * This class allows programmers to calculate the shortest path in a maze.
 *
 * Programmers should pass the maze data file directory and output file directory in the constructor.
 *
 * To calculate the shortest path in a maze, programmers need to call calculate_shortest_path(). This function will
 * return an ArrayList object, containing a series of Location objects for each vertex along the shortest path.
 *
 * To generate a .csv file containing the coordinates along the shortest path vertices, programmers need to call output_file()
 * after calling calculate_shortest_path(). The file will be created in the directory specified in the constructor.
 */
public class ShortestPathGenerator
{
    /**
     * The number of rows in the map.
     */
    private final int ROW = 30;

    /**
     * The number of columns in the map.
     */
    private final int COL = 30;

    /**
     * Directory of the map data file in .csv format.
     */
    private String map_file_path;

    /**
     * Directory of the file in .csv format for storing the coordinates along the shortest path vertices.
     */
    private String output_file_path;

    /**
     * Map data in int[][].
     */
    private int[][] map =  new int[ROW][COL];

    /**
     * This function is a constructor for ShortestPathGenerator class.
     * @param map_file_path Directory of the map data file in .csv format.
     * @param output_file_path Directory of the file in .csv format for storing the coordinates along the shortest path vertices.
     */
    public ShortestPathGenerator(String map_file_path, String output_file_path){
        this.map_file_path = map_file_path;
        this.output_file_path = output_file_path;
        load_csv(this.map_file_path);
    }

    /**
     * This function calculates the shortest path according the given maze data.
     * @param entry Location object that indicates the coordinates of the entry point.
     * @param exit Location object that indicates the coordinates of the exit point.
     * @return ArrayList consisting the Location objects about the shortest path vertices.
     */
    public ArrayList<Location> calculate_shortest_path(Location entry, Location exit)
    {
        boolean[][] visited = new boolean[ROW][COL];
        int[][][] previous = new int[ROW][COL][2];
        ArrayList<Location> Shortest_path_vertices = new ArrayList<>();
        // error handling: same entry and exit
        if ((entry.row() == exit.row()) && (entry.col() == exit.col()))
        {
            System.err.println("Same entry and exit point");
            return null;
        }

        Queue<Location> queue = new LinkedList<>();

        // marking the entry as visited
        visited[entry.row()][entry.col()] = true;

        // entry doesn't have a previous step
        previous[entry.row()][entry.col()][0] = -1;
        previous[entry.row()][entry.col()][1] = -1;

        queue.add(entry);
        while (!queue.isEmpty())
        {
            // get the head of the queue
            Location current_vertex = queue.poll();

            // up, down, left, right
            int[] select_row = {-1,1,0,0};
            int[] select_col = {0,0,-1,1};

            for (int i = 0; i < 4; i++)
            {
                // boundary check (out of range, barrier, visited)
                if ((current_vertex.row()+select_row[i]) >= ROW ||
                        (current_vertex.row()+select_row[i]) < 0 ||
                        (current_vertex.col()+select_col[i]) >= COL ||
                        (current_vertex.col()+select_col[i]) < 0 ||
                        (map[current_vertex.row()+select_row[i]][current_vertex.col()+select_col[i]] == 1) ||
                        (visited[current_vertex.row()+select_row[i]][current_vertex.col()+select_col[i]] == true))
                {
                    continue;
                }

                else // (visited[current_vertex.extract_row()+select_row[i]][current_vertex.extract_col()+select_col[i]] == false)
                {
                    visited[current_vertex.row()+select_row[i]][current_vertex.col()+select_col[i]] = true;
                    previous[current_vertex.row()+select_row[i]][current_vertex.col()+select_col[i]][0] = current_vertex.row();
                    previous[current_vertex.row()+select_row[i]][current_vertex.col()+select_col[i]][1] = current_vertex.col();
                    queue.add(new Location(current_vertex.row()+select_row[i], current_vertex.col()+select_col[i]));
                }

                // exit point check
                if ((current_vertex.row()+select_row[i]) == exit.row() && (current_vertex.col()+select_col[i]) == exit.col())
                {
                    return output_array(entry, exit, previous,Shortest_path_vertices);
                }
            }
        }

        // error handling: invalid points
        System.err.println("No path");
        return null;
    }

    /**
     * This function converts the coordinates in the Location objects from ArrayList object in the parameter, to a .csv file..
     * The first line in the file will be "s1".
     * Lines afterwards will be in the format of "row, col", where row and col are the coordinates of a vertex.
     *
     * @param Shortest_path_vertices An ArrayList object that stores Location objects about the vertex along the shortest path.
     */
    public void output_file(ArrayList<Location> Shortest_path_vertices)
    {
        try
        {
            FileWriter writer = new FileWriter(output_file_path);
            writer.write("s1" + System.lineSeparator());
            for (int i = 0; i < Shortest_path_vertices.size(); i++)
            {
                writer.write(Shortest_path_vertices.get(i).row() + "," + Shortest_path_vertices.get(i).col() + System.lineSeparator());
            }
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * @param entry Location object that indicates the coordinates of the entry point.
     * @param exit Location object that indicates the coordinates of the exit point.
     * @param previous An int[][][] for recording some maze data after running calculate_shortest_path().
     * @param Shortest_path_vertices An ArrayList empty object for storing Location objects about the vertex along the shortest path.
     * @return ArrayList consisting the Location objects about the shortest path vertices.
     */
    public ArrayList<Location> output_array(Location entry, Location exit, int[][][] previous, ArrayList<Location> Shortest_path_vertices)
    {
        // add exit point vertex first
        int row = exit.row(), col = exit.col();

        while ((row != -1) && (col != -1)) // starting point in previous is {-1,-1}
        {
            Shortest_path_vertices.add(0, new Location(row, col));
            int temp = row;
            row = previous[row][col][0];
            col = previous[temp][col][1];
        }
        return Shortest_path_vertices;
    }

    /**
     * This function is called by the constructor. It is to import the maze data into the object for further calculation.
     *
     * @param map_file_path Directory of the map data file in .csv format.
     */
    public void load_csv(String map_file_path)
    {
        // transform the .csv into map
        String row = "";
        try (BufferedReader br = new BufferedReader(new FileReader(map_file_path)))
        {
            int i = 0;
            while ((row = br.readLine()) != null) {
                String[] values = row.split(",");
                int j = 0;
                for (String value : values) {
                    map[i][j] = (Integer.parseInt(values[j].substring(1,2)));
                    j++;
                }
                i++;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


}