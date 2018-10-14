import org.jbox2d.common.Vec2;
import java.lang.Object;
import city.soi.platform.*;

/** Implements both: 
 * collision listener for when the player collides with an enemy
   and step listener, to control the movement of the enemy*/
public class Batman extends Body implements CollisionListener, StepListener
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
    public Batman(Game game) 
    {
        super(game.getWorld(), new PolygonShape(-30.0f,29.5f, -30.0f,-31.5f, 32.0f,-31.5f, 32.0f,29.5f, -30.0f,29.5f) );
        this.game = game;
        this.setImage(new BodyImage("images/bwl1.gif"));
         this.player = game.getPlayer();
             getWorld().addCollisionListener(this);
        game.getWorld().addStepListener(this);
        this.life = game.getLife();
        
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
    
    public Vec2 enemyBehaviourBatman()
    {
        Vec2 move = new Vec2();
        float left = -100;
        float right = 100;
        float middle = 150;
        
        System.out.println(game.getBatman().getPosition().x);
        if(game.getBatman().getPosition().x > left && game.movinginvisible2.getPosition().x < middle)
        {
            this.setImage(new BodyImage("images/bwl1.gif"));
            move = (new Vec2(-30,0));
        }        
        
        
        if(game.getBatman().getPosition().x < right  && game.movinginvisible2.getPosition().x > middle)
        {
            this.setImage(new BodyImage("images/bwr1.gif"));
            move = (new Vec2(30, 0));
        }        
        
        return move;
    }
 }


        
