package gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JSpinner;

/**
 * <p>
 * Dialog window takes numer of populations.
 * </p>
 * 
 * @author Piotr Waszkiewicz
 * @version 1.0
 * 
 */
public class DialogWindow extends JFrame
{
	/******************/
	/* VARIABLES */
	/******************/
	private static final long serialVersionUID = 3797092962653333938L;
	private MainWindow window;
	private JSpinner sizeSpinner;

	/******************/
	/* METHODS */
	/******************/
	public DialogWindow(MainWindow window)
	{
		super();
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(250, 80);
		this.setLocationRelativeTo(window); // sets window centered
		this.setTitle("Input size of population");
		this.setResizable(false);
		this.window = window;
		setLayout();
	}

	/**
	 * <p>
	 * Sets dialog visible enabling to input new population size.
	 * </p>
	 */
	public void getPopulationSize()
	{
		this.setVisible(true);
	}

	private void setLayout()
	{
		this.getContentPane().setLayout(
				new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		sizeSpinner = new JSpinner();
		sizeSpinner.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(sizeSpinner);

		JButton button = new JButton("Ok");
		button.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				 window.createNewPopulationSize((Integer)sizeSpinner.getValue());
			}
		});
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(button);
	}
}
