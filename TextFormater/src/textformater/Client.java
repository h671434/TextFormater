package textformater;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import textformater.ui.UserInterface;

public class Client {
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGui();
			}
		});
	}

	private static void createAndShowGui() {
		UserInterface frame = new UserInterface("TextFormater");

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1200, 800);
		frame.setLocationByPlatform(true);
		frame.setVisible(true);
	}
}
