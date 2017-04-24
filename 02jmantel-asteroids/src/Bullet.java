import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

/**
 * Let's give our ship a way to defend itself
 */
public class Bullet extends Sprite {
	protected static AffineTransform IDENTITY = new AffineTransform();
	private int age;
	
	/**
	 * The bullet constructor 
	 */
	public Bullet(int left, int right, int top, int bottom) {
		super(left, right, top, bottom);
		
		age = 20; // all bullets will start this old
	}
	
	/**
	 * Move the bullet and age it.
	 */
	@Override
	public void move() {
		super.move();
		age--; // whenever this algorithm is called we lower the age by 1.
	}

	/**
	 * Draw the bullet
	 */
	
	@Override
	public void drawOn(Graphics gfx) {
		Graphics2D g2 = (Graphics2D)gfx;// get stronger handle on graphics helper
		g2.setTransform(IDENTITY); // resets transformation
		g2.translate(x, y);	// translate origin to sprite location
		gfx.setColor(Color.blue); // set the color
		gfx.fillRect(0, 0, 3, 3); // fill the rectangle
		g2.setTransform(IDENTITY); // resets transformation
	}
	
	/**
	 * Is the bullet to old yet?
	 */
	public boolean isTooOld() {
		if(this.age<0) { // if the age is less than 0
			return true; // the bullet is too old
		} else { // otherwise
			return false; // it isn't
		}
	}

	/**
	 * Getter used in tests
	 */
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
}