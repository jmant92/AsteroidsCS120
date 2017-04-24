import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;


public class AppletTests {

	@Test
	public void asteroidCreation() {
		final int MAX_WIDTH = 800;
		final int MAX_HEIGHT = 800;
		final int MAX = 20;
		
		ArrayList<Asteroid> asteroids = new ArrayList<Asteroid>();
		// Initial locations are meant to be bad starting locations so I know if something goes wrong
		int xLoc = 400;
		int yLoc = 400;
		
		// I want to test to see if the asteroids appear in the 50 pixel range
		for(int i=0; i<MAX; i++) { // go through every asteroids
			xLoc =  (int) (Math.random()*698+51); // I don't want them to be half in the screen
			yLoc =  (int) (Math.random()*698+51); // but I also don't want them to be ON the edge, hence the 698

			if(!(((xLoc>(MAX_WIDTH/2-50))&&(xLoc<(MAX_WIDTH/2+50))) // if the asteroid is not within 50 pixels of the center on the x axis
					||((yLoc>(MAX_HEIGHT/2-50))&&(yLoc<(MAX_HEIGHT/2+50))))) { // or not within 50 pixels of the center on the y axis
				asteroids.add(new Asteroid(0, MAX_WIDTH, 0, MAX_HEIGHT)); // add a new asteroid with these boundaries
				asteroids.get(i).setLocation(xLoc, yLoc); // set its location to whatever it was
			} else { // otherwise
				i--; // set i back 1 so we always start with 20 asteroids
			}
		}
		
		// So I test for every asteroid
		for(Asteroid a: asteroids) {
			if((((xLoc>(MAX_WIDTH/2-50))&&(xLoc<(MAX_WIDTH/2+50)))
					||((yLoc>(MAX_HEIGHT/2-50))&&(yLoc<(MAX_HEIGHT/2+50))))) {
				// if it's in the range, print out the location it spawns and fail the test
				System.out.println("Asteroids Initial X: "+a.getX());
				System.out.println("Asteroids Initial Y: "+a.getY());
				fail("The asteroid is in a bad place.");
			}
		}
	}
	
	@Test
	public void bulletDestructionTest() {
		ArrayList<Asteroid> asteroids = new ArrayList<Asteroid>();
		List<Sprite> remA = new ArrayList<Sprite>();
		Ship theShip = new Ship(0,800,0,800);
		int shipCount = 3;
		
		// Add 2 asteroids
		Asteroid a1 =  new Asteroid(0,800,0,800);
		Asteroid a2 =  new Asteroid(0,800,0,800);
		
		asteroids.add(a1);
		asteroids.add(a2);
		
		// set the location of the ship and asteroids
		theShip.setLocation(400, 400);
		a1.setLocation(405, 410);
		a2.setLocation(395, 390);
		
		// go over all of the collision detection for the asteroids
		for(Asteroid a: asteroids) {
			a.move(); // move the asteroids
			
			if(theShip.isCollided(a)) { // if the ship crashes into an asteroid
				shipCount--; // subtract one life
				remA.add(a); // add that asteroid to the removal list
			}
		}
		asteroids.removeAll(remA); // remove all at once from asteroids
		
		assertTrue(shipCount==1);
		assertTrue(asteroids.size()==0);
	}

}
