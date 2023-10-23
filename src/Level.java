import bagel.*;
import bagel.util.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * The Level class.
 * Allows the player to play one level in the game.
 * There is one public method in Level, which is play().
 * <p></p>
 * To create a new level, simply create a subclass and pass in the world file (csv)
 * and the score required to win. The implementation of gameplay in this class is pretty
 * well-generalised -- there should be no need to write anything new in the subclasses,
 * unless if there's any specific behaviour unique to that level only.
 * This also means that it's custom level friendly. Just make a
 * new world file and a target score, and you're all set to create a new custom level!
 * <p></p>
 * Note: I'm assuming the ghost movements, the cherries, the pellet and frenzy mode, etc.
 * are not unique to Level1 only. I think those are very common features in Pac-Man.
 * If I, for example, were to create a custom level with similar features as above, putting the
 * code for all of those stuff in Level1.java instead of here would result in code duplication.
 * Hopefully this is an acceptable design approach :)
 */
public abstract class Level {

    // variables to keep track of frame count
    private int switchCounter = 0;
    private int frenzyCounter = 0;
    private final static int FRENZY_FRAME = 1000;     // the length of frenzy mode
    private final static int SWITCH_OPEN_FRAME = 15;  // frames to switch between pac open<->close

    // player's score
    private int score;
    private final int targetScore;
    /** The player wins this level */
    public final static char WIN = 'w';
    /** The player loses a life in this level (does not mean it's game over) */
    public final static char LOSE = 'l';
    /** The player is currently playing this level */
    public final static char PLAYING = 'p';

    // entities
    private Pac player;
    private ArrayList<Ghost> ghosts = new ArrayList<>();
    private ArrayList<Wall> walls = new ArrayList<>();
    private ArrayList<Dot> dots = new ArrayList<>();
    private ArrayList<Cherry> cherries = new ArrayList<>();
    private ArrayList<Pellet> pellets = new ArrayList<>();

    // frenzy
    private boolean frenzy = false;

    // message
    private Message message = new Message();

    /**
     * The constructor for Level.
     * @param fileName the world file which must be a csv file. This constructor
     *                  automatically reads csv from fileName and parses its content.
     * @param targetScore the score required for the player to achieve to win the game.
     */
    public Level(String fileName, int targetScore) {
        this.targetScore = targetScore;

        // code structure inspired by the code from lecture 8 slide 45
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String text;

            // string values in the CSV file that represent entities
            String PLAYER = "Player";
            String GHOST = "Ghost";
            String GHOST_RED = "GhostRed";
            String GHOST_BLUE = "GhostBlue";
            String GHOST_GREEN = "GhostGreen";
            String GHOST_PINK = "GhostPink";
            String WALL = "Wall";
            String DOT = "Dot";
            String CHERRY = "Cherry";
            String PELLET = "Pellet";

            // parse csv content
            while ((text = br.readLine()) != null) {
                String cells[] = text.split(",");
                String entity = cells[0];
                double x = Double.parseDouble(cells[1]);
                double y = Double.parseDouble(cells[2]);
                Point loc = new Point(x, y);

                // pac
                if (entity.equals(PLAYER)) {
                    player = new Pac(loc);

                // ghosts
                } else if (entity.equals(GHOST)) {
                    ghosts.add(new GhostRed(loc, true));
                } else if (entity.equals(GHOST_RED)) {
                    ghosts.add(new GhostRed(loc, false));
                } else if (entity.equals(GHOST_BLUE)) {
                    ghosts.add(new GhostBlue(loc));
                } else if (entity.equals(GHOST_GREEN)) {
                    ghosts.add(new GhostGreen(loc));
                } else if (entity.equals(GHOST_PINK)) {
                    ghosts.add(new GhostPink(loc));

                // other entities
                } else if (entity.equals(WALL)) {
                    walls.add(new Wall(loc));
                } else if (entity.equals(DOT)) {
                    dots.add(new Dot(loc));
                } else if (entity.equals(CHERRY)) {
                    cherries.add(new Cherry(loc));
                } else if (entity.equals(PELLET)) {
                    pellets.add(new Pellet(loc));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * The game is played here, in the play() method.
     * play() plays one level of the game.
     * @return char of either WIN = 'w', LOSE = 'l', or PLAYING = 'p'.
     */
    public char play(Input input) {

        // frame counter for pac to switch between open and close
        switchCounter = (switchCounter + 1) % SWITCH_OPEN_FRAME;
        if (switchCounter == 0) {
            player.switchOpen();
        }

        // this is where the game starts.
        // Draw all entities (plus score)
        drawEntities(dots);
        drawEntities(walls);
        drawEntities(cherries);
        drawEntities(pellets);
        message.drawScore(score);

        // move ghosts
        for (Ghost ghost : ghosts) {
            ghost.move(walls);
        }

        // move pac
        if (input.isDown(Keys.RIGHT)) {
            player.move(MovableEntity.FACING_RIGHT, walls);
            // 'else if' instead of 'if' to prevent diagonal movement
            // when multiple keys are pressed
        } else if (input.isDown(Keys.LEFT)) {
            player.move(MovableEntity.FACING_LEFT, walls);
        } else if (input.isDown(Keys.UP)) {
            player.move(MovableEntity.FACING_UP, walls);
        } else if (input.isDown(Keys.DOWN)) {
            player.move(MovableEntity.FACING_DOWN, walls);
        } else {
            player.draw();
        }

        // if the player has lost a life
        if (handleCollision()) {
            return LOSE;
        }

        // if the player has reached the target score
        if (score >= targetScore) {
            return WIN;
        }

        return PLAYING;
    }

    /**
     * A shortcut to draw multiple entities in one line.
     */
    private void drawEntities(ArrayList<? extends Entity> entities) {
        for (Entity entity : entities) {
                entity.draw();
        }
    }

    /**
     * Activates frenzy mode.
     */
    private void activateFrenzy() {
        for (Ghost ghost : ghosts) {
                ghost.activateFrenzy();
        }
        player.activateFrenzy();
        frenzy = true;
    }

    /**
     * Deactivates frenzy mode.
     */
    private void deactivateFrenzy() {
        for (Ghost ghost : ghosts) {
                ghost.deactivateFrenzy();
        }
        player.deactivateFrenzy();
        frenzy = false;
    }

    /**
     * Handles player behaviour when there is a collision between player
     * and any other entities.
     * @return boolean true if the collision causes player to lose a life, false otherwise.
     */
    private boolean handleCollision() {
        // ghost
        int i = player.isColliding(ghosts);
        if (i >= 0) {
            if (frenzy) {
                // disappear until frenzy mode is finished, then respawn at starting location.
                // a neat trick to make the ghost "disappear" without actually removing it:
                // put it off-screen.
                ghosts.get(i).setLocation(Entity.OFF_SCREEN);
                score += Ghost.SCORE;
            } else {
                // move to starting location
                ghosts.get(i).moveToStart();
                player.moveToStart();
                return true;
            }
        }

        // dot
        i = player.isColliding(dots);
        if (i >= 0) {
            // increase score by 10
            dots.remove(i);
            score += Dot.SCORE;
        }

        // cherry
        i = player.isColliding(cherries);
        if (i >= 0) {
            // increase score by 20
            cherries.remove(i);
            score += Cherry.SCORE;
        }

        // pellet
        i = player.isColliding(pellets);
        if (i >= 0) {
            // activate frenzy mode
            pellets.remove(i);
            activateFrenzy();
            frenzyCounter = 0;
        }

        // after activation, deactivate frenzy mode after 1000 frames
        if (frenzy) {
            frenzyCounter++;
            if (frenzyCounter == FRENZY_FRAME) {
                deactivateFrenzy();
            }
        }
        return false;
    }
}
