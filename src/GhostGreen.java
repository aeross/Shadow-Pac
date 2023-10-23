import bagel.Image;
import bagel.util.Point;

import java.util.ArrayList;
import java.util.Random;


/**
 * The green ghost.
 * Unlike red ghosts, the green ghost only has one behaviour:
 * It randomly chooses whether to move vertically or horizontally during instantiation
 * at a speed of 4, then switches 180 whenever it hits a wall.
 * The "randomness" is only called *once* during instantiation, and never called again afterwards.
 * If the vertical direction is chosen, the ghost will start by initially moving down,
 * and if the horizontal direction is chosen, it will start by initially moving right.
 */
public class GhostGreen extends Ghost {
    private final static int SPEED = 4;
    private final static Image IMAGE = new Image("res/ghostGreen.png");
    private double direction;
    private Random r = new Random();

    /**
     Constructor.
     * @param loc the initial location of the ghost
     */
    public GhostGreen(Point loc) {
        super(IMAGE, loc, SPEED);
        generateDir();
    }

    /**
     * Generates initial ghost direction.
     */
    private void generateDir() {
        double[] MOVES = {MovableEntity.FACING_RIGHT, MovableEntity.FACING_DOWN};
        direction = MOVES[r.nextInt(MOVES.length)];
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
