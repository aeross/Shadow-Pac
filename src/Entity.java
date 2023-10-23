import bagel.*;
import bagel.util.*;

import java.util.ArrayList;

/**
 * Entity class.
 * An entity is defined as something that physically exists and can interact in some way
 * with other entities in game.
 * Each entity has a (x,y) coordinate that points to its certain location
 * on the map, an image, and a bounding box derived from its location and image size.
 */
public abstract class Entity {
    /** The "off-screen" location for entities that are temporarily removed from the map.
     */
    public final static Point OFF_SCREEN = new Point(1200, 1200);
    private Point loc;
    private Image image;
    private Rectangle boundingBox;

    /**
     * The constructor for Entity. Automatically builds the bounding box based on param value.
     * @param image the image of this entity
     * @param loc the starting location of this entity
     */
    public Entity(Image image, Point loc) {
        this.loc = loc;
        this.image = image;
        this.boundingBox = new Rectangle(loc.x, loc.y, this.image.getWidth(),
                this.image.getHeight());
    }


    /**
     * A getter for the entity's location.
     * @return Point the entity's current location.
     */
    public Point getLocation() {
        return this.loc;
    }

    /**
     * A getter for the entity's image.
     * @return Image the entity's image.
     */
    public Image getImage() {
        return this.image;
    }

    /**
     * A getter for the entity's bounding box.
     * @return Rectangle the entity's bounding box.
     */
    public Rectangle getBoundingBox() {
        return this.boundingBox;
    }

    /**
     * A setter for the entity's location.
     * @param newLoc the entity's new location.
     */
    public void setLocation(Point newLoc) {
        this.loc = newLoc;
        this.boundingBox = new Rectangle(this.loc.x, this.loc.y, this.image.getWidth(),
                this.image.getHeight());
    }

    /**
     * A setter for the entity's image.
     * @param newImage the entity's new image.
     */
    public void setImage(Image newImage) {
        this.image = newImage;
    }


    /** Draws an entity into the game.
     */
    public void draw() {
        double x = this.loc.x;
        double y = this.loc.y;
        this.image.drawFromTopLeft(x, y);
    }

    /**
     * Draws an entity into the game.
     * @param options Bagel's DrawOptions for various options for drawing the entity.
     */
    public void draw(DrawOptions options) {
        double x = this.loc.x;
        double y = this.loc.y;
        this.image.drawFromTopLeft(x, y, options);
    }

    /** Checks if two entities collide (i.e., their bounding boxes intersect)
     * @param entity the entity to be checked for collision
     * @return boolean true if colliding, false if not colliding
     */
    public boolean isColliding(Entity entity) {
        return this.boundingBox.intersects(entity.boundingBox);
    }

    /**
     * Checks if this entity collides with any one of `ArrayList<entities>'.
     * @param entities the `ArrayList<entities>' to be checked for collision
     * @return boolean the index of `ArrayList<entities>' that are colliding, or -1 if none are
     *         colliding (with the assumption that only one entity in `ArrayList<entities>'
     *         can collide with this entity at a time)
     */
    public int isColliding(ArrayList<? extends Entity> entities) {
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i) != null && isColliding(entities.get(i))) {
                return i;
            }
        }
        return -1;
    }
}