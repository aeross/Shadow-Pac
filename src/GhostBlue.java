import bagel.Image;
import bagel.util.Point;

import java.util.ArrayList;


/**
 * The blue ghost.
 * Unlike red ghosts, the blue ghost only has one behaviour:
 * It moves in a vertical direction at a speed of 2, starting by moving down,
 * then switches 180 whenever it hits a wall.
 */
public class GhostBlue extends Ghost {
    private final static int SPEED = 2;
    private final static Image IMAGE = new Image("res/ghostBlue.png");
    private double direction = MovableEntity.FACING_DOWN;

    /**
     Constructor.
     * @param loc the initial location of the ghost
     */
    public GhostBlue(Point loc) {
        super(IMAGE, loc, SPEED);
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
