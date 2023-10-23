/**
 * An extension to the Level class for Level1.
 */
public class Level1 extends Level {
    private final static int TARGET_SCORE = 800;
    private final static String LEVEL_1_CSV = "res/level1.csv";

    /**
     * The constructor for Level1.
     * Automatically passes LEVEL_1_CSV and TARGET_SCORE as constructor parameter to superclass.
     */
    public Level1() {
        super(LEVEL_1_CSV, TARGET_SCORE);
    }
}
