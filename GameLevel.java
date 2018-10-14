import city.soi.platform.*;
import java.util.List;

/**
 * Abstract class GameLevel. A game level populates a world with bodies
 * and provides methods for initialising the game state and checking
 * if the level has been completed.
 */
public abstract class GameLevel
{
    /** The game to which this level belongs. */
    protected Game game;
    
    /**
     * Initialise a new game level.
     * @param game the game to which this level belongs
     */
    public GameLevel(Game game) {
        this.game = game;
    }
    
    /**
     * Populate the world with bodies.
     */
    protected abstract void populate();
    
    /**
     * Has this level been completed?
     */
    public abstract boolean isCompleted();
    
    /**
     * Set player attributes (position, velocity, etc) to the approriate start
     * values for this level. This method may be called, for example, when play
     * starts or after the player has lost a life.
     */
    public abstract void putPlayerAtStart();
    
    /**
     * Destroy all bodies except those needed for the next level.
     * This version destroys all bodies except the player.
     * Override in sub-classes as necessary.
     */
    public void clearLevel() {
        List<Body> bodies = game.getWorld().getBodies();
        Player player = game.getPlayer();
        for (Body b : bodies) {
            b.destroy();
        }
    }
    
}