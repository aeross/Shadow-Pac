import bagel.*;
import bagel.util.*;

import java.util.ArrayList;

/**
 * The Pac class. A subclass to MovableEntity.
 * This is the only entity where it is controlled by the player.
 */
public class Pac extends MovableEntity {
    private final static int SPEED = 3;
    private final static int FRENZY_SPEED = 1;
    private final static Image PAC_IMG = new Image("res/pac.png");
    private final Image PAC_OPEN_IMG = new Image("res/pacOpen.png");
    private boolean isOpen = false;

    /**
     * The constructor for Pac.
     * @param loc the initial location of pac
     */
    public Pac(Point loc) {
        super(PAC_IMG, loc, SPEED);
    }

    /** Switches image from pac.png to pacOpen.png, or vice versa. */
    public void switchOpen() {
        if (!isOpen) {
            super.setImage(PAC_OPEN_IMG);
            isOpen = true;
        } else {
            super.setImage(PAC_IMG);
            isOpen = false;
        }
    }

    /** Moves pac. Automatically rotates pac's image based on move direction.
     * @param moveDirection the move direction.
     * @param walls the walls preventing pac from moving through.
     */
    public void move(double moveDirection, ArrayList<Wall> walls) {
        moveToEmpty(moveDirection, walls, true);
    }

    /** Changes pac behaviour for frenzy mode. */
    public void activateFrenzy() {
        super.setSpeed(SPEED + FRENZY_SPEED);
    }

    /** Reverts pac behaviour back to normal. */
    public void deactivateFrenzy() {
        super.setSpeed(SPEED);
    }
}
