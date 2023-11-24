package game_entities;

import org.junit.Test;

import static org.junit.Assert.*;

public class VertexEntityTest {

    @Test
    public void testConstructor(){
        Vertex v = new Vertex(true); //target function
    }
    @Test
    public void isClear() {
        Vertex v = new Vertex(true);
        boolean real = v.isClear(); //target function
        assertEquals(true, real);
    }
}