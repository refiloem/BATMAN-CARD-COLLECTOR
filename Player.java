import javax.swing.JFrame;
import city.soi.platform.*;

/** The player in the game, implements steplistener to check whether the player is still eligible to play 
   */
public class Player extends Actor implements StepListener
{
    private int cards;
    private int life;
    private Game game;
    
    /**
     * Initialise a new player.
     * @param game  The game in which the player will be playing.
     */
    public Player(Game game)
    {
        super(game.getWorld(), new PolygonShape(-16.5f,19.5f, 2.5f,27.5f, 15.5f,22.5f, 19.5f,6.5f, -2.5f,-26.5f, -19.5f,-28.5f, -16.5f,19.5f));
        this.game = game;
        cards = 0;//no of cards collected
        life = 3;//no of lives
        String filename = "images/smooooch_joker_by_gothicraft-d5r2li13.gif";//image of the player
        BodyImage playerImage = new BodyImage(filename);
        setImage(playerImage);
        setFillColor(java.awt.Color.MAGENTA);
        setLineColor(java.awt.Color.BLACK);
    }
    
    /** Increase the cards count. */
    public void incrementCardCount()
    {
        cards++;
    }
    
    /** Increase the life count. */
     public void incrementLifeCount()
    {
        life++;
    }
    
    /** Decrease the card count. */
    public void decrementCardCount()
    {
        cards--;
    }
    
    /** Decrease the life count. */
    public void decrementLifeCount()
    {
        life--;
    }
    
    /** The number of cards the player currently has. */
    public int getCard()
    {
        return cards;
    }
    
    
    /** resets the number of cards the player currently has. */
    public void resetCardCount()
    {
        cards = 0;
        System.out.println("card count is now " + cards);
    }
    
    /** The number of cards the player currently has. */
    public int getLife()
    {
        return life;
    }
    
    /** The number of cards the player currently has. */
    public void resetLives()
    {
        life = 3;
        System.out.println("the number of lives is now " + life);
    }
    
    @Override
    public void preStep(StepEvent se) 
    {
        
    }
     
    /** checks to see whether the player has anymore lives remaining*/
    @Override
    public void postStep(StepEvent se) 
     {
         if (life == 0)
         {
             System.exit(0);//if life count hits zero, game over!!
        }
    }
}





















