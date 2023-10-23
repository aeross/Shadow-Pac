import bagel.*;
import bagel.util.*;

/**
 * The Wall class. A subclass to Entity.
 */
public class Wall extends Entity {
    private final static Image WALL_IMG = new Image("res/wall.png");

    /**
     * The constructor for Wall.
     * @param loc the initial location of wall
     */
    public Wall(Point loc) {
        super(WALL_IMG, loc);
    }
}
