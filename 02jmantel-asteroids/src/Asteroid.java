import java.awt.Color;
import java.awt.Polygon;

/**
 * The Asteroid class creates the asteroids, moves them, and rotates them.
 */

public class Asteroid extends PolygonSprite {
	protected double dAngle;
	
	/**
	 * This is the constructor
	 */
	public Asteroid(int left, int right, int top, int bottom) {
		super(left, right, top, bottom);

		this.setSize((int) (Math.random()*40+10)); // set the asteroid size
		dAngle = 100/size*Math.PI/180; // how fast the asteroid spins depends on its size
		
		int n = (int) (Math.random()*5+5); // Choose a random number of points b/t 5 and 10
		double initAngle=(3*Math.PI/2); // Start pointing up, because again, apparently 3PI/2 is up
		this.myPolygon = new Polygon();
		
		for(int i=0; i<n; i++) { // Make the points
			int xPt=(int) (this.size*Math.cos(initAngle)); // xPoint
			int yPt =(int) (int) (this.size*Math.sin(initAngle)); // yPoint
			myPolygon.addPoint(xPt, yPt);
			initAngle+=(2*Math.PI/n); // Add this section to the initial angle
		}
		
		this.myColor = Color.gray;
	}
	
	/**
	 * For movement and spinning
	 */
	@Override
	public void move() {
		super.move(); // call superclass move algorithm
		this.angle+=dAngle; // add the change in angle as well
		
		if(this.isOutOfBounds()) { // did the asteroid leave the field
			this.rebound(); // bounce back
		}
	}
	
	/**
	 * Meant for the test cases to check if the asteroid does move;
	 */
	public void setAngle(double angle) {
		this.angle = angle*Math.PI/180;
	}
	
	/**
	 * Also meant for test cases
	 */
	public double getAngle() {
		return this.angle;
	}
}
