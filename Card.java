import org.jbox2d.common.Vec2;
import city.soi.platform.*;
import javax.swing.JOptionPane; 
import javax.swing.JFrame;

/**
 * Pick-ups in a game. When the player collides with a card, the
 * player's cards count is increased and the card is removed
 * from the world. If all cards are collected game ends.
 */
public class Card extends Body implements CollisionListener, StepListener
{
    /** The game in which the player is playing. */
    private Game game;
    private Player player; 
    
    /**
     * Initialise a new card.
     * @param g The game.
     */
    public Card(Game game)
    {
       super(game.getWorld(), new PolygonShape(-25.0f,-20.5f, 26.0f,-20.5f, 26.0f,19.5f, -25.0f,19.5f, -25.0f,-20.5f));
       this.game = game;
       this.player = game.getPlayer();
       game.getWorld().addCollisionListener(this);
       game.getWorld().addStepListener(this);
       setImage(new BodyImage("images/jokers-card-design.jpg"));  
    }

    /** if a player collides with a card, the card count is incremented and the card disappears */
    public void collide(CollisionEvent e){
        
        if (e.getOtherBody() == game.getPlayer() ){
             
            game.getPlayer().incrementCardCount();
              destroy();
           System.out.println("Number of cards = " + player.getCard());
        }
        
    }
    
    @Override
    public void preStep(StepEvent se) 
    {
   
    }
     
    /** checks if all cards are collected before ending game*/
    @Override
    public void postStep(StepEvent se) 
     {
        
    }
}
