import bagel.*;
import bagel.util.*;

/**
 * The class that handles all in-game messages.
 * This could honestly be a singleton, but since I've heard bad things about it,
 * I'll stay away from such design pattern for this project.
 * Feedback as to whether a singleton is appropriate here is appreciated.
 */
public class Message {

    // in-game font size
    private final static String FONT = "res/FSO8BITR.ttf";
    private final static int DEFAULT_SIZE = 64;

    // messages
    private final static String TITLE = "SHADOW PAC";
    private final static String INSTRUCTIONS_1 = "PRESS SPACE TO START";
    private final static String INSTRUCTIONS_2 = "USE ARROW KEYS TO MOVE";
    private final static String INSTRUCTIONS_3 = "EAT THE PELLET TO ATTACK";
    private final static String LEVEL_COMPLETE = "LEVEL COMPLETE!";
    private final static String WIN = "WELL DONE!";
    private final static String LOSE = "GAME OVER!";
    private final static String SCORE = "SCORE";

    // text to be drawn on the game
    private Font gameText;

    // draw text at given x and y coordinates.
    private void drawText(String text, double x, double y, int fontSize) {
        gameText = new Font(Message.FONT, fontSize);
        gameText.drawString(text, x, y);
    }

    // if x and y coordinates are not specified, draw at center.
    // note: no longer centered if text consists of multiple lines (\n),
    // because Bagel automatically left-aligns the text.
    private void drawText(String text, int fontSize) {
        gameText = new Font(Message.FONT, fontSize);
        double xCenter = Window.getWidth() / 2.0;
        double yCenter = Window.getHeight() / 2.0;
        xCenter = xCenter - (gameText.getWidth(text) / 2.0);
        yCenter = yCenter + (fontSize / 2.0);
        gameText.drawString(text, xCenter, yCenter);
    }

    /**
     * draws the instruction message before level 0 starts.
     */
    public void drawStart0() {
        Point TITLE_LOC = new Point(260, 250);
        Point INSTRUCTIONS_1_LOC = new Point(260 + 60, 250 + 190);
        Point INSTRUCTIONS_2_LOC = new Point(260 + 60 - 12, 250 + 190 + 40);
        int INSTRUCTIONS_SIZE = 24;

        // SHADOW PAC
        drawText(TITLE, TITLE_LOC.x, TITLE_LOC.y, DEFAULT_SIZE);
        // PRESS SPACE TO START
        drawText(INSTRUCTIONS_1, INSTRUCTIONS_1_LOC.x, INSTRUCTIONS_1_LOC.y,
                INSTRUCTIONS_SIZE);
        // USE ARROW KEYS TO MOVE
        drawText(INSTRUCTIONS_2, INSTRUCTIONS_2_LOC.x, INSTRUCTIONS_2_LOC.y,
                INSTRUCTIONS_SIZE);
    }

    /**
     * draws the instruction message before level 1 starts.
     */
    public void drawStart1() {
        Point INSTRUCTIONS_1_LOC = new Point(200, 350);
        Point INSTRUCTIONS_2_LOC = new Point(200 - 23, 350 + 60);
        Point INSTRUCTIONS_3_LOC = new Point(200 - 46, 350 + 120);
        int INSTRUCTIONS_SIZE = 40;

        // PRESS SPACE TO START
        drawText(INSTRUCTIONS_1, INSTRUCTIONS_1_LOC.x, INSTRUCTIONS_1_LOC.y,
                INSTRUCTIONS_SIZE);
        // USE ARROW KEYS TO MOVE
        drawText(INSTRUCTIONS_2, INSTRUCTIONS_2_LOC.x, INSTRUCTIONS_2_LOC.y,
                INSTRUCTIONS_SIZE);
        drawText(INSTRUCTIONS_3, INSTRUCTIONS_3_LOC.x, INSTRUCTIONS_3_LOC.y,
                INSTRUCTIONS_SIZE);
    }

    /**
     * Draws the winning message (after level1 is won).
     */
    public void drawWin() {
        drawText(WIN, DEFAULT_SIZE);
    }

    /**
     * Draws the losing message.
     */
    public void drawLose() {
        drawText(LOSE, DEFAULT_SIZE);
    }

    /**
     * Draws the message after level0 is won.
     */
    public void drawLevelComplete() {
        drawText(LEVEL_COMPLETE, DEFAULT_SIZE);
    }

    /**
     * Draws the amount of score the player has.
     */
    public void drawScore(int score) {
        Point SCORE_LOC = new Point(25, 25);
        int SCORE_SIZE = 20;

        drawText(SCORE + " " + Integer.toString(score), SCORE_LOC.x
                , SCORE_LOC.y, SCORE_SIZE);
    }

}

