import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;

/**
 * The PolygonSprite class inherits methods from Sprite class and sets the initial angle facing to the right
 * It also draws the polygons as well as rotates them 
 */

public abstract class PolygonSprite extends Sprite {
	protected static AffineTransform IDENTITY = new AffineTransform();
	protected Polygon myPolygon;
	protected Color myColor;
	protected double angle;
	
	/**
	 * Initialize the polygon
	 */
	
	public PolygonSprite(int left, int right, int top, int bottom) {
		super(left, right, top, bottom);
		angle = 0; // start each polygon facing to the right
	}

	/**
	 * Draw the polygon
	 */
	
	public void drawOn(Graphics g) {
		
		Graphics2D g2 = (Graphics2D)g;	// get stronger handle on graphics helper
		
		g2.setColor(myColor); // set the color to the color of the polygon
		
		g2.setTransform(IDENTITY);	// reset transforms
		g2.translate(x, y);	// translate origin to sprite location
		g2.rotate(angle);	// rotate axis by angle
		g2.fill(myPolygon);	// render the polygon
		g2.setTransform(IDENTITY);	// reset transforms
	}
	
	/**
	 * Rotate the polygon an angle, theta. Note: The angle entered will not be converted into radians
	 */
	public void rotate(double theta) {
		angle+=theta; // add to the current angle
	}
	
	
	/**
	 * Getters and setters for testing
	 */
	public double getAngle() {
		return angle;
	}
	
	public void setAngle(double angle) {
		this.angle=angle;
	}
}
