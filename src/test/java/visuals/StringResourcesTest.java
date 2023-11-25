package visuals;

import org.junit.Test;

import static org.junit.Assert.*;

public class StringResourcesTest {

    @Test
    public void testShowRemainingMoves(){
        StringResources.showRemainingMoves(0,2); //target function
        StringResources.showRemainingMoves(1,3); //target function
    }
}