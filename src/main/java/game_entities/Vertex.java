package game_entities;

public class Vertex extends Entity{
    private boolean clear;

    public Vertex(boolean isClear){
        clear = isClear;
    }
    public boolean isClear(){return clear;}
}
