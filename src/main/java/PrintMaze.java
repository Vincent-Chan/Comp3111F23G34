public class PrintMaze {

    public static void print_maze(char[][] m)
    {
        for (int i = 0; i < m.length; i++)
        {
//            System.out.print("{");
            for (int j = 0; j < m[0].length; j++)
            {
                System.out.print(m[i][j]);
//                System.out.print("\'");
//                System.out.print(m[i][j]);
//                System.out.print("\', ");
            }
//            System.out.print("},");
            System.out.println();
        }
    }

}
