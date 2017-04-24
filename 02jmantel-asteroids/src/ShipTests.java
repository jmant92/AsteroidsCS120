import static org.junit.Assert.*;

import org.junit.Test;


public class ShipTests {

	@Test
	public void shipRotateTest() {
		Ship s = new Ship(0,800,0,800);
		s.setAngle(0);
		
		// Does the ship rotate well?
		s.rotate(10);
		s.rotate(35);
		assertTrue(s.getAngle() == 45);
	}
	
	@Test
	public void shipMovementTest() {
		Ship s = new Ship(0,800,0,800);
		
		// Does x move work?
		s.setX(10);
		s.setVelocity(10, 30);
		s.move();
		assertTrue(s.getX() == 20);
		
		// What about y?
		s.setY(10);
		s.setVelocity(50, 10);
		s.move();
		assertTrue(s.getY() == 20);
		
		// How about x and y thrust?
		s.setX(0);
		s.setY(0);
		s.setVelocity(8, 8);
		s.setAngle(60*Math.PI/180);
		s.thrust();
		s.move();
		assertTrue(s.getDx() == 9);
		assertTrue(s.getDy() == 8.0+Math.sqrt(3));
		
		// Can it brake though?
		s.setVelocity(10, 10);
		s.brake();
		assertTrue(s.getDx() == 7);
		assertTrue(s.getDy()==7);
		
		s.setVelocity(2, 2);
		s.brake();
		assertTrue(s.getDx()==0);
		assertTrue(s.getDy()==0);
		
		// But how about wrapping?
		// For left bound
		s.setLocation(5, 400);
		s.setVelocity(-10, -10);
		s.move();
		assertTrue(s.getX() == 800);
		
		// For right bound
		s.setLocation(795, 400);
		s.setVelocity(10, -10);
		s.move();
		assertTrue(s.getX() == 0);
		
		// For top bound
		s.setLocation(400, 5);
		s.setVelocity(-10, -10);
		s.move();
		assertTrue(s.getY() == 800);
		
		// For bottom bound
		s.setLocation(400, 795);
		s.setVelocity(-10, 10);
		s.move();
		assertTrue(s.getY() == 0);
	}
	
	@Test
	public void shipShootTest() {
		Ship s = new Ship(0,800,0,800);
		
		// Can the bullet move faster than the ship?
		s.setVelocity(10, 0);
		assertTrue(s.shoot().getDx()==30);
	}

}
