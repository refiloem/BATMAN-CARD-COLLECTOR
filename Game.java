import city.soi.platform.*;
import java.applet.AudioClip;
import java.net.URL;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.*;
import java.net.URL;
import java.awt.Font;
import org.jbox2d.common.Vec2;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import javax.swing.JApplet;
import java.util.List;
import javax.sound.sampled.LineUnavailableException;

/**
 * Created by Refiloe Matlapeng
 * 2016/04/19
 * Gaborone, Botswana
 * A very basic platform game.
 */

public class Game implements StepListener
{
    /** The player (a specialised Actor). */
    private Player player;
    private Enemy enemy1;
    private Enemy enemy2;
    private Robin robin;
    private Batman batman;
    private Card card;
    public Body movinginvisible;
    public Body movinginvisible2;
   
    /** Game over flag. */
    private boolean isOver;
    private Life life;
    private Life life1;
    private Life life2;
    public Body ground;
    private LevelUp levelup;
    
    /** The World in which the game bodies move and interact.*/
    private World world;
    private Game game;
    
    private Lifecoin coin;
    
    /** A graphical display of the world (a specialised JPanel). */
    private WorldView view;
    private final JFrame frame;
    
    /** game levels */
    private GameLevel level1;
    private GameLevel level2;
    private GameLevel level3;
    private GameLevel currentlevel;
    
    /** game views */
    GameViewA viewA;
    GameViewB viewB;
    GameViewC viewC;
    /** A debug display. */
    private DebugViewer debugViewer;
    private Body startImg;

    /** Initialise a new Game. */
    public Game() {
        isOver = false;
    
        //make the world
        world = new World();
        frame=new JFrame("Game");
        frame.setResizable(false);
      
        //final JLabel lives = new JLabel();
        //final JLabel points = new JLabel();
        //final JLabel card = new JLabel();
        
        //adding steplistener
        world.addStepListener(this);
        
        //making the game levels
        level1 = new GameLevel1(this);
        level2 = new GameLevel2(this);
        level3 = new GameLevel3(this);
        currentlevel = level1;
        
        //enemies
        enemy1 = new Enemy(this);
        enemy2 = new Enemy(this);
        enemy1.setPosition(new Vec2(100,0));
        enemy2.setPosition(new Vec2(-100,0));
        
        //new instance of player
        player = new Player(this);
        player.setPosition(new Vec2(0,-200));
    
        currentlevel.populate();
        currentlevel.putPlayerAtStart();
 
        // make a view
        viewA = new GameViewA(this, 1500, 1500);
        viewA.paintBackground();
       
        //invisible palform - used to act as movement for enemies, see enemy code.
        movinginvisible = new SlidingPlatform(world, PolygonShape.makeBox(50, 20), new Vec2(0, 250), 20.0f);
        movinginvisible.setPosition(new Vec2(0, -150));
        //movinginvisible.setImage(new BodyImage("images/platform-2.png"));
        movinginvisible.setGhostly(true);
        movinginvisible.setRenderLayer(-3);
        movinginvisible.setVisible(false);
        
        movinginvisible2 = new SlidingPlatform(world, PolygonShape.makeBox(50, 20), new Vec2(300, 0), 7.0f);
        movinginvisible2.setPosition(new Vec2(0, 50));
        //movinginvisible2.setImage(new BodyImage("images/platform-2.png"));
        movinginvisible2.setGhostly(true);
        movinginvisible2.setRenderLayer(-2);
        movinginvisible2.setVisible(false);
        
        // display the view in a frame
        final JFrame frame = new JFrame("Game");
        
        // add keyboard handling
        frame.addKeyListener(new java.awt.event.KeyAdapter(){
            
            /** Handle key press events for walking and jumping. */
            public void keyPressed(java.awt.event.KeyEvent e)
            {
                if (isOver) return;
                int code = e.getKeyCode();
                // SPACE = jump
                if (code == java.awt.event.KeyEvent.VK_SPACE) {
                    
                    // only jump if player is not already jumping
                    if (!player.isJumping()) {
                        player.jump(200);
                    }
                    
                // W button = walk left
                } else if (code == java.awt.event.KeyEvent.VK_LEFT) {
                    
                    player.setImage(new BodyImage("images/smooooch_joker_by_gothicraft-d5r2li15.gif"));
                    player.walkLeft(90);
                    
                // O button = walk right
                } else if (code == java.awt.event.KeyEvent.VK_RIGHT) {
                    
                    player.setImage(new BodyImage("images/smooooch_joker_by_gothicraft-d5r2li13.gif"));
                    player.walkRight(90);
                    
                // F1 key toggles display of debug view
                } else if (code == java.awt.event.KeyEvent.VK_F1) {
                    
                    if (debugViewer == null) debugViewer = new DebugViewer(new DebugSettings(world));
                    
                      if (debugViewer.isRunning()) {
                        
                           debugViewer.stopViewer();
                           
                        } else {
                            
                           debugViewer.startViewer();
                           
                        }  
                }
                
                 // R button == restart
                else if (code == KeyEvent.VK_R) {
                restartGame();
               }
                 else if (code == java.awt.event.KeyEvent.VK_P){
                        world.pause();
                   
                   // ENTER button = play game
                    } else if (code == java.awt.event.KeyEvent.VK_ENTER){
                    world.unpause();
                }
            }
            
            /** Handle key release events (stop walking). */
            public void keyReleased(java.awt.event.KeyEvent e)
            {
                if (isOver) return;
                
                int code = e.getKeyCode();
                
                if (code == java.awt.event.KeyEvent.VK_LEFT) {
                    
                    player.setImage(new BodyImage("images/smooooch_joker_by_gothicraft-d5r2li1.gif"));
                    player.stopWalking();
                    
                } else if (code == java.awt.event.KeyEvent.VK_RIGHT) {
               
                    player.setImage(new BodyImage("images/smooooch_joker_by_gothicraft-d5r2li2.gif"));
                    player.stopWalking();
                    
                }
            }
          });
        
        
        // quit the application when the game window is closed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //
        frame.add(viewA);
        // don't let the game window be resized
        frame.setResizable(true);
        // size the game window to fit the world view
        frame.pack();
        // make the window visible
        frame.setVisible(true);

        // start!
        world.start();
    }
     
    public void getNextLevel(){
        if (currentlevel == level1){
           currentlevel.clearLevel();
           System.out.println("level2");
           currentlevel = level2;
           player.resetCardCount();
           player.resetLives();
           currentlevel.populate();
           viewB = new GameViewB(this, 1500, 1500);
           viewB.paintBackground();
           frame.add(viewB);
           player = new Player(this);
           player.setPosition(new Vec2(0,-300));
           currentlevel.putPlayerAtStart();
        } 
        else if (currentlevel == level2) {
           currentlevel.clearLevel();
           System.out.println("level3");
           currentlevel = level3;
           player.resetCardCount();
           player.resetLives();
           viewC = new GameViewC(this, 1500, 1500);
           viewC.paintBackground();
           frame.add(viewC);
           currentlevel.populate();
           player = new Player(this);
           player.setPosition(new Vec2(0,110));
           currentlevel.putPlayerAtStart();
           
           enemy1 = new Enemy(this);
           enemy2 = new Enemy(this);
           enemy1.setPosition(new Vec2(100,0));
           enemy2.setPosition(new Vec2(-100,0));
           
        }  
        else 
        {
            System.out.println("Game Over");
            isOver = true;
        }
    }
    
    public void preStep(StepEvent e){
        
        // Get player postion
        //Vec2 playerPosition  = player.getPosition();
        // Set Camera
        //view.setCamera(playerPosition, 1.0f);
        
        // How can you make sure that the camera is steady? i.e follows
        // players x position only? At the moment is annoying going up and 
        // down with the player
    }
    
    public void postStep(StepEvent e){
        enemy1.setLinearVelocity(enemy1.enemyBehaviour1());
        enemy2.setLinearVelocity(enemy2.enemyBehaviour2());
    }
    /** Is the game over? */
    public boolean isOver()
    {
        return isOver;
    }
    
    /** End the game. */
    public void gameOver()
    {
      final JFrame frame = new JFrame("Game Over");
        world.pause();
        isOver = true;
    }
    
    /** The world in which this game is played. */
    public World getWorld()
    {
        return world;
    }
    
    public void restartGame() 
    {
        new Game();
        if ( isOver) {
              isOver = false;
        }
    }
    
    /** The world view. */
    public WorldView getView()
    {
        return view;
    }
    
    /** The lives*/
    public Life getLife()
    {
        return life;
    }
    
    /** The lives*/
    public Life getLife1()
    {
        return life1;
    }
    
    /** The lives*/
    public Life getLife2()
    {
        return life2;
    }
    
    /** The player. */
    public Player getPlayer()
    {
        return player;
    }
    
    /** Enemy number 1 */
     public Enemy getEnemy1()
    {
        return enemy1;
    }
    
    /** Enemy number 2 */
     public Enemy getEnemy2()
    {
        return enemy2;
    }
    
    /** Enemy number 2 */
     public Robin getRobin()
    {
        return robin;
    }
    
    /** Enemy number 2 */
     public Batman getBatman()
    {
        return batman;
    }
    
    /** the ground */
     public Body getGround(){
        return ground;
    }
    
    /** current level */
     public GameLevel getCurrentLevel(){
        return currentlevel;
    }
   
    /** Play a game. */
    public static void main(String[] args) {
        new Game();
    }
}





























