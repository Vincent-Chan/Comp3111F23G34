package game_states;

import java.util.Objects;

/**
 * Data structure to represent a location on the map
 * @param row the row of the location (0-indexed, increases from up to down)
 * @param col the column of the location (0-indexed, increases from left to right)
 * */
public record Location(int row, int col) {

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
