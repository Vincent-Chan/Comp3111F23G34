package game_states;

import org.junit.Test;
import static junit.framework.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

public class LocationTest {

    // Function A Unit Testing
    @Test
    public void testEqual()
    {
        Location a = new Location(1,2); // target function
        Location b = new Location(1,2); // target function
        Location c = new Location(2,3); // target function

        assertEquals(true,a.equals(b));
        assertEquals(false,a.equals(c));
    }

    // Function A Unit Testing
    @Test
    public void opposite_nodeTest1()
    {
        Location loc_a1 = new Location(15, 25) ;
        Location loc_b1 = new Location(14, 25) ;

        Location actual1 = loc_a1.opposite_node(loc_b1) ; //target function

        Location expected1 = new Location(16, 25) ;

        assertEquals(expected1, actual1) ;

        //////////

        Location loc_a2 = new Location(15, 25) ;
        Location loc_b2 = new Location(16, 25) ;

        Location actual2 = loc_a2.opposite_node(loc_b2) ; //target function

        Location expected2 = new Location(14, 25) ;

        assertEquals(expected2, actual2) ;

        //////////

        Location loc_a3 = new Location(15, 25) ;
        Location loc_b3 = new Location(15, 24) ;

        Location actual3 = loc_a3.opposite_node(loc_b3) ; //target function

        Location expected3 = new Location(15, 26) ;

        assertEquals(expected3, actual3) ;

        //////////

        Location loc_a4 = new Location(15, 25) ;
        Location loc_b4 = new Location(15, 26) ;

        Location actual4 = loc_a4.opposite_node(loc_b4) ; //target function

        Location expected4 = new Location(15, 24) ;

        assertEquals(expected4, actual4) ;
    }

    // Function A Unit Testing
    @Test
    public void opposite_nodeTest2()
    {
        Location loc_a5 = new Location(15, 25) ;
        Location loc_b5 = new Location(15, 25) ;

        Location actual5 = loc_a5.opposite_node(loc_b5) ; //target function

        Location expected5 = null ;

        assertEquals(expected5, actual5) ;
    }

}