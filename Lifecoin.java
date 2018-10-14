import city.soi.platform.*;
import org.jbox2d.common.Vec2;

/** implements collision listener, for when a collides with the coin */
public class Lifecoin extends Body implements CollisionListener
{
    private Game game;
    private World world;
    private Player player;
    private Life life;
    
    
    /**
     * initialize a new Lifecoin
     * @param game  The game in which the Lifecoin will appear
     */
    public Lifecoin(Game game) {
        super(game.getWorld(), new PolygonShape(-30.0f,29.5f, -30.0f,-31.5f, 32.0f,-31.5f, 32.0f,29.5f, -30.0f,29.5f));
        this.game = game;
        game.getWorld().addCollisionListener(this);
       this.player = game.getPlayer();
       this.life = game.getLife();
       setImage(new BodyImage("images/lifecoin.png")); 
       getWorld().addCollisionListener(this);
    }
    
    /** Resets the amount of lives */
     public void resetLife() 
     {   
        Life life =  new Life(game);
        life.setPosition(new Vec2(-450,325));
    }
    
    /**If the player collides with a lifecoin, his lives go up and the coin should dissapear*/
    public void collide(CollisionEvent e)
    {
        if (e.getOtherBody()== game.getPlayer()){ 
            game.getPlayer().incrementLifeCount();//increments the life count
            resetLife();//new life gained(shows a new life body)
            destroy();//coin dissapears
            System.out.println("Life gained, new life count is" + " " + game.getPlayer().getLife());
        }
    }
}
  
 

