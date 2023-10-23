import bagel.*;
import bagel.util.*;

/**
 * The Cherry class. An extension to the Entity class.
 */
public class Cherry extends Entity {
    private final static Image CHERRY_IMG = new Image("res/cherry.png");
    /** Score for player if the cherry is eaten. */
    public final static int SCORE = 20;

    /**
     * The constructor for Cherry.
     * @param loc the initial location of cherry
     */
    public Cherry(Point loc) {
        super(CHERRY_IMG, loc);
    }
}
