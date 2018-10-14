import javax.swing.JOptionPane; 
import javax.swing.JFrame;
import city.soi.platform.*;
import org.jbox2d.common.Vec2;

/**The life body, which shows the number of lives the player has
   implements step listener to check:
   -number of lives
   -and to ensure player does not exceed maximum bounds
   */
public class Life extends Body implements StepListener
{
    private int life;
    private Player player;
    private Body ground;
    private Game game; 
    private Enemy enemy;
    
    /** Creating the life body*/
    public Life(Game game)
    {    
        super(game.getWorld(), new CircleShape(19), Body.Type.STATIC);
        this.game = game;
        this.player = game.getPlayer();
        setImage(new BodyImage("images/joker-portrait.png"));
        this.player = game.getPlayer();
        this.ground = game.getGround();
        game.getWorld().addStepListener(this);
        this.life = game.getPlayer().getLife();
    }
    
    /** increments player life */
     public void incrementPlayerLife()
    {
         player.incrementLifeCount();
    }
    
    /** Decrements player life*/
    public void decrementPlayerlife()
    {
        player.decrementLifeCount();
    }
    
    /**checks the number of lives the player has*/
    public int getPlayerLife()
    {
        return player.getLife();
    }

    
    @Override
    public void postStep(StepEvent se) 
    {
        
    }
    
    /** checks to ensure player is not out of bounds, also that they are still eligible to play */
    @Override
    public void preStep(StepEvent se)  
    {
          if(game.getPlayer().getPosition().y<-450||game.getPlayer().getPosition().y>600) 
          {    
              destroy();
              decrementPlayerlife();
              Player player = game.getPlayer();
              player.setPosition(new Vec2(0, -250));
          } 
      
          if(game.getPlayer().getPosition().x<-1500||game.getPlayer().getPosition().x>1500)  
          {
              destroy();
              decrementPlayerlife();
              enemy.resetPlayer();
            }
      
          if (game.getPlayer().getLife() == 0)
          {
              game.gameOver();
              JOptionPane.showMessageDialog(null,"!!GAME OVER!!" );
              System.exit(0);
          }
    }
}