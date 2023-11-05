import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class DataOfSquareTest {

    @Test
    void SquareDataTest1()
    {
        DataOfSquare squareData1 = new DataOfSquare(0) ;


        ArrayList<Color> C1 = new ArrayList<Color>() ;
        C1.add(Color.white) ;
        C1.add(Color.gray) ;
        C1.add(Color.red) ;
        C1.add(Color.green) ;

        int color1 = 0 ;

        SquarePanel square1 = new SquarePanel(C1.get(color1)) ;


        assertEquals(squareData1.C, C1) ;
        assertEquals(squareData1.color, color1) ;
        assertEquals(squareData1.square, square1);
    }

    @Test
    void SquareDataTest2()
    {
        DataOfSquare squareData2 = new DataOfSquare(1) ;


        ArrayList<Color> C2 = new ArrayList<Color>() ;
        C2.add(Color.white) ;
        C2.add(Color.gray) ;
        C2.add(Color.red) ;
        C2.add(Color.green) ;

        int color2 = 0 ;

        SquarePanel square2 = new SquarePanel(C2.get(color2)) ;


        assertEquals(squareData2.C, C2) ;
        assertEquals(squareData2.color, color2) ;
        assertEquals(squareData2.square, square2);
    }

    @Test
    void SquareDataTest3()
    {
        DataOfSquare squareData3 = new DataOfSquare(2) ;


        ArrayList<Color> C3 = new ArrayList<Color>() ;
        C3.add(Color.white) ;
        C3.add(Color.gray) ;
        C3.add(Color.red) ;
        C3.add(Color.green) ;

        int color3 = 0 ;

        SquarePanel square3 = new SquarePanel(C3.get(color3)) ;


        assertEquals(squareData3.C, C3) ;
        assertEquals(squareData3.color, color3) ;
        assertEquals(squareData3.square, square3);
    }

    @Test
    void SquareDataTest4()
    {
        DataOfSquare squareData4 = new DataOfSquare(3) ;


        ArrayList<Color> C4 = new ArrayList<Color>() ;
        C4.add(Color.white) ;
        C4.add(Color.gray) ;
        C4.add(Color.red) ;
        C4.add(Color.green) ;

        int color4 = 0 ;

        SquarePanel square4 = new SquarePanel(C4.get(color4)) ;


        assertEquals(squareData4.C, C4) ;
        assertEquals(squareData4.color, color4) ;
        assertEquals(squareData4.square, square4);
    }

}