import org.jbox2d.common.Vec2;
import java.lang.Object;
import city.soi.platform.*;

/** Implements both: 
 * collision listener for when the player collides with an enemy
   and step listener, to control the movement of the enemy*/
public class Robin extends Body implements CollisionListener, StepListener
{
    
    private Game game;
    private World world;  
    // private int life;
    private Life life;
    private Life life1;
    private Life life2;
    private Player player;
   
   
    /**
     * Initialise an enemy.
     * @param g The game.
     */
    public Robin(Game game) 
    {
        super(game.getWorld(), new PolygonShape(35.5f,36.5f, -21.5f,17.5f, -36.5f,-32.5f, 34.5f,-33.5f, 35.5f,36.5f) );
        this.game = game;
        this.setImage(new BodyImage("images/r.gif"));
         this.player = game.getPlayer();
             getWorld().addCollisionListener(this);
        game.getWorld().addStepListener(this);
        this.life = game.getLife();
        this.setGravityStrength(0);//allows bodies to act without gravitational pull.
    }
   
    /** Handles the reseting of a player*/
    public void resetPlayer() 
    {            
        Player player = game.getPlayer();
        player.setPosition(new Vec2(0, -250));
    }
   
    /** If the player falls off*/
    public void collide(CollisionEvent e){
         Player player = game.getPlayer();
         life = game.getLife();
         life1 = game.getLife1();
         life2 = game.getLife2();
         //player loses a life then is reset
        if (e.getOtherBody() == player )
        {
             player.decrementLifeCount();
             resetPlayer();
             destroy();
             System.out.println("Life lost, new life count is" + " " + game.getPlayer().getLife());
        }

    }
     
    @Override
    public void preStep(StepEvent se) 
    {
    }
    
    /**Step listener waits for conditions to be met before enabling the correct events*/
    @Override
    public void postStep(StepEvent se) 
    {
    
    }
    
    /** This creates the movement of the enemy going up and down */
    public Vec2 enemyBehaviour()
    {
        Vec2 move;
        move = new Vec2(0,0);
        return move;
    }
    
 }


        
