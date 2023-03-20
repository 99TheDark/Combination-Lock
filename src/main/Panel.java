package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Panel extends JPanel implements Runnable {

	// Most variables are public in case of later changes with more classes :)

	private static final long serialVersionUID = 1L;
	public JFrame window;
	Thread thread;
	KeyHandler keyH;

	public String guess;
	String lastKeyDown;
	public ArrayList<String> lines;

	public double totalTime;

	public boolean paused;

	public CombinationLock lock;

	public Point mouse = new Point(0, 0);

	double dt;

	public int windowWidth, windowHeight;
	public double widthRatio, heightRatio;
	public double scaleQuantity;

	public Font font = new Font("SansSerif", Font.PLAIN, 32);

	// Desired FPS
	int FPS = 60;

	public Panel(JFrame window) {

		// Setup window options
		this.window = window;
		this.keyH = new KeyHandler();
		this.setPreferredSize(new Dimension(800, 500)); // 8 x 5 ratio (normal comp size, 1440 x 900)
		this.setDoubleBuffered(true);
		this.setFocusable(true);
		this.addKeyListener(keyH);
		this.setBackground(Color.black);

		// The word you want
		lock = new CombinationLock("amazing");

		// Resets the guess and lines
		guess = "";
		lines = new ArrayList<String>();
		lines.add("");

		// For sine wave, adds all delta times
		totalTime = 0;

		// It's not paused at the start
		paused = false;

	}

	public void startThread() {

		// Starts the window thread or basically makes it start running
		thread = new Thread(this);
		thread.start();

	}

	@Override
	public void run() {

		// Delta time so things happen with frame change
		int billion = 1000000000;
		long lastNanoTime = System.nanoTime();

		while (thread != null) {

			// Don't edit your guesses when paused
			if (!paused) {

				frame();

			}

			if ((System.nanoTime() - lastNanoTime) >= billion / FPS) {

				dt = (double) (System.nanoTime() - lastNanoTime) / 1000000000;

				totalTime += dt;

				update();
				repaint();

				lastNanoTime = System.nanoTime();

			}

		}

	}

	// Updates with FPS
	public void update() {

		// Mouse in case I add one
		Point mousePoint = window.getContentPane().getMousePosition();

		if (mousePoint != null) {

			mousePoint.x = (int) (mousePoint.x / scaleQuantity);
			mousePoint.y = (int) (mousePoint.y / scaleQuantity);

			if (mousePoint.x < 800 && mousePoint.y < 500) {

				mouse = mousePoint;

			}

		}

	}

	// Updates every frame so not input delay
	public void frame() {

		// Set size of window
		windowWidth = window.getContentPane().getWidth();
		windowHeight = window.getContentPane().getHeight();

		// Everything calculated should be in the 800x500 rect, then will be scaled up
		widthRatio = (double) windowWidth / 800;
		heightRatio = (double) windowHeight / 500;

		// Scales the whole screen smoothly
		scaleQuantity = Math.min(widthRatio, heightRatio);

		String key = keyH.keyDown();

		// Typing
		if (key != null && !key.equals(lastKeyDown)) {

			lastKeyDown = key;

			if (key == "delete") {

				if (guess.length() > 0) {

					guess = guess.substring(0, guess.length() - 1);

				}

			} else if (key == "enter") {

				String clue = lock.getClue(guess).toUpperCase();

				lines.set(lines.size() - 1, clue);
				lines.add("");

				if (guess.equals(clue)) {

					lines.add("You got it correct! Good job!");
					lines.add("");

					paused = true;

				}

				guess = "";

			} else if (guess.length() <= lock.word.length()) { // If word is too long

				guess += key;

			}

		}

		if (key == null) {

			lastKeyDown = "";

		}

		lines.set(lines.size() - 1, guess);

	}

	public void paintComponent(Graphics graphics) {

		Graphics2D g = (Graphics2D) graphics;

		g.scale(scaleQuantity, scaleQuantity);

		g.setFont(font);
		g.setColor(Color.white);

		// Sine wave
		int wave = (int) (5 * Math.sin(totalTime * 3));

		// Draw lines
		for (int i = 0; i < lines.size(); i++) {

			g.drawString(lines.get(i), 20, 50 + i * 40 + wave);

		}

	}

}