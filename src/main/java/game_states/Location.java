package game_states;

import java.util.Objects;

/**
 * The class Location involves 2 data members, which are row and col.
 *
 * @param row: Stores the row index of the location (i.e. node)
 * @param col: Stores the column index of the location (i.e. node)
 *
 * The function opposite_node() accepts Location instance (the parent node of the current node) as input parameter,
 * then return a node that is adjacent to the current node (i.e. the node that call this member function) and opposite to the parent node.
 */

public record Location(int row, int col) {

    /**
     * opposite_node() is a member function for Location class.
     * It returns the opposite node of the current node, and it should be exactly the adjacent node of the current node;
     * or null.
     *
     * @param l: The parent node of the current node that call this member function
     * @return The opposite node of the current node, and it should be exactly the adjacent node of the current node; or null.
     */
    public Location opposite_node(Location l) {
        /**
         * Situation A: Both parent node and the current node are in the column
         */

        /**
         * Case A1: The current node is below the parent node
         * return the node that is directly below and adjacent to the current node
         *
         * Picture demonstration:
         *
         * [parent node]
         * [current node]
         * [return node]
         */
        if (this.row > l.row)
        {
            return new Location(this.row + 1, this.col) ;
        }
        /**
         * Case A2: The current node is above the parent node
         * return the node that is directly above and adjacent to the current node
         *
         * Picture demonstration:
         *
         * [return node]
         * [current node]
         * [parent node]
         */
        if (this.row < l.row)
        {
            return new Location(this.row - 1, this.col) ;
        }

        /**
         * Situation B: Both parent node and the current node are in the row
         */

        /**
         * Case B1: The current node is on the right side of the parent node
         * return the node that is directly on the right of and adjacent to the current node
         *
         * Picture demonstration:
         *
         * [parent node] [current node] [return node]
         */
        if (this.col > l.col)
        {
            return new Location(this.row, this.col + 1) ;
        }
        /**
         * Case B2: The current node is on the left side of the parent node
         * return the node that is directly on the left of and adjacent to the current node
         *
         * Picture demonstration:
         *
         * [left node] [current node] [parent node]
         */
        if (this.col < l.col)
        {
            return new Location(this.row, this.col - 1) ;
        }

        /**
         * If none of the above if statements are satisfied, it would return null.
         */
        return null ;
    }
}