package game_states;

import org.jetbrains.annotations.NotNull;

/**
 * An actions of moving a player.
 */
public abstract sealed class Move permits Move.Down, Move.Left, Move.Right, Move.Up {

    protected final int initiator;

    /**
     * @return The id of the player who performed the action.
     */
    public int getInitiator() {
        return initiator;
    }

    protected Move(int initiator) {
        this.initiator = initiator;
    }

    /**
     * Generates the next position after the move based on the current position.
     *
     * @param currentLocation The current position.
     * @return The next position.
     */
    public abstract @NotNull Location nextPosition(@NotNull Location currentLocation);

    /**
     * The action of moving down.
     */
    public static final class Down extends Move {

        /**
         * @param initiator The id of the player who give the invalid input.
         */
        public Down(int initiator) {
            super(initiator);
        }

        @Override
        public @NotNull Location nextPosition(@NotNull Location currentLocation) {
            return new Location(currentLocation.row()+1, currentLocation.col());
        }

        @Override
        public String toString(){return "DOWN";}
    }

    /**
     * The action of moving left.
     */
    public static final class Left extends Move {
        /**
         * @param initiator The id of the player who give the invalid input.
         */
        public Left(int initiator) {
            super(initiator);
        }

        @Override
        public @NotNull Location nextPosition(@NotNull Location currentLocation) {
            return new Location(currentLocation.row(), currentLocation.col()-1);
        }

        @Override
        public String toString(){return "LEFT";}
    }

    /**
     * The action of moving right.
     */
    public static final class Right extends Move {
        /**
         * @param initiator The id of the player who give the invalid input.
         */
        public Right(int initiator) {
            super(initiator);
        }

        @Override
        public @NotNull Location nextPosition(@NotNull Location currentLocation) {
            return new Location(currentLocation.row(), currentLocation.col()+1);
        }

        @Override
        public String toString(){return "RIGHT";}

    }

    /**
     * The action of moving up.
     */
    public static final class Up extends Move {
        /**
         * @param initiator The id of the player who give the invalid input.
         */
        public Up(int initiator) {
            super(initiator);
        }

        @Override
        public @NotNull Location nextPosition(@NotNull Location currentLocation) {
            return new Location(currentLocation.row()-1, currentLocation.col());
        }

        @Override
        public String toString(){return "UP";}
    }
}