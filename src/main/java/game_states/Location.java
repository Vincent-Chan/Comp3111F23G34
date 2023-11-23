package game_states;

import java.util.Objects;

/**
 * <p>The class Location involves 2 data members, which are row and col.</p>
 *
 * @param row Stores the row index of the location (i.e. node)
 * @param col Stores the column index of the location (i.e. node)
 *
 * <p>The function opposite_node() accepts Location instance (the parent node of the current node) as input parameter,
 * then return a node that is adjacent to the current node (i.e. the node that call this member function) and opposite to the parent node.</p>
 */

public record Location(int row, int col) {

    /**
     * <p>opposite_node() is a member function for Location class.</p>
     * <p>It returns the opposite node of the current node, and it should be exactly the adjacent node of the current node;
     * or null.</p>
     *
     * @param l The parent node of the current node that call this member function
     * @return The opposite node of the current node, and it should be exactly the adjacent node of the current node; or null.
     */
    public Location opposite_node(Location l) {
        /**
         * <p>Situation A: Both parent node and the current node are in the column</p>
         */

        /**
         * <p>Case A1: The current node is below the parent node</p>
         * <p>return the node that is directly below and adjacent to the current node</p>
         *
         * <p>Picture demonstration:</p>
         *
         * <p>[parent node]</p>
         * <p>[current node]</p>
         * <p>[return node]</p>
         */
        if (this.row > l.row)
        {
            return new Location(this.row + 1, this.col) ;
        }
        /**
         * <p>Case A2: The current node is above the parent node</p>
         * <p>return the node that is directly above and adjacent to the current node</p>
         *
         * <p>Picture demonstration:</p>
         *
         * <p>[return node]</p>
         * <p>[current node]</p>
         * <p>[parent node]</p>
         */
        if (this.row < l.row)
        {
            return new Location(this.row - 1, this.col) ;
        }

        /**
         * <p>Situation B: Both parent node and the current node are in the row</p>
         */

        /**
         * <p>Case B1: The current node is on the right side of the parent node</p>
         * <p>return the node that is directly on the right of and adjacent to the current node</p>
         *
         * <p>Picture demonstration:</p>
         *
         * <p>[parent node] [current node] [return node]</p>
         */
        if (this.col > l.col)
        {
            return new Location(this.row, this.col + 1) ;
        }
        /**
         * <p>Case B2: The current node is on the left side of the parent node</p>
         * <p>return the node that is directly on the left of and adjacent to the current node</p>
         *
         * <p>Picture demonstration:</p>
         *
         * <p>[left node] [current node] [parent node]</p>
         */
        if (this.col < l.col)
        {
            return new Location(this.row, this.col - 1) ;
        }

        /**
         * <p>If none of the above if statements are satisfied, it would return null.</p>
         */
        return null ;
    }
}