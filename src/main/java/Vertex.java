public class Vertex {

    public int row ;
    public int col ;
    public Vertex parent ;

    public Vertex(int x, int y, Vertex v)
    {
        row = x ;
        col = y ;
        this.parent = v ;
    }

    public Vertex opposite_node()
    {
        if (row > parent.row)
        {
            return new Vertex(this.row + 1, this.col, this) ;
        }
        if (this.row < this.parent.row)
        {
            return new Vertex(this.row - 1, this.col, this) ;
        }

        if (this.col > this.parent.col)
        {
            return new Vertex(this.row, this.col + 1, this) ;
        }
        if (this.col < this.parent.col)
        {
            return new Vertex(this.row, this.col - 1, this) ;
        }

        return null ;
    }

}
