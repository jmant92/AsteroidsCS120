import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;


public class AsteroidTests {

	@Test
	public void asteroidRotateTests() {
		Asteroid a = new Asteroid(0, 800, 0, 800);
		
		// do the boundaries read?
		assertEquals(0, a.getMinx());
		assertFalse(a.isOutOfBounds());
		
		// Angle test
		a.setAngle(10);
		a.move();
		assertTrue(a.getAngle() == (10*Math.PI/180)+(100/a.getSize()*Math.PI/180)); // This means the asteroid rotates.
	}
	
	@Test
	public void asteroidReboundTest() {
		Asteroid a = new Asteroid(0,800,0,800);
		
		// Does it bounce off of...
		// the left side?
		a.setLocation(5, 400);
		a.setVelocity(-15, 15);
		a.move();
		assertTrue(a.getX()==10.0);
		
		// the right side?
		a.setLocation(795, 400);
		a.setVelocity(15, 15);
		a.move();
		assertTrue(a.getX()==790.0);
		
		// the top?
		a.setLocation(400, 5);
		a.setVelocity(15, -15);
		a.move();
		assertTrue(a.getY()==10.0);
		
		// the bottom?
		a.setLocation(400, 795);
		a.setVelocity(15, 15);
		a.move();
		assertTrue(a.getY()==790.0);
	}
}
