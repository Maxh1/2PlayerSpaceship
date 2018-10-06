package AlienGame;

import java.util.ArrayList;
import java.util.List;

public class Alien extends Sprite {
	private final int INITIAL_X = 400;
	private List<Missile> missiles;

	public Alien(int x, int y) {
		super(x, y);

		initAlien();
	}

	private void initAlien() {
		missiles = new ArrayList<>();
		loadImage("src/resources/alien.png");
		getImageDimensions();
	}

	public void move() {
		
		 
		 
		if (x < 0) {
			x = INITIAL_X;
		}

		x -= 1;

		if (Math.random() > 0.97) {
			if (Math.random() < 0.5) {
				y -= 20;
			} else {
				y += 20;
			}

		}
	}
	
	 public List<Missile> getMissiles() { return missiles; }
	 
	 public void fire() { if(Math.random()<0.11) { missiles.add(new Missile(x +
	 width, y + height / 2));} }
	 
	 
}
