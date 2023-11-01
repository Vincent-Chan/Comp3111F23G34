import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;



public class ShortestPath
{
    static int ROW = 30;
    static int COL = 30;

    private static int[][] map =  new int[ROW][COL];
    private static boolean[][] visited = new boolean[ROW][COL]; // default value is false
    private static int[][][] previous = new int[ROW][COL][2];
    private static List<int[]> Shortest_path_vertices = new ArrayList<>();

	public static int[][] calculate_shortest_path(Vertex entry, Vertex exit)
	{
        clear_data();

        // error handling: same entry and exit
        if ((entry.extract_row() == exit.extract_row()) && (entry.extract_col() == exit.extract_col()))
        {
            System.err.println("Same entry and exit point");
            int[][] temp = new int[1][2];
            temp[0][0] = entry.extract_row();
            temp[0][1] = entry.extract_col();
            Shortest_path_vertices.add(temp[0]);
            return temp; 
        }

		Queue<Vertex> queue = new LinkedList<>();
        
        // marking the entry as visited
		visited[entry.extract_row()][entry.extract_col()] = true; 

        // entry doesn't have a previous step
		previous[entry.extract_row()][entry.extract_col()][0] = -1;
		previous[entry.extract_row()][entry.extract_col()][1] = -1;

		queue.add(entry);
		while (!queue.isEmpty())
		{
            // get the head of the queue
			Vertex current_vertex = queue.poll();

			// up, down, left, right
			int[] select_row = {-1,1,0,0};
			int[] select_col = {0,0,-1,1};

			for (int i = 0; i < 4; i++)
			{
				// boundary check (out of range, barrier, visited)
				if ((current_vertex.extract_row()+select_row[i]) >= ROW || 
                    (current_vertex.extract_row()+select_row[i]) < 0 || 
                    (current_vertex.extract_col()+select_col[i]) >= COL || 
                    (current_vertex.extract_col()+select_col[i]) < 0 || 
                    (map[current_vertex.extract_row()+select_row[i]][current_vertex.extract_col()+select_col[i]] == 1) ||
                    (visited[current_vertex.extract_row()+select_row[i]][current_vertex.extract_col()+select_col[i]] == true))
				{
					continue;
				}

				else // (visited[current_vertex.extract_row()+select_row[i]][current_vertex.extract_col()+select_col[i]] == false)
				{
					visited[current_vertex.extract_row()+select_row[i]][current_vertex.extract_col()+select_col[i]] = true;
					previous[current_vertex.extract_row()+select_row[i]][current_vertex.extract_col()+select_col[i]][0] = current_vertex.extract_row();
					previous[current_vertex.extract_row()+select_row[i]][current_vertex.extract_col()+select_col[i]][1] = current_vertex.extract_col();
                    queue.add(new Vertex(current_vertex.extract_row()+select_row[i], current_vertex.extract_col()+select_col[i]));
				}

				// exit point check
				if ((current_vertex.extract_row()+select_row[i]) == exit.extract_row() && (current_vertex.extract_col()+select_col[i]) == exit.extract_col())
				{
					return output_array(entry, exit);
				}	
			}
		}

        // error handling: invalid points
        System.err.println("No path");
		return new int[0][2];
	}

    public static void output_file()
    {
    	try
    	{
    		FileWriter writer = new FileWriter("shortest_path_vertices.csv");
    		writer.write("s1" + System.lineSeparator());
    		for (int i = 0; i < Shortest_path_vertices.size(); i++)
    		{
    			writer.write(Shortest_path_vertices.get(i)[0] + "," + Shortest_path_vertices.get(i)[1] + System.lineSeparator());
    		}
            writer.close();
    	}
    	catch (IOException e)
    	{
    		e.printStackTrace();
    	}
    }

    public static int[][] output_array(Vertex entry, Vertex exit)
    {
    	// add exit point vertex first
    	int row = exit.extract_row(), col = exit.extract_col();

    	while ((row != -1) && (col != -1)) // starting point in previous is {-1,-1}
    	{
            Shortest_path_vertices.add(0, new int[]{row,col});
    		int temp = row;
            row = previous[row][col][0];
    		col = previous[temp][col][1];
    	}

        // Changing List<int[]> into int[][]
        int num_of_vertices = Shortest_path_vertices.size();
        int[][] array = new int[num_of_vertices][2];
        for (int i = 0; i < num_of_vertices; i++)
        {
            array[i] = Shortest_path_vertices.get(i);
        }
    	return array;
    }

    public static void load_csv() 
    {
    	// transform the .csv into map
        String row = "";
        try (BufferedReader br = new BufferedReader(new FileReader("MazeData.csv")))
        {
            int i = 0;
            while ((row = br.readLine()) != null) {
                String[] values = row.split(",");
//                for (i = 0; i < COL; i++)
//                {
//                    System.out.print(values[i].substring(1,2));
//                }
//                System.out.println();


//                 Process the values
                int j = 0;
                for (String value : values) {
                    map[i][j] = (Integer.parseInt(values[j].substring(1,2)));
                    j++;
                }
                // System.out.println();
                i++;
            }
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // public static int[][] run(Vertex entry, Vertex exit)
    // {
    //     clear_data();
    //     calculate_shortest_path(entry, exit);
    //     return output_array(entry, exit);
    // }

    public static void clear_data() // clear the data before calculating shortest path again
    {
        visited = new boolean[ROW][COL];
        previous = new int[ROW][COL][2]; // is this needed?
        Shortest_path_vertices = new ArrayList<>();
    }

    public static void print_map()
    {
        for (int i = 0; i < ROW; i++)
        {
            for (int j = 0; j < COL; j++)
            {
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
    }

    public static void print_previous()
    {
        for (int i = 0; i < ROW; i++)
        {
            for (int j = 0; j < COL; j++)
            {
                System.out.print(previous[i][j][0] + "," + previous[i][j][1] + "\t");
            }
            System.out.println();
        }
    }
}
