public class Main
{
    public static void main(String[] args)
    {
        ShortestPath.load_csv();
//        ShortestPath.print_map();
        int[][] result = ShortestPath.calculate_shortest_path(new Vertex(17, 0), new Vertex(19, 29)); // .calculate_shortest_path() returns int[][]
        
        // printing the vertices and number of vertices in the shortest path
        // for (int i = 0; i < result.length; i++) {
        //    for (int j = 0; j < result[i].length; j++) {
        //        System.out.print(result[i][j] + " ");
        //    }
        //    System.out.println();
        // }
        // System.out.println(result.length);
        
        ShortestPath.output_file();
    }

}