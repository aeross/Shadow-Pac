import bagel.*;

/**
 * Skeleton Code for SWEN20003 Project 2, Semester 1, 2023
 *
 * Please enter your name below
 * Andrew Dharmaputra 1213935
 */
public class ShadowPac extends AbstractGame {

    private final static String GAME_TITLE = "SHADOW PAC";
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final Image BACKGROUND_IMAGE = new Image("res/background0.png");

    // game start and end
    private boolean Level0Start = false;
    private boolean Level0End = false;
    private boolean Level1Start = false;
    private boolean Level1End = false;
    private char Level0Outcome;
    private char Level1Outcome;

    // levels
    private final Level0 LEVEL_0;
    private final Level1 LEVEL_1;
    // I'm assuming the player has 3 lives in total, not 3 lives for each level,
    // so I'm instantiating it here instead of in Level.java
    private final Heart HEART = new Heart();

    // frames
    private int frameCounter = 0;
    private final static int LEVEL_COMPLETE_FRAMES = 300;

    // message
    private Message message = new Message();

    /**
     * The constructor for ShadowPac.
     */
    public ShadowPac() {
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
        LEVEL_0 = new Level0();
        LEVEL_1 = new Level1();
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowPac game = new ShadowPac();
        game.run();
    }

    /**
     * Performs a state update.
     * Allows the game to exit when the escape key is pressed.
     */
    protected void update(Input input) {

        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }
        BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);

        // level 0
        if (!Level0Start) {
            // Initial game state before space was pressed
            message.drawStart0();
            if (input.wasPressed(Keys.SPACE)) {
                Level0Start = true;
            }
        } else if (!Level0End) {
            // The game after space was pressed -- play level0!
            Level0Outcome = playLevel(input, LEVEL_0);
        }

        // if the player wins level 0
        if (Level0Outcome == Level.WIN && Level1Outcome != Level.WIN) {
            Level0End = true;
        }

        // level 1
        if (Level0End && !Level1Start) {
            // show level complete message for 300 frames
            // note that pressing space won't do anything during this 300 frames
            if (frameCounter < LEVEL_COMPLETE_FRAMES) {
                frameCounter++;
                message.drawLevelComplete();
            } else {
                // after that, display instructions
                // now, pressing space will start level 1
                message.drawStart1();
                if (input.wasPressed(Keys.SPACE)) {
                    Level1Start = true;
                }
            }
        } else if (Level0End && !Level1End) {
            // play level1
            Level1Outcome = playLevel(input, LEVEL_1);
        }

        // if the player wins level 1
        if (Level1Outcome == Level.WIN) {
            Level1End = true;
            message.drawWin();
        }

        // if the player loses all their lives
        if (HEART.getHearts() == 0) {
            Level1End = true;
            message.drawLose();
        }
    }

    /**
     * Allows the player to play a level until they win or run out of lives.
     * @param input player's keyboard input
     * @param level the level that is going to be played
     * @return char of either Level.WIN = 'w', Level.LOSE = 'l', or Level.PLAYING = 'p'.
     */
    private char playLevel(Input input, Level level) {
        char outcome = 0;
        if (HEART.getHearts() > 0) {
            HEART.drawHearts();
            outcome = level.play(input);

            if (outcome == Level.LOSE) {
                HEART.loseHearts(1);
            }
        }

        if (HEART.getHearts() == 0) {
            return Level.LOSE;
        }

        return outcome;
    }
}