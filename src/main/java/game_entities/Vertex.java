package game_entities;

/**
 * The data structure for a vertex entity on the game map
 */
public class Vertex{
    /** Indicates whether a vertex is clear */
    private boolean clear;

    /**
     * @param isClear Whether to set this vertex as a clear one or a barrier
     */
    public Vertex(boolean isClear){
        clear = isClear;
    }


    /**
     * @return Whether the vertex is clear or not
     */
    public boolean isClear(){return clear;}
}
