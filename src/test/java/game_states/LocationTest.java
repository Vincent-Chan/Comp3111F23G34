package game_states;

import org.junit.Test;
import static junit.framework.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

public class LocationTest {
    @Test
    public void testEqual(){
        Location a = new Location(1,2);
        Location b = new Location(1,2);
        Location c = new Location(2,3);

        assertEquals(true,a.equals(b));
        assertEquals(false,a.equals(c));
    }

}