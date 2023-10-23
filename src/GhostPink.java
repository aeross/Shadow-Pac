import bagel.Image;
import bagel.util.Point;

import java.util.ArrayList;
import java.util.Random;


/**
 * The pink ghost.
 * Unlike red ghosts, the pink ghost only has one behaviour:
 * It randomly chooses one move direction in any of the four directions during instantiation,
 * at a speed of 4, then randomly chooses one direction in any of the four directions
 * again whenever it hits a wall. The "randomness" is called every time this ghost hits a wall.
 */
public class GhostPink extends Ghost {
    private final static int SPEED = 3;
    private final static Image IMAGE = new Image("res/ghostPink.png");
    private double direction;
    private Random r = new Random();

    /**
     Constructor.
     * @param loc the initial location of the ghost
     */
    public GhostPink(Point loc) {
        super(IMAGE, loc, SPEED);
        generateDir();
    }

    /**
     * Generates initial ghost movement.
     */
    private void generateDir() {
        double[] MOVES = {MovableEntity.FACING_RIGHT, MovableEntity.FACING_LEFT,
                MovableEntity.FACING_UP, MovableEntity.FACING_DOWN};
        direction = MOVES[r.nextInt(MOVES.length)];
    }

    /**
     * Moves ghost based on its specified behaviour.
     */
    protected void doMove(ArrayList<Wall> walls) {
        if (!moveToEmpty(direction, walls, false)) {
            generateDir();
        }
    }
}
