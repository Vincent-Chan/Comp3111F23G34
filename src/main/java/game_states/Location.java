package game_states;

import java.util.Objects;

/**
 * The class Location involves 2 data members, which are row and col.
 *
 * @param row: Stores the row index of the location (i.e. node)
 * @param col: Stores the column index of the location (i.e. node)
 *
 *
 *
 * This class also involves 2 member functions.
 *
 * The function toString() will convert the location into a String expression of the location.
 *
 * The function opposite_node() accepts Location instance (the parent node of the current node) as input parameter,
 * then return a node that is adjacent to the current node (i.e. the node that call this member function) and opposite to the parent node.
 */

public record Location(int row, int col) {

    /**
     * toString() is a member function for Location class.
     * It returns the String expression of the location.
     *
     * @return: A string that store the string expression of the current location in the format [ row , col ]
     */
    @Override
    public String toString()
    {
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
