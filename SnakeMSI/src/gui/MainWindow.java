package gui;

import java.awt.Label;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * <p>
 * Main Window class for application. It will become visible after program
 * starts.
 * </p>
 * 
 * @author Piotr Waszkiewicz
 * @version 1.0
 * 
 */
public class MainWindow extends JFrame
{
	/******************/
	/* VARIABLES */
	/******************/
	private static final long serialVersionUID = 2740437090361841747L;
	private final String title = "Snake Game";

	/******************/
	/* METHODS */
	/******************/
	public MainWindow()
	{
		super();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(640, 480);
		this.setLocationRelativeTo(null); // sets window centered
		this.setTitle(title);
		this.setResizable(false);

		createLayout();
	}

	private void createLayout()
	{
		this.getContentPane().setLayout(
				new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		this.add(new Label("Snake Game"));
		this.add(new JButton("Start simulation"));
		
	}

}
