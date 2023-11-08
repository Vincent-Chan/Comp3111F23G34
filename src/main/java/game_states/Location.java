package game_states;

import java.util.Objects;

public record Location(int row, int col) {
    @Override
    public String toString(){
        return "[ "+row+" , "+col+" ]";
    }

    public Location opposite_node(Location l) {
        if (this.row > l.row)
        {
            return new Location(this.row + 1, this.col) ;
        }
        if (this.row < l.row)
        {
            return new Location(this.row - 1, this.col) ;
        }

        if (this.col > l.col)
        {
            return new Location(this.row, this.col + 1) ;
        }
        if (this.col < l.col)
        {
            return new Location(this.row, this.col - 1) ;
        }

        return null ;
    }
}
