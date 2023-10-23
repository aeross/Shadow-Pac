import bagel.Image;
import bagel.util.Point;

/**
 * The Heart class.
 * This class is hard-coded to specifically have 3 lives in total.
 */
public class Heart {
    private final Point HEART_LOC = new Point(900, 10);
    private final static int NEXT_HEART = 30;
    private final Image HEART_IMG = new Image("res/heart.png");
    private final static int LIVES = 3;
    private int livesLost = 0;


    /** A getter to get how many lives a player has in the game.
     *
     * @return int number of lives the player has
     */
    public int getHearts() {
        return LIVES - livesLost;
    }

    /**
     * Draws hearts onto the game's screen.
     */
    public void drawHearts() {
        double x = HEART_LOC.x;
        double y = HEART_LOC.y;
        int currLives = getHearts();

        while (currLives > 0) {
            HEART_IMG.drawFromTopLeft(x, y);
            x += NEXT_HEART;
            currLives--;
        }
    }

    /** method to add how many lives is lost in the game.
     * @param livesLost: an integer specifying how many lives is lost
     */
    public void loseHearts(int livesLost) {
        this.livesLost += livesLost;
        if (this.livesLost > LIVES) this.livesLost = LIVES;
    }
}
