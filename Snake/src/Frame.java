import java.awt.GridLayout;

import javax.swing.JFrame;

public class Frame extends JFrame{
	
	public Frame() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);			// Stops program on exit
		setTitle("Snake");
		setResizable(false);
		
		init();
		
	}
	
	public void init() {
		
		setLayout(new GridLayout(1, 1, 0, 0));
		
		Screen scrn = new Screen();
		add(scrn);													// Add screen to the frame
		
		pack();													// Makes frame the size of
																// screen
		
		setLocationRelativeTo(null);							// Centers frame
		setVisible(true);
	}

	public static void main(String[] args) {

		new Frame();
		
	}
	
	

}
