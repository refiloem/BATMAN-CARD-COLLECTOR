 
import city.soi.platform.*;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.FontMetrics;

public class GameViewA extends WorldView
{
    private Game game;
    
    public GameViewA(Game game, int width, int height)
    {
        super(game.getWorld(), width, height);
        this.game = game;
    }
    
    // Version 1
    public void paintBackground(){
         //make the background
        Body backGround=new Body(getWorld(), PolygonShape.makeBox(1500, 600), Body.Type.STATIC);
        backGround.setImage(new BodyImage("images/background_08.jpg"));
        backGround.setRenderLayer(-5);
        backGround.setGhostly(true);
    }
    
    private void drawGameOver(Graphics g) {
        g.setColor(Color.BLUE);
        g.setFont(new Font("Times", Font.BOLD, 64));
        FontMetrics gameOverMetric = g.getFontMetrics();
        String message = "Game Over!";
        int messageWidth = g.getFontMetrics().stringWidth(message);
        g.drawString(message, (getWidth() - messageWidth) / 2, getHeight() / 2);
    }

}
