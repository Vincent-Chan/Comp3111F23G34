package game_algorithm;

import game_states.Location;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;



public class ShortestPathGenerator
{
    private final int ROW = 30;
    private final int COL = 30;
    private String map_file_path;
    private String output_file_path;

    private int[][] map =  new int[ROW][COL];

    public ShortestPathGenerator(String map_file_path, String output_file_path){
        this.map_file_path = map_file_path;
        this.output_file_path = output_file_path;
        load_csv(this.map_file_path);
    }
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