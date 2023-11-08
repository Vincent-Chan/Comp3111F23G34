import org.junit.Test;

import static org.junit.Assert.*;

public class VertexTest {

    @Test
    void VertexTest1()
    {
        Vertex v = new Vertex(0, 4, null) ;
    }

    //////////////////////////////

    @Test
    void opposite_node_Test1()
    {
        Vertex mother = new Vertex(15, 25, null) ;
        Vertex child = new Vertex(16, 25, mother) ;


        Vertex actual_vertex_1 = child.opposite_node() ;

        Vertex expected_vertex_1 = new Vertex(17, 25, child) ;

        assertEquals(expected_vertex_1, actual_vertex_1) ;

    }

    @Test
    void opposite_node_Test2()
    {
        Vertex mother = new Vertex(15, 25, null) ;
        Vertex child = new Vertex(14, 25, mother) ;


        Vertex actual_vertex_2 = child.opposite_node() ;

        Vertex expected_vertex_2 = new Vertex(13, 25, child) ;

        assertEquals(expected_vertex_2, actual_vertex_2) ;

    }

    @Test
    void opposite_node_Test3()
    {
        Vertex mother = new Vertex(15, 25, null) ;
        Vertex child = new Vertex(15, 26, mother) ;


        Vertex actual_vertex_3 = child.opposite_node() ;

        Vertex expected_vertex_3 = new Vertex(15, 27, child) ;

        assertEquals(expected_vertex_3, actual_vertex_3) ;

    }

    @Test
    void opposite_node_Test4()
    {
        Vertex mother = new Vertex(15, 25, null) ;
        Vertex child = new Vertex(15, 24, mother) ;


        Vertex actual_vertex_4 = child.opposite_node() ;

        Vertex expected_vertex_4 = new Vertex(15, 23, child) ;

        assertEquals(expected_vertex_4, actual_vertex_4) ;

    }

}