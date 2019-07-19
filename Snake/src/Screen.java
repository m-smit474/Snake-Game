import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Screen extends JPanel implements Runnable{

	private static final long serialVersionUID = 1L;			// Not sure what this does
																// was recommended
	public static final int WIDTH = 800, HEIGHT = 800;
	private Thread thread;
	private boolean running = false;
	
	private BodyPart b;
	private ArrayList<BodyPart> snake;							// A dynamic array
	
	private int xCoor = 10, yCoor = 10;
	private int size = 5;
	
	private boolean right = true,
					left = false,
					up = false,
					down = false;
	
	private int ticks = 0;
	
	public Screen() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		snake = new ArrayList<BodyPart>();
		
		start();
	}
	
	public void tick() {										// Updates stuff
		
		if(snake.size() == 0) {
			b = new BodyPart(xCoor, yCoor, 10);
			snake.add(b);
		}
		
		ticks++;
		
		if(ticks > 250000) {
			if(right) xCoor++;
			if(left) xCoor--;
			if(up) yCoor--;
			if(down) yCoor++;
			
			ticks = 0;
			
			b = new BodyPart(xCoor, yCoor, 10);
			snake.add(b);
			
			if(snake.size() > size) {
				snake.remove(0);
			}
		}
	}
	
	public void paint(Graphics g) {								// JPanel component
		
		g.clearRect(0, 0, WIDTH, HEIGHT);						// Clears screen
		
		g.setColor(Color.BLACK);
		for(int i = 0; i < WIDTH / 10; i++) {					// Vertical grid lines
			g.drawLine(i * 10, 0, i * 10, HEIGHT);
		}
		
		for(int i = 0; i < HEIGHT / 10; i++) {					// Horizontal grid lines
			g.drawLine(0, i * 10, WIDTH, i*10);
		}
		
		for(int i = 0; i < snake.size(); i++) {
			snake.get(i).draw(g);
		}
	}
	
	public void start() {
		running = true;
		thread = new Thread(this, "Game Loop");
		thread.start();
	}
	
	public void stop() {
		
	}
	
	public void run() {											// Required by Runnable

		while(running) {
			tick();
			repaint();
		}
		
	}
}
