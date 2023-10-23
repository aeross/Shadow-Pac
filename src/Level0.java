/**
 * An extension to the Level class for Level0.
 */
public class Level0 extends Level {
    private final static int TARGET_SCORE = 1210;
    private final static String LEVEL_0_CSV = "res/level0.csv";

    /**
     * The constructor for Level0.
     * Automatically passes LEVEL_0_CSV and TARGET_SCORE as constructor parameter to superclass.
     */
    public Level0() {
        super(LEVEL_0_CSV, TARGET_SCORE);
    }
}
