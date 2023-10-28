import java.util.ArrayList;
import java.awt.Color;

public class DataOfSquare {

    // ArrayList that'll contain the colors
    ArrayList<Color> C = new ArrayList<Color>();
    int color; // 0: Clear Path, 1: Barrier, 2: Starting point, 3: Exit point
    SquarePanel square;

    public DataOfSquare(int col){

        // Let's add the color to the arrayList
        C.add(Color.white);     // 0
        C.add(Color.gray);      // 1
        C.add(Color.red);       // 2
        C.add(Color.green);     // 3

        color = col;
        square = new SquarePanel(C.get(color));
    }

    /*

    public void lightMeUp(int c){
        square.ChangeColor(C.get(c));
    }

    */

}