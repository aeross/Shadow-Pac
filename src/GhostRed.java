import bagel.Image;
import bagel.util.Point;

import java.util.ArrayList;


/**
 * The red ghost.
 * This ghost has two possible different behaviours. One behaviour says that this ghost is
 * stationary, and the other one says this ghost moves in a horizontal direction
 * (starting by moving right, then switches 180 whenever it hits a wall) at a speed of 1.
 * This class handles those two behaviours.
 */
public class GhostRed extends Ghost {
    private final static int SPEED = 1;
    private final static Image IMAGE = new Image("res/ghostRed.png");
    private double direction = MovableEntity.FACING_RIGHT;

    /**
     * Constructor.
     * @param loc the initial location of the ghost
     * @param stationary specifies whether this ghost is stationary
     */
    public GhostRed(Point loc, boolean stationary) {
        super(IMAGE, loc, generateSpeed(stationary));
    }

    /**
     * Generates speed value based on input.
     * @param stationary specifies whether this ghost is stationary
     * @return 1 if not stationary, 0 if stationary
     */
    private static int generateSpeed(boolean stationary) {
        if (stationary) {
            return 0;
        }
        return SPEED;
    }

    /**
     * Moves ghost based on its specified behaviour.
     */
    protected void doMove(ArrayList<Wall> walls) {
        // adding by pi means reversing the direction, e.g. from right to left or down to up
        if (!moveToEmpty(direction, walls, false)) {
            direction = (direction + Math.PI) % (2 * Math.PI);
        }
    }
}
