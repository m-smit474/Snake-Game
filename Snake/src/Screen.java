import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

public class Screen extends JPanel implements Runnable{

	private static final long serialVersionUID = 1L;			// Not sure what this does
																// was recommended
	public static final int WIDTH = 800, HEIGHT = 800;
	private Thread thread;
	private boolean running = false;
	
	private BodyPart b;
	private ArrayList<BodyPart> snake;							// A dynamic array
	
	private Apple apple;
	private ArrayList<Apple> apples;
	
	private Random r;
	
	private int xCoor = 10, yCoor = 10;
	private int size = 5;
	
	private boolean right = true,
					left = false,
					up = false,
					down = false;
	
	private int ticks = 0;
	
	private Key key;
	
	public Screen() {
		setFocusable(true);
		key = new Key();
		addKeyListener(key);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		r = new Random();
		
		snake = new ArrayList<BodyPart>();
		apples = new ArrayList<Apple>();
		
		start();
	}
	
	public void tick() {										// Updates stuff
		
		if(snake.size() == 0) {
			b = new BodyPart(xCoor, yCoor, 10);
			snake.add(b);
		}
		
		if(apples.size() == 0) {
			int xCoor = r.nextInt(79);
			int yCoor = r.nextInt(79);
			
			apple = new Apple(xCoor, yCoor, 10);
			apples.add(apple);
		}
		
		for(int i = 0; i < apples.size(); i++) {
			if(xCoor == apples.get(i).getxCoor() && yCoor == apples.get(i).getyCoor()) {
				size++;
				apples.remove(i);
				i--;
			}
		}
		
		for(int i = 0; i < snake.size(); i++) {
			if(xCoor == snake.get(i).getxCoor() && yCoor == snake.get(i).getyCoor()) {
				if(i != snake.size() - 1) {
					stop();
				}
			}
		}
		
		if(xCoor < 0)
			xCoor += 81;
		else if(xCoor > 79)
			xCoor -= 81;
		else if(yCoor < 0)
			yCoor += 81;
		else if(yCoor > 79)
			yCoor -= 81;
		
		
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
		
		g.setColor(new Color(10,50,0));							// Dark green background
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
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
		
		for(int i = 0; i < apples.size(); i++) {
			apples.get(i).draw(g);
		}
		
		if(!running) {
			g.setColor(Color.RED);
			g.fillRect(0, 0, WIDTH, HEIGHT);
		}
	}
	
	public void start() {
		running = true;
		thread = new Thread(this, "Game Loop");
		thread.start();
	}
	
	public void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		repaint();
	}
	
	public void run() {											// Required by Runnable

		while(running) {
			tick();
			repaint();
		}
		
	}
	
	private class Key implements KeyListener {					// Takes keyboard input

		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			
			if(key == KeyEvent.VK_RIGHT && !left) {
				up = false;
				down = false;
				right = true;
			}
			
			if(key == KeyEvent.VK_LEFT && !right) {
				up = false;
				down = false;
				left = true;
			}
			
			if(key == KeyEvent.VK_UP && !down) {
				right = false;
				left = false;
				up = true;
			}
			
			if(key == KeyEvent.VK_DOWN && !up) {
				right = false;
				left = false;
				down = true;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
