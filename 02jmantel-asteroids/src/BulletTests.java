import static org.junit.Assert.*;

import org.junit.Test;


public class BulletTests {

	@Test
	public void bulletAgeTest() {
		Bullet b = new Bullet(0, 800, 0, 800);
		
		// Move the bullet 10 times
		for(int i = 0; i<10; i++) {
			b.move();
		}
		// get the age which should be 10
		assertTrue(b.getAge() == 10);
		
		// testing to see if the bullet knows when it is too old
		b.setAge(0);
		assertFalse(b.isTooOld());
		
		b.setAge(-1);
		assertTrue(b.isTooOld());
	}

}
