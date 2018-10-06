package AlienGame;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class SpaceShip extends Sprite {

	private int dx;
	private int dy;
	private List<Missile> missiles;

	public SpaceShip(int x, int y) {
		super(x, y);

		initCraft();

	}

	private void initCraft() {

		missiles = new ArrayList<>();
		loadImage("src/resources/pewpew.png");
		getImageDimensions();

	}

	public void move() {

		x += dx * 8;
		y += dy * 8;

		if (x < 1) {
			x = 1;
		}

		if (y < 1) {
			y = 1;
		}
	}

	public List<Missile> getMissiles() {
		return missiles;
	}

	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_SPACE) {
			fire();

		}

		if (key == KeyEvent.VK_LEFT) {
			dx = -1;
		}

		if (key == KeyEvent.VK_RIGHT) {
			dx = 1;
		}

		if (key == KeyEvent.VK_UP) {
			dy = -1;
		}

		if (key == KeyEvent.VK_DOWN) {
			dy = 1;
		}
	}

	public void fire() {
		missiles.add(new Missile(x + width, y + height / 2));
	}

	public void keyReleased(KeyEvent e) {

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT) {
			dx = 0;
		}

		if (key == KeyEvent.VK_RIGHT) {
			dx = 0;
		}

		if (key == KeyEvent.VK_UP) {
			dy = 0;
		}

		if (key == KeyEvent.VK_DOWN) {
			dy = 0;
		}
	}

	public void sendCommand(String command) {

		if (command.equals("space")) {

			fire();
		}

		if (command.equals("left")) {
			dx = -1;
		}

		if (command.equals("right")) {
			dx = 1;
		}

		if (command.equals("up")) {
			dy = -1;
		}

		if (command.equals("down")) {
			dy = 1;
		}
	}
}
