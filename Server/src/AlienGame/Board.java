package AlienGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {
	private Timer timer;
	private SpaceShip spaceship;
	private List<Alien> aliens;
	private boolean ingame;
	private final int ICRAFT_X = 40;
	private final int ICRAFT_Y = 60;
	private final int B_WIDTH = 600;
	private final int B_HEIGHT = 600;
	private final int DELAY = 15;

	private final int[][] pos = { { 2380, 29 }, { 2500, 59 }, { 1380, 89 }, { 780, 109 }, { 580, 139 }, { 680, 239 },
			{ 790, 259 }, { 760, 50 }, { 790, 150 }, { 980, 209 }, { 560, 45 }, { 510, 70 }, { 930, 159 }, { 590, 80 },
			{ 530, 60 }, { 940, 59 }, { 990, 30 }, { 920, 200 }, { 900, 259 }, { 660, 50 }, { 540, 90 }, { 810, 220 },
			{ 860, 20 }, { 740, 180 }, { 820, 128 }, { 490, 170 }, { 700, 30 } };

	public Board() {

		initBoard();

	}

	private void initBoard() {

		addKeyListener(new TAdapter());
		setFocusable(true);
		setBackground(Color.BLACK);
		setDoubleBuffered(true);
		ingame = true;

		setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));

		spaceship = new SpaceShip(ICRAFT_X, ICRAFT_Y);

		initAliens();

		timer = new Timer(DELAY, this);
		timer.start();
	}

	public void initAliens() {

		aliens = new ArrayList<>();

		for (int[] p : pos) {
			aliens.add(new Alien(p[0], p[1]));

		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (ingame) {

			drawObjects(g);

		} else {

			drawGameOver(g);
		}

		Toolkit.getDefaultToolkit().sync();
	}

	private void drawObjects(Graphics g) {

		if (spaceship.isVisible()) {
			g.drawImage(spaceship.getImage(), spaceship.getX(), spaceship.getY(), this);
		}

		List<Missile> ms = spaceship.getMissiles();

		for (Missile missile : ms) {
			if (missile.isVisible()) {
				g.drawImage(missile.getImage(), missile.getX(), missile.getY(), this);
			}
		}

		for (Alien alien : aliens) {
			if (alien.isVisible()) {

				List<Missile> msA = alien.getMissiles();

				g.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);

				for (Missile missile : msA) {
					if (missile.isVisible()) {
						g.drawImage(missile.getImage(), missile.getX(), missile.getY(), this);

					}

				}

			}
		}

		g.setColor(Color.WHITE);
		g.drawString("Aliens left: " + aliens.size(), 5, 15);
	}

	private void drawGameOver(Graphics g) {

		String msg = "Game Over";
		Font small = new Font("Helvetica", Font.BOLD, 14);
		FontMetrics fm = getFontMetrics(small);

		g.setColor(Color.white);
		g.setFont(small);
		g.drawString(msg, (B_WIDTH - fm.stringWidth(msg)) / 2, B_HEIGHT / 2);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		inGame();

		updateShip();
		updateMissiles();
		updateAliens();

		//checkCollisions();

		repaint();
	}

	private void inGame() {

		if (!ingame) {
			timer.stop();
		}
	}

	private void updateShip() {

		if (spaceship.isVisible()) {

			spaceship.move();
		}
	}

	private void updateMissiles() {

		List<Missile> ms = spaceship.getMissiles();

		for (int i = 0; i < ms.size(); i++) {

			Missile m = ms.get(i);

			if (m.isVisible()) {
				m.move(10);
			} else {
				ms.remove(i);
			}
		}
		for (int b = 0; b < aliens.size(); b++) {

			List<Missile> msA = aliens.get(b).getMissiles();
			for (int z = 0; z < msA.size(); z++) {
				Missile m1 = msA.get(z);
				if (m1.isVisible()) {
					m1.move(-3);

				} else {
					msA.remove(z);
				}

			}
		}
	}

	private void updateAliens() {

		if (aliens.isEmpty()) {

			ingame = false;
			return;
		}

		for (int i = 0; i < aliens.size(); i++) {

			Alien a = aliens.get(i);

			if (a.isVisible()) {
				a.move();
				if (Math.random() > 0.5) {
					a.fire();
				}
			} else {
				aliens.remove(i);
			}
		}
	}

	public void checkCollisions() {

		Rectangle r3 = spaceship.getBounds();

		for (Alien alien : aliens) {

			Rectangle r2 = alien.getBounds();
			List<Missile> msA = alien.getMissiles();
			for (Missile m1 : msA) {
				Rectangle r4 = m1.getBounds();
				if (r3.intersects(r4)) {
					spaceship.setVisible(false);
					ingame = false;
				}
			}
			if (r3.intersects(r2)) {

				spaceship.setVisible(false);
				alien.setVisible(false);
				ingame = false;

			}
		}

		List<Missile> ms = spaceship.getMissiles();

		for (Missile m : ms) {

			Rectangle r1 = m.getBounds();

			for (Alien alien : aliens) {

				Rectangle r2 = alien.getBounds();

				if (r1.intersects(r2)) {

					m.setVisible(false);
					alien.setVisible(false);
				}

			}
		}
	}

	public void sendCommand(String command) {
		spaceship.sendCommand(command);
	}

	private class TAdapter extends KeyAdapter {

		@Override
		public void keyReleased(KeyEvent e) {
			spaceship.keyReleased(e);
		}

		@Override
		public void keyPressed(KeyEvent e) {
			spaceship.keyPressed(e);
		}
	}

}
