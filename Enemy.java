import org.jbox2d.common.Vec2;
import java.lang.Object;
import city.soi.platform.*;

/** Implements both: 
 * collision listener for when the player collides with an enemy
   and step listener, to control the movement of the enemy*/
public class Enemy extends Body implements CollisionListener, StepListener
{
    
    private Game game;
    private World world;  
    // private int life;
    private Life life;
    private Player player;
   
   
    /**
     * Initialise an enemy.
     * @param g The game.
     */
    public Enemy(Game game) 
    {
        super(game.getWorld(), new PolygonShape(-30.0f,29.5f, -30.0f,-31.5f, 32.0f,-31.5f, 32.0f,29.5f, -30.0f,29.5f) );
        this.game = game;
        this.setImage(new BodyImage("images/flying-animated-bat.gif"));
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
        //System.out.println(game.getEnemy2().getPosition());
        //System.out.println(game.getEnemy2().getPosition());
        
    }
    
    /** This creates the movement of the enemy going up and down */
    public Vec2 enemyBehaviour1()
    {
        Vec2 move = new Vec2();
        float up = 200;
        float down = -130;
        float middleup = -50;
        
        if(game.getEnemy1().getPosition().y < up && game.movinginvisible.getPosition().y > middleup )
        {
            move = (new Vec2(0,30));
        }        
        
        if(game.getEnemy1().getPosition().y > down && game.movinginvisible.getPosition().y < middleup )
        {
            move = (new Vec2(0,-30));
        }        
        
        return move;
    }
    
    public Vec2 enemyBehaviour2()
    {
        Vec2 move = new Vec2();
        float up = 200;
        float down = -170;
        float middleup = -50;
        
        if(game.getEnemy2().getPosition().y < up && game.movinginvisible.getPosition().y > middleup )
        {
            move = (new Vec2(0,50));
        }        
        
        if(game.getEnemy2().getPosition().y > down && game.movinginvisible.getPosition().y < middleup )
        {
            move = (new Vec2(0,-50));
        }        
        
        return move;
    }
 }


        
