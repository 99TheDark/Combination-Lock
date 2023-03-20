package main;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public static void main(String args[]) {
		
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setTitle("Combination Lock");
		
		Dimension minSize = new Dimension(400, 250);
		window.setMinimumSize(minSize);
		
		Panel panel = new Panel(window);
		window.add(panel);
		
		window.pack();

		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		panel.startThread();
		
	}

}