import bagel.*;
import bagel.util.*;

/**
 * The Dot class. An extension to the Entity class.
 */
public class Dot extends Entity {
    private final static Image DOT_IMG = new Image("res/dot.png");
    /** Score for player if the dot is eaten. */
    public final static int SCORE = 10;

    /**
     * The constructor for dot.
     * @param loc the initial location of dot
     */
    public Dot(Point loc) {
        super(DOT_IMG, loc);
    }

}
