import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.*;
import java.util.*;

/**
 * Very basic polygon editor.
 * Use the main method to launch the editor. See below for how it works.
 * <p>
 * THIS IS A QUICK DIRTY HACK.
 * IT IS NOT PROPERLY DOCUMENTED AND WILL NOT BE SUPPORTED. GOOD LUCK.
 * <p>
 * Each time a point is added, deleted or moved, a comma-separated list of coordinates
 * is printed to the console; this text can be copied and pasted as a parameter list
 * for the city.soi.platform.PolygonShape(float... coords) constructor BUT you
 * will have problems if the polygon is not convex (remember though that a Body
 * can have multiple shapes, so you can get pretty much any shaped body by using
 * overlapping convex polygons).
 * <p>
 * If the editor is initialised with a non-null file name parameter, the editor
 * will attempt to open the image file and display a scaled up version as
 * background (allowing you to trace a polygon approximation of the image outline).
 *
 * To add the next point to the polygon, left click where you want it. <br>
 * To delete an existing point, right click on it. <br>
 * To move a point, drag it.
 * <p>
 * It is not currently possible to insert a new point between two existing points; sorry.
 * <p>
 * If the editor is started from the command line, the output from a previous edit
 * can be fed back in for re-editing (after the image file name, if any). The image
 * file name can also be optionally followed by a scale specifier.
 *
 * <p>Example 1:
 *
 * <p>	<pre>java PolygonEditor large-bird.gif -scale 0.2</pre>
 *
 * <p>lets you draw around the large-bird.gif image, generating polygon vertex coordinates on the
 * assumption that the image will be scaled down to a fifth of its actual size when used
 * in the game (this can be useful if your game involves zooming the display, otherwise
 * the zoomed image will look blocky).
 *
 * <p>Example 2:
 *
 * <p>	<pre>java PolygonEditor small-bird.gif 3.5f,19.5f, 14.5f,4.5f, 14.5f,-3.5f, 7.5f,-17.5f, -7.5f,-17.5f, -14.5f,-1.5f, -14.5f,4.5f, -3.5f,19.5f</pre>
 *
 * <p>lets you edit the polygon drawn in a previous session around the image small-bird.gif (not scaled).
 */
public class PolygonEditor extends JPanel
{

    private static int WIDTH = 800;
    private static int HEIGHT = 800;
    private static int SCALE = 8;

    private List<Point> points;
    private Point currentPoint;
    private Point currentPointInPoints;
    private boolean pointInMotion;
    private ImageIcon icon;
	private float offX, offY;
	private float userScale;

    /**
     * Initialise a new editor with no background image.
     */
    private PolygonEditor()
    {
        this(null, 1.0f);
    }

	private static float toOneDP(float x) {
		return Math.round(10*x)/10.0f;
	}

    /**
     * Initialise a new editor with background image specified by given file name.
     * If file name is null, no image will be loaded.
     * @param f image file name
     */
    private PolygonEditor(String f, float userScale)
    {
        super();
		this.userScale = userScale;
        setPreferredSize(new java.awt.Dimension(WIDTH, HEIGHT));
		offX = WIDTH/2.0f;
		offY = HEIGHT/2.0f;
        if (f != null) icon = new ImageIcon(f);
        if (icon != null) {
            Image image = icon.getImage();
            int w = icon.getIconWidth();
			int h = icon.getIconHeight();
			offX = toOneDP(w * userScale/2.0f);
			offY = toOneDP(h * userScale/2.0f);
            image = image.getScaledInstance(Math.round(w * SCALE * userScale), -1, Image.SCALE_DEFAULT);
            icon.setImage(image);
        }
        points = new ArrayList<Point>();
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                pointInMotion = false;
                currentPoint = toGridPoint(e.getX(), e.getY());
                int i = points.indexOf(currentPoint);
                if (i < 0) {
                    currentPointInPoints = null;
                } else {
                    currentPointInPoints = points.get(i);
                }
            }

            public void mouseReleased(MouseEvent e) {
                if (pointInMotion) {
					pointInMotion = false;
					currentPointInPoints = null;
					updateView();
					return;
				}
                String mods = e.getMouseModifiersText(e.getModifiers());
                if (!mods.equals("Button1")) {
                    points.remove(currentPoint);
                } else {
                    points.add(currentPoint);
                }
                updateView();
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (currentPointInPoints == null) return;
                pointInMotion = true;
                Point gp = toGridPoint(e.getX(), e.getY());
                currentPointInPoints.x = gp.x;
                currentPointInPoints.y = gp.y;
                repaint();
            }
        });
    }

    /** The current list of polygon vertex coordinates as a comma-separated string. */
    public String toString()
    {
        String s = "";
        for (Point p : points) {
            if (s.length() > 0) s += ", ";
            s += +toOneDP(p.x - offX)+"f"+","+toOneDP(offY - p.y)+"f";
        }
        return s;
    }

    /**
     * The unscaled point corresponding to a screen position.
     * @param x    the x-cordinate of the screen point.
     * @param y    the y-cordinate of the screen point.
     * @return the unscaled point
     */
    private Point toGridPoint(int x, int y) {
        return new Point((int)(x/SCALE), (int)(y/SCALE));
    }

    /** Draw the image (if any) and polygon. */
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if (icon != null) icon.paintIcon(this, g, 0, 0);
        for(int i = 0; i < WIDTH/SCALE; i++) {
            g.setColor(Color.GRAY);
            g.drawLine(0, (i+1)*SCALE, WIDTH, (i+1)*SCALE);
        }
        for(int i = 0; i < HEIGHT/SCALE; i++) {
            g.setColor(Color.GRAY);
            g.drawLine((i+1)*SCALE, 0, (i+1)*SCALE, HEIGHT);
        }
        g.setColor(Color.BLUE);
        Point prev = null;
        for (Point p : points) {
            g.fillRect(p.x*SCALE, p.y*SCALE, SCALE, SCALE);
            if (prev != null) {
                g.drawLine(prev.x*SCALE + (SCALE/2), prev.y*SCALE + (SCALE/2), p.x*SCALE + (SCALE/2), p.y*SCALE + (SCALE/2));
            }
            prev = p;
        }
    }

    /**
     * Open an editor.
     * If first command-line argument is not a number, assume it is an image file name.
     * The optional image file name can be optionally followed by a scale specifier
     * of the form -scale <floating-point number>.
     * Any remaining arguments are assumed to be a comma-separated list of polygon vertex coordinates,
     * in the same format as the parameter list to city.soi.platform.PolygonShape(float...).
     */
    public static void main(String[] args) {
        String fileName = null;
        int firstCoordIndex = 0;
		float userScale = 1.0f;
        if (args.length > 0) {
            try {
				String[] test = args[0].split("[, ]+");
                Float.parseFloat(test[0]);
            } catch (NumberFormatException e) {
                fileName = args[0];
                firstCoordIndex = 1;
				if (args.length > 2 && args[1].toLowerCase().equals("-scale")) {
					userScale = Float.parseFloat(args[2]);
					firstCoordIndex = 3;
				}
            }
        }
        java.util.ArrayList<String> coordStrings = new java.util.ArrayList<String>();
        for (int i = firstCoordIndex; i < args.length; i++) {
            String[] coords = args[i].split("[, ]+");
            for (String x : coords) coordStrings.add(x);
        }
        PolygonEditor editor = new PolygonEditor(fileName, userScale);
        if (coordStrings.size() > 0) {
            int i = 0;
            boolean gotX = false;
            float x = 0;
            float y = 0;
            while (i < coordStrings.size()) {
                try {
                    float xy = Float.parseFloat(coordStrings.get(i));
                    if (gotX) {
                        y = xy;
                        Point p = new Point(Math.round(x + editor.offX), Math.round(editor.offY - y));
                        editor.points.add(p);
                        gotX = false;
                    } else {
                        x = xy;
                        gotX = true;
                    }
                } catch (NumberFormatException e) { }
                i++;
            }
        }
        JFrame frame = new JFrame(fileName == null ? "polygon editor" : fileName);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(editor);
        frame.pack();
        frame.setVisible(true);
    }

    /** Update the display and print the current coordinate list to console. */
    private void updateView()
    {
        System.out.println(this);
        repaint();
    }

}
