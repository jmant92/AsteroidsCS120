import java.awt.Color;
import java.awt.Polygon;

/**
 * This is the ship class, it will tell the superclass the parameters for making the ship,
 * shoot bullets, accelerate the ship, brake, and take care of the ships actions outside
 * the play area.
 */

public class Ship extends PolygonSprite {

	/**
	 * Ship Constructor
	 */
	public Ship(int left, int right, int top, int bottom) {
		super(left, right, top, bottom);

		this.angle = 0; // the angle that will be changed is 0 for starters
		double initAngle = 0; // face right
		this.size = 10; // the ships size
		this.myPolygon = new Polygon();
		int n = 3; // number of points on the polygon

		for(int i=0; i<n; i++) {
			int xPt=(int) (this.size*Math.cos(initAngle)); // xPoint
			int yPt =(int) (int) (this.size*Math.sin(initAngle)); // yPoint
			this.myPolygon.addPoint(xPt, yPt); // 
			initAngle+=2*Math.PI/n;
		}

		this.myColor = Color.red;
	}

	/**
	 * Shoot the bullets
	 */
	public Bullet shoot() {
		Bullet b = new Bullet(0, 800, 0, 800); // create a new bullet
		b.setVelocity(this.getDx()+20*Math.cos(this.angle), this.getDy()+20*Math.sin(this.angle));
		// Set the velocity of the new bullet with respect to the angle the ship is facing
		b.setLocation((int)Math.round(this.getX()), (int)Math.round(this.getY()));
		// Set the location of the new bullet to where the ship is
		return b;
	}

	/**
	 * Speed the ship up
	 */

	public void thrust() {
		// add 2 to the velocity
		this.dx+=2*Math.cos(this.angle); // take into account the x
		this.dy+=2*Math.sin(this.angle); // and y direction
	}

	/**
	 * This is so we can stop the ship.
	 */

	public void brake() {
		if(this.getDx()>3) { // if the horizontal velocity is greater than three
			this.dx-=3; // subtract 3 from the velocity
		} else { // otherwise
			this.dx=0; // set it to 0, we don't want our ship to move backwards
		}

		// Same thing as above but with vertical movement
		if(this.getDy()>3) {
			this.dy-=3;
		} else {
			this.dy = 0;
		}
	}

	
	/**
	 * Move the ship
	 */
	@Override
	public void move() {
		super.move();
		
		if(this.isOutOfBounds()) { // if the ship leaves the play area
			if(this.x<this.minx) { // on the left
				this.setX(maxx); // set its new location to the right boundary
			} else if(this.x>this.maxx) { // on the right
				this.setX(minx); // set its new location to the left boundary
			}

			if(this.y<this.miny) { // on the top
				this.setY(maxy); // new location at the bottom boundary
			} else if(this.y>this.maxy) { // on the bottom
				this.setY(miny); // new location at the top boundary
			}
		}
	}

}
