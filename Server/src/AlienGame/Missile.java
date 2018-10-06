package AlienGame;

public class Missile extends Sprite {
	private final int BOARD_WIDTH = 500;
	private final int MISSILE_SPEED = 1;

	public Missile(int x, int y) {
		super(x, y);

		initMissile();
	}

	private void initMissile() {

		loadImage("src/resources/boomboom.png");
		getImageDimensions();
	}

	public void move(int missile_speed) {

		x += missile_speed;

		if (x > BOARD_WIDTH)
			visible = false;
	}
}
