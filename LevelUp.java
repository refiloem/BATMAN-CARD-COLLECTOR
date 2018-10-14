import org.jbox2d.common.Vec2;
import city.soi.platform.*;

/**
 * Pick-ups in a game. When the player collides with the door, the
 * player proceeds to the next level.
 */

public class LevelUp extends Body implements CollisionListener {

    /** The game in which the player is playing. */
    private Game game;
    private GameLevel1 clear;
    private boolean completed;
    /**
     * Initialise a new orange.
     * @param g The game.
     */
    public LevelUp(Game game) {
        super(game.getWorld(), new PolygonShape(-20.0f,35.0f, -20.0f,-34.0f, 20.0f,-34.0f, 20.0f,35.0f, -20.0f,35.0f), Body.Type.STATIC);
        this.game = game;
        setImage(new BodyImage("images/door1.gif"));
        getWorld().addCollisionListener(this);
        completed = false;
    }
    
    public void collide(CollisionEvent e){
        
        Player player = game.getPlayer();
        if (e.getOtherBody() == player){ 
            if(game.getCurrentLevel().isCompleted() == true){
                
                System.out.println("Congratulations! You are progressing to the next level");
                 game.getNextLevel();
            }
        }
    }
}

