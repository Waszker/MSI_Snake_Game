package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * <p>
 * Action listener for main GUI window.
 * </p>
 * 
 * @author Piotr Waszkiewicz
 * @version 1.0
 * 
 */
public class MainWindowActionListener implements ActionListener
{
	/******************/
	/* VARIABLES */
	/******************/
	private MainWindow window;

	/******************/
	/* METHODS */
	/******************/
	public MainWindowActionListener(MainWindow window)
	{
		super();
		this.window = window;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		switch (e.getActionCommand())
		{
			case MainWindow.START_SIMULATION_ACTION_COMMAND:
				startWaitingThread(window.runSimulation(true));
				break;

			case MainWindow.START_ITERATION_ACTION_COMMAND:
				startWaitingThread(window.runSimulation(false));
				break;

			case MainWindow.STOP_ITERATION_ACTION_COMMAND:
				MainWindow.STOP_SIMULATION = true;
				window.setButtonsEnabled(true);
				break;

			case "MenuItemSave":
				window.saveSession();
				break;

			case "MenuItemLoad":
				window.loadSession();
				break;

			case "MenuItemNew":
				if (MainWindow.STOP_SIMULATION)
					(new DialogWindow(window)).getPopulationSize();
				break;
		}

	}

	private void startWaitingThread(final Thread threadToWaitFor)
	{
		window.setButtonsEnabled(false);
		new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				try
				{
					threadToWaitFor.join();
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				window.setButtonsEnabled(true);
			}
		}).start();
	}
}
