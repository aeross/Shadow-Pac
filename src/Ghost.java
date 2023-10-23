//import bagel.*;
import bagel.Image;
import bagel.util.Point;

import java.util.ArrayList;

/** The Ghost class. A subclass to MovableEntity.
 * This class is abstract, since there are no "generic" ghosts -- a ghost must be either
 * red, blue, pink, or green.
 * However, all ghosts have one thing in common: the frenzy mode,
 * whose implementation can be seen in activateFrenzy() and deactivateFrenzy() methods below.
 */
public abstract class Ghost extends MovableEntity {
    // the non-frenzy speed, which is required because subtracting -0.5 for frenzy
    // then adding 0.5 after frenzy ends won't work if the non-frenzy speed is less than 0.5
    private final double normalSpeed;
    private final Image ghostImg;
    private final static double FRENZY_SPEED = 0.5;
    private final Image GHOST_FRENZY_IMG = new Image("res/ghostFrenzy.png");

    /** Score for player if the ghost is eaten during frenzy mode. */
    public final static int SCORE = 30;


    /**
     * The constructor for Ghost.
     * @param image the image of this ghost
     * @param loc the starting location of this ghost
     * @param speed the moving speed of this ghost
     */
    public Ghost(Image image, Point loc, double speed) {
        super(image, loc, speed);
        ghostImg = image;
        normalSpeed = speed;
    }


    /** Changes ghost behaviour for frenzy mode. */
    public void activateFrenzy() {
        super.setImage(GHOST_FRENZY_IMG);
        double newSpeed = normalSpeed - FRENZY_SPEED;
        if (newSpeed < 0) {
            super.setSpeed(0);
        } else {
            super.setSpeed(newSpeed);
        }
    }

    /** Reverts ghost behaviour back to normal after frenzy mode ends. */
    public void deactivateFrenzy() {
        // if the ghost has been eaten during frenzy, reset to starting position
        if (super.getLocation().equals(Entity.OFF_SCREEN)) {
            super.moveToStart();
        }

        // revert everything to normal
        super.setImage(ghostImg);
        super.setSpeed(normalSpeed);
    }

    /**
     * Moves ghost based on its specified behaviour.
     * This is a template method, with doMove() as the hook method below.
     * @param walls the walls preventing this ghost from moving through
     */
    public void move(ArrayList<Wall> walls) {
        doMove(walls);
    }

    protected abstract void doMove(ArrayList<Wall> walls);
}
