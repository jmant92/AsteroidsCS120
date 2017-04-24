import java.awt.Graphics;

/**
 * The all important sprite class. Every sprite inherits from here. It takes care of the boundaries,
 * position, velocity, and size. It also helps detect collisions, rebounds, and size.
 */

public abstract class Sprite {
	protected double x, y;
	protected double dx, dy;
	protected int minx, maxx, miny, maxy;
	protected int size;

	/**
	 * Initialize the sprite
	 */
	public Sprite(int left, int right, int top, int bottom) {
		// setting the boundaries
		minx=left;
		maxx=right;
		miny=top;
		maxy=bottom;

		// setting the middle of the sprite
		x = (minx+maxx)/2;
		y = (miny+maxy)/2;
		
		// This bit of code aids with the starting direction of the asteroids
		int negX = (int)(Math.random()*2);
		int negY = (int)(Math.random()*2);
		if(negX<1) {
			negX=-1;
		} else {
			negX=1;
		}
		
		if(negY<1) {
			negY=-1;
		} else {
			negY=1;
		}
		
		// initial velocity
		dx = (Math.random()*4+1)*negX;
		dy = (Math.random()*4+1)*negY;
	}

	/**
	 * Using the Pythagorean theorem, we check for collision detection
	 */
	public boolean isCollided(Sprite other) {
		int d = (int) Math.sqrt((this.x-other.getX())*(this.x-other.getX())+(this.y-other.getY())*(this.y-other.getY()));
		return (d<(this.size+other.getSize()));
		// i.e. if one side of a sprite overlaps the side of another sprite, then a collision is detected
	}

	/**
	 * Check if the sprite is outside the boundaries
	 */
	public boolean isOutOfBounds() {
		if((x>maxx)||(x<minx)||(y>maxy)||(y<miny)) { // is it outside the boundaries set?
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Bounce back if the sprite left its bounds
	 */
	public void rebound() {
		if(isOutOfBounds()) { // left the boundaries?
			if(x>maxx) { // was it the right side?
				double extra;
				extra = x-maxx; // take the sprites current x location and subtract the boundary from it
				x =maxx+(-1*extra); // set the new x to be the location of the right boundary less the extra bit
				dx*=-1; // also change the velocity to be going the opposite direction
			} else if(x<minx) { // same thing for the left side
				double extra;
				extra = x-minx;
				x =minx+(-1*extra);
				dx*=-1;
			}
			
			// The same idea as above
			if(y>maxy) {
				double extra;
				extra = y-maxy;
				y =maxy+(-1*extra);
				dy*=-1;
			} else if (y<miny) {
				double extra;
				extra = y-miny;
				y =miny+(-1*extra);
				dy*=-1;
			}
		}
	}

	/**
	 * Sets the initial location at some x and y
	 */
	public void setLocation(int nx, int ny) {
		this.x=nx;
		this.y=ny;
	}

	/**
	 * Sets the initial velocity vectors
	 */
	public void setVelocity(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}

	public void setSize(int ns) {
		size = ns;
	}

	public abstract void drawOn(Graphics gfx);

	/**
	 * Moves the sprite
	 */
	public void move() {
		x+=dx;
		y+=dy;
	}

	public double getX() {
		return x;
	}
	
	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}
	
	public void setY(double y) {
		this.y = y;
	}

	public int getMinx() {
		return minx;
	}

	public void setMinx(int minx) {
		this.minx = minx;
	}

	public int getMaxx() {
		return maxx;
	}

	public void setMaxx(int maxx) {
		this.maxx = maxx;
	}

	public int getMiny() {
		return miny;
	}

	public void setMiny(int miny) {
		this.miny = miny;
	}

	public int getMaxy() {
		return maxy;
	}

	public void setMaxy(int maxy) {
		this.maxy = maxy;
	}

	public double getDx() {
		return dx;
	}

	public double getDy() {
		return dy;
	}

	public int getSize() {
		return size;
	}	
}
