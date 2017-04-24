import java.awt.Color;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JApplet;
import javax.swing.JTextField;
import javax.swing.Timer;

/**
 * Game Applet is the main method. This holds the initialization for everything,
 * tells the user when the game is over, paints everything, and has two helper classes:
 * KeyHelper, which takes care of keyboard interactions, and TimerHelper, which takes care of
 * the timer.
 * @author JonathanMantel
 *
 */

public class GameApplet extends JApplet {
	public static int MAX_WIDTH = 800;
	public static int MAX_HEIGHT = 800;
	private ArrayList<Asteroid> asteroids;
	private ArrayList<Bullet> bullets;
	private Ship theShip;
	private int shipCount;
	private Timer timer;
	private int score;

	/**
	 * Initialize the window size, ship, ship count, location of the ship, ship velocity
	 * score, all of the asteroids on screen as well as their location, the list that will hold the bullets,
	 * the keylistener, and start the timer.
	 */
	public void init() {
		this.setSize(MAX_WIDTH, MAX_HEIGHT); // The window will be 800x800
		theShip = new Ship(0, MAX_WIDTH, 0, MAX_HEIGHT); // the ship and it's boundaries
		shipCount = 3; // number of ships
		theShip.setLocation(MAX_WIDTH/2, MAX_HEIGHT/2); // location of the ship
		theShip.setVelocity(0, 0); // the ship isn't moving at the beginning
		
		score = 0; // initial score

		asteroids = new ArrayList<Asteroid>();
		final int MAX = 20; // maximum number of asteroids
		for(int i=0; i<MAX; i++) { // go through every asteroids
			int xLoc =  (int) (Math.random()*698+51); // I don't want them to be half in the screen
			int yLoc =  (int) (Math.random()*698+51); // but I also don't want them to be ON the edge, hence the 698

			if(!(((xLoc>(MAX_WIDTH/2-50))&&(xLoc<(MAX_WIDTH/2+50))) // if the asteroid is not within 50 pixels of the center on the x axis
					||((yLoc>(MAX_HEIGHT/2-50))&&(yLoc<(MAX_HEIGHT/2+50))))) { // or not within 50 pixels of the center on the y axis
				asteroids.add(new Asteroid(0, MAX_WIDTH, 0, MAX_HEIGHT)); // add a new asteroid with these boundaries
				asteroids.get(i).setLocation(xLoc, yLoc); // set its location to whatever it was
			} else { // otherwise
				i--; // set i back 1 so we always start with 20 asteroids
			}
		}

		bullets = new ArrayList<Bullet>();
		
		KeyListener keyL = new KeyHelper();
		this.setFocusable(true);
		this.addKeyListener(keyL);
		
		ActionListener actL = new TimerHelper();
		timer = new Timer(1000/15, actL); // 15 ticks per second
		timer.start(); // start the timer
	}
	
	
	/**
	 * Check to see if it's game over
	 */
	
	public boolean isGameOver() {
		if((shipCount <= 0)||(asteroids.isEmpty())) { // no more ships or no more asteroids
			return true; // game over
		} else { // otherwise
			return false; // game on
		}
	}
	
	/**
	 * This paints everything on screen
	 */
	public void paint(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, MAX_WIDTH, MAX_HEIGHT); // paint white over the entire screen to cover up trails the images leave
		
		if(isGameOver()) { // is it game over?
			g.setColor(Color.black); // set the string color to black
			g.drawString("Game Over", (MAX_WIDTH/2)-10, MAX_HEIGHT/2); // Tell the user game over in the middle of the screen
			g.drawString("Score: "+ score, MAX_WIDTH/2, (MAX_HEIGHT/2)+20); // and tell the user their score.
			timer.stop();
		} else {
			theShip.drawOn(g); // draw the ship
			g.drawString("Ships left: "+shipCount, MAX_WIDTH-100, MAX_HEIGHT-20); // give the ship count
			
			g.drawString("Score: "+score, (MAX_WIDTH/2)-20, MAX_HEIGHT-20); // give the score
			for(Asteroid a: asteroids) { // draw all of the asteroids in the arraylist asteroids
				a.drawOn(g); 
			}

			for(Bullet b: bullets) { // draw all of the bullets in the arraylist bullets
				b.drawOn(g);
			}
		}
	}
	
	/**
	 *	This is the Key helper class, it takes care of all the keyboard interactions
	 */
	private class KeyHelper implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			
			if(e.getKeyCode()==KeyEvent.VK_UP) {
				// when up is pressed, accelerate the ship
				theShip.thrust();
			}
			
			if(e.getKeyCode()==KeyEvent.VK_DOWN) {
				// when down is pressed, slow the ship down
				theShip.brake();
			}
			
			if(e.getKeyCode()==KeyEvent.VK_LEFT) {
				// while left is pressed rotate the ship 10 degrees counter clockwise
				theShip.rotate(-10*Math.PI/180);
			}
			
			if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
				// while right is pressed rotate the ship 10 degrees clockwise
				theShip.rotate(10*Math.PI/180);
			}
			
			if(e.getKeyCode()==KeyEvent.VK_B) {
				// while b is pressed shoot and add a bullet to the list
				bullets.add(theShip.shoot());
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	
	/**
	 * Timer helper tells the application what to do every time the timer ticks.
	 */
	private class TimerHelper implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			List<Sprite> remB = new ArrayList<Sprite>(); // declare empty list for removing
			
			for(Bullet b: bullets) { // iterate over each bullet
				if(b.isTooOld()) remB.add(b); // add old bullets to remove list
				if(b.isOutOfBounds()) remB.add(b); // add out of bounds bullets to the remove list
			}
			bullets.removeAll(remB); // now remove all at once from bullet
			
			theShip.move(); // move the ship
			
			for(Bullet b: bullets) {
				b.move(); // move the bullets
			}
			
			List<Sprite> remA = new ArrayList<Sprite>();
			
			for(Asteroid a: asteroids) {
				a.move(); // move the asteroids
				
				if(theShip.isCollided(a)) { // if the ship crashes into an asteroid
					shipCount--; // subtract one life
					remA.add(a); // add that asteroid to the removal list
				}
			}
			asteroids.removeAll(remA); // remove all at once from asteroids
			
			for(Bullet b: bullets) { // go through every bullet
				for(Asteroid a: asteroids) { // and every asteroid
					if(b.isCollided(a)) { // if a bullet has collided with the asteroid
						remA.add(a); // add that asteroid to the removal list
						remB.add(b); // add that bullet to the removal list
						score+=500/a.getSize(); // add a number to the score depending on the size
					}
				}
			}
			asteroids.removeAll(remA); // remove all the listed bullets at once
			bullets.removeAll(remB); // remove all the listed asteroids at once
			
			repaint(); // repaint the screen
		}
	}
}
