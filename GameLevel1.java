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
 * Write a description of class GameLevel here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public  class GameLevel1 extends GameLevel
{
    // instance variables - replace the example below with your own
    public Body movinginvisible;
    private Life life;
    private Life life1;
    private Life life2;
    private Body ground;
    private Enemy enemy1;
    private Enemy enemy2;

    private World world;
    private Player player;
    private boolean completed;
    //private Lifecoin coin;

    public GameLevel1(Game game)
    {
        super(game);
        this.game = game;
        //enemy1 = game.getEnemy1();
        //enemy2 = game.getEnemy2();
        player = game.getPlayer();
        world = game.getWorld();
        // JOptionPane.showMessageDialog(null, "WELCOME TO LEVEL ONE!");
        completed = false;
    }

    public World getWorld()
    {
        return world;
    }

    @Override
    protected  void populate()
    {   
       
        // make the ground
        Body ground = new Body(world, PolygonShape.makeBox(250, 39), Body.Type.STATIC);
        ground.setPosition(new Vec2(0, -308));
        ground.setImage(new BodyImage("images/platform-1.png"));

        // make some platforms, includes moving platforms and stationary ones
        Body movingPlatform1 = new SlidingPlatform(world, PolygonShape.makeBox(50, 20), new Vec2(0, -300), 7.0f);
        movingPlatform1.setPosition(new Vec2(200, 100));
        movingPlatform1.setImage(new BodyImage("images/platform-2.png"));

        Body staticPlatform2 = new Body(world, PolygonShape.makeBox(18, 12), Body.Type.STATIC);
        staticPlatform2.setPosition(new Vec2(0, 100));
        staticPlatform2.setImage(new BodyImage("images/platform-3.png"));

        Body movingPlatform3 = new SlidingPlatform(world, PolygonShape.makeBox(50, 20), new Vec2(0, 165), 7.0f);
        movingPlatform3.setPosition(new Vec2(-200, -100));
        movingPlatform3.setImage(new BodyImage("images/platform-2.png"));

        Body staticPlatform4 = new Body(world, PolygonShape.makeBox(50, 20), Body.Type.STATIC);
        staticPlatform4.setPosition(new Vec2(0, -100));
        staticPlatform4.setImage(new BodyImage("images/platform-2.png"));

        Body staticPlatform5 = new Body(world, PolygonShape.makeBox(50, 20), Body.Type.STATIC);
        staticPlatform5.setPosition(new Vec2(-200, 180));
        staticPlatform5.setImage(new BodyImage("images/platform-2.png"));
        
        Body staticPlatform6 = new Body(world, PolygonShape.makeBox(50, 20), Body.Type.STATIC);
        staticPlatform6.setPosition(new Vec2(200, 180));
        staticPlatform6.setImage(new BodyImage("images/platform-2.png"));
        
        
        Body staticPlatform7 = new Body(world, PolygonShape.makeBox(50, 20), Body.Type.STATIC);
        staticPlatform7.setPosition(new Vec2(370, 180));
        staticPlatform7.setImage(new BodyImage("images/platform-2.png"));

         
        //new instance of level up door
        LevelUp levelup = new LevelUp(game);
        levelup.putOn(staticPlatform7);
        
        //making cards
        Card card1 = new Card(game);
        card1.putOn(staticPlatform6);

        Card card2 = new Card(game);
        card2.putOn(movingPlatform1);

        Card card3 = new Card(game);
        card3.putOn(staticPlatform5);

        // make lifecoin
        Lifecoin coin = new Lifecoin(game);
        coin.putOn(movingPlatform3);

        // display lives 
        life = new Life(game);
        life.setPosition(new Vec2(-600, 325));
        life1 = new Life(game);
        life1.setPosition(new Vec2(-550,325));
        life2= new Life(game);
        life2.setPosition(new Vec2(-500,325));  

    }

    @Override
    public boolean isCompleted()
    {
        if(player.getCard() == 3)
        {
            completed = true;
        }
        return completed;
    }

    @Override
    public void putPlayerAtStart()
    {
        player = game.getPlayer();
        player.setPosition(new Vec2(-90, 0));;
        player.move(new Vec2(-90, 0));
    }

}