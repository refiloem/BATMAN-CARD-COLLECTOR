import city.soi.platform.*;
import java.util.List;

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
public  class GameLevel2 extends GameLevel
{
    // instance variables - replace the example below with your own
    
    private Life life;
    private Life life1;
    private Life life2;
    private Body ground;
    
    private World world;
    private LevelUp levelup;
    private Player player;
    private boolean completed;
    //private Lifecoin coin;

    public GameLevel2(Game game)
    {
        super(game);
        this.game = game;
        this.ground = ground;
        player = game.getPlayer();
       
        // JOptionPane.showMessageDialog(null, "WELCOME TO LEVEL ONE!");
        completed = false;
    }

    @Override
    protected void populate()
    {   
 
        // make the ground
        Body ground = new Body(world, PolygonShape.makeBox(250, 39), Body.Type.STATIC);
        ground.setPosition(new Vec2(0, -308));
        ground.setImage(new BodyImage("images/platform-1.png"));

        // make some platforms, includes moving platforms and stationary platforms
        Body staticPlatform = new Body(world, PolygonShape.makeBox(50, 10), Body.Type.STATIC);
        staticPlatform.setPosition(new Vec2(-400, 150));
        staticPlatform.setImage(new BodyImage("images/platform_10.png"));
        
        Body staticPlatform1 = new Body(world, PolygonShape.makeBox(50, 20), Body.Type.STATIC);
        staticPlatform1.setPosition(new Vec2(400, 0));
        staticPlatform1.setImage(new BodyImage("images/platform_10.png"));
         
        // Body staticPlatform2 = new Body(world, PolygonShape.makeBox(50, 20), Body.Type.STATIC);
        // staticPlatform2.setPosition(new Vec2(200, 170));
        // staticPlatform.setImage(new BodyImage("images/platform_10.png"));
        
        Body staticPlatform3 = new Body(world, PolygonShape.makeBox(50, 20), Body.Type.STATIC);
        staticPlatform3.setPosition(new Vec2(-200, -50));
        staticPlatform3.setImage(new BodyImage("images/platform_12.png"));
        
        Body staticPlatform4 = new Body(world, PolygonShape.makeBox(50, 20), Body.Type.STATIC);
        staticPlatform4.setPosition(new Vec2(350, 150));
        staticPlatform4.setImage(new BodyImage("images/platform_12.png"));
        
        Body movingPlatform1 = new SlidingPlatform(world, PolygonShape.makeBox(50, 20), new Vec2(0, 80), 4.0f);
        movingPlatform1.setPosition(new Vec2(110, -140));
        movingPlatform1.setImage(new BodyImage("images/platform_10.png"));
        
        Body movingPlatform2 = new SlidingPlatform(world, PolygonShape.makeBox(50, 20), new Vec2(10, 90), 6.0f);
        movingPlatform2.setPosition(new Vec2(0, 170));
        movingPlatform2.setImage(new BodyImage("images/platform_10.png"));
        
        Body movingPlatform3 = new SlidingPlatform(world, PolygonShape.makeBox(50, 20), new Vec2(350, -140), 10.0f);
        movingPlatform3.setPosition(new Vec2(-180, 140));
        movingPlatform3.setImage(new BodyImage("images/platform_12.png"));
        
        Body staticPlatform5 = new Body(world, PolygonShape.makeBox(50, 20), Body.Type.STATIC);
        staticPlatform5.setPosition(new Vec2(-100, -200));
        staticPlatform5.setImage(new BodyImage("images/platform_11.png"));
        
        //making cards
        Card card1 = new Card(game);
        card1.putOn(staticPlatform3);

        Card card2 = new Card(game);
        card2.putOn(movingPlatform2);

        Card card3 = new Card(game);
        card3.putOn(staticPlatform5);
        
        
        Card card4 = new Card(game);
        card4.putOn(staticPlatform4);
        
        //levelup
        levelup = new LevelUp(game);
        levelup.putOn(staticPlatform);
        
      

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
        if(player.getCard() == 4)
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
     