import bagel.*;
import bagel.util.*;

import java.util.ArrayList;

/**
 * An extension of the Entity class.
 * The Entity class assumes an entity is static or immovable,
 * whereas this class assumes movability and therefore has additional move methods
 * and rotations towards its image based on move direction.
 */
public abstract class MovableEntity extends Entity {
    /** Specifies the right direction the entity is facing. */
    public final static double FACING_RIGHT = 0;
    /** Specifies the down direction the entity is facing. */
    public final static double FACING_DOWN = Math.PI / 2;
    /** Specifies the left direction the entity is facing. */
    public final static double FACING_LEFT = Math.PI;
    /** Specifies the up direction the entity is facing. */
    public final static double FACING_UP = 1.5 * Math.PI;
    private double facingCurr = FACING_RIGHT;  // default value is 0, which is facing right
    private final Point startingLoc;
    private double speed;

    /**
     * The constructor for MovableEntity.
     * @param image the image of this entity
     * @param loc the starting location of this entity
     * @param speed the moving speed of this entity
     */
    public MovableEntity(Image image, Point loc, double speed) {
        super(image, loc);
        this.startingLoc = new Point(loc.x, loc.y);
        this.speed = speed;
    }

    /**
     * A setter for move speed.
     * @param speed the new speed value.
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }


    /**
     * Draws entity based on the direction it's facing
     * after the last move, overriding draw() from Entity class.
     */
    @Override
    public void draw() {
        DrawOptions options = new DrawOptions();
        options.setRotation(facingCurr);
        super.draw(options);
    }

    /**
     * The difference between move() and draw() is that in a frame,
     * move is called when an entity moves to a certain direction, whereas
     * draw is only called when an entity remains still in that particular frame.
     *
     * @param moveDirection the direction this entity is going to move towards
     * @param rotation a true or false parameter -- true if moving
     *                 to a certain direction will cause the entity to rotate
     *                 towards that direction, false if the entity never rotates
     */
    private void move(Double moveDirection, boolean rotation) {
        if (rotation) facingCurr = moveDirection;
        Point currLoc = super.getLocation();
        Point newLoc = null;

        // edge case: if entity is off-screen, don't move
        if (currLoc.equals(Entity.OFF_SCREEN)) {
            return;
        }

        // otherwise, move as normal
        if (moveDirection == FACING_RIGHT) {
            newLoc = new Point(currLoc.x + speed, currLoc.y);
        } else if (moveDirection == FACING_LEFT) {
            newLoc = new Point(currLoc.x - speed, currLoc.y);
        } else if (moveDirection == FACING_UP) {
            newLoc = new Point(currLoc.x, currLoc.y - speed);
        } else if (moveDirection == FACING_DOWN) {
            newLoc = new Point(currLoc.x, currLoc.y + speed);
        }

        super.setLocation(newLoc);
        draw();
    }

    /**
     * Moves entity to a location where it does not intersect with other specified entities.
     * If it intersects, the entity stays in place and doesn't move.
     *
     * @param moveDirection the direction this entity is going to move towards
     * @param entities the entities that prevent this entity from moving through
     * @param rotation a true or false parameter -- true if moving
     *                 to a certain direction will cause the entity to rotate
     *                 towards that direction, false if the entity never rotates
     * @return boolean false if it moves to a non-empty spot (i.e., if it moves, it
     *         would have intersected with another entity), true otherwise
     */
    public boolean moveToEmpty(Double moveDirection, ArrayList<? extends Entity> entities,
                               boolean rotation) {
        double x = super.getLocation().x;
        double y = super.getLocation().y;
        boolean empty = true;

        // To prevent the entity from intersecting with another entity, need to check for
        // the entity's all possible moves before the player makes a move,
        // and prevent the entity from making moves that hit another entity.
        Rectangle rightMove = new Rectangle(x + speed, y,
                super.getImage().getWidth(), super.getImage().getHeight());
        Rectangle leftMove = new Rectangle(x - speed, y,
                super.getImage().getWidth(), super.getImage().getHeight());
        Rectangle downMove = new Rectangle(x, y + speed,
                super.getImage().getWidth(), super.getImage().getHeight());
        Rectangle upMove = new Rectangle(x, y - speed,
                super.getImage().getWidth(), super.getImage().getHeight());

        for (Entity e : entities) {
            if (e != null) {
                Rectangle bound = e.getBoundingBox();
                if (moveDirection == FACING_RIGHT && bound.intersects(rightMove)) {
                    // if the entity hits another specified entity, the entity does not move
                    // but the direction the entity is facing still changes.
                    if (rotation) facingCurr = FACING_RIGHT;
                    draw();
                    empty = false;
                } else if (moveDirection == FACING_LEFT && bound.intersects(leftMove)) {
                    if (rotation) facingCurr = FACING_LEFT;
                    draw();
                    empty = false;
                } else if (moveDirection == FACING_UP && bound.intersects(upMove)) {
                    if (rotation) facingCurr = FACING_UP;
                    draw();
                    empty = false;
                } else if (moveDirection == FACING_DOWN && bound.intersects(downMove)) {
                    if (rotation) facingCurr = FACING_DOWN;
                    draw();
                    empty = false;
                }
            }
        }
        if (empty) move(moveDirection, rotation);
        return empty;
    }

    /** Moves entity to its starting position. */
    public void moveToStart() {
        facingCurr = FACING_RIGHT;  // revert facingCurr to default
        super.setLocation(startingLoc);
        super.draw();
    }
}
