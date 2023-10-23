import bagel.*;
import bagel.util.*;

/**
 * The Pellet class. A subclass to Entity.
 */
public class Pellet extends Entity {
    private final static Image PELLET_IMG = new Image("res/pellet.png");

    /**
     * The constructor for Pellet.
     * @param loc the initial location of pellet
     */
    public Pellet(Point loc) {
        super(PELLET_IMG, loc);
    }
}
