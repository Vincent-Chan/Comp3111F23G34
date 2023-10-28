import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MazeDataToCSV {

    public static void toCSV(char[][] maze_data)
    {

        File file = new File("MazeData.csv");

        try
        {
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

                writer.writeAll(data); ;
                data.clear() ;
            }

            writer.close() ;
        }
        catch (IOException e)
        {
            e.printStackTrace() ;
        }
    }

}
