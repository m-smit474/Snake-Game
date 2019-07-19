import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Screen extends JPanel implements Runnable{

	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 800, HEIGHT = 800;
	private Thread thread;
	private boolean running = false;
	
	public Screen() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		start();
	}
	
	public void tick() {										// Updates stuff
		
	}
	
	public void paint(Graphics g) {								// JPanel component
		
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
