package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSpinner;
import javax.swing.border.TitledBorder;

import snakemain.Session;

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
	public static boolean STOP_SIMULATION = true;
	static final String START_SIMULATION_ACTION_COMMAND = "StartSimulation";
	static final String START_ITERATION_ACTION_COMMAND = "StartIteration";
	static final String STOP_ITERATION_ACTION_COMMAND = "StopIteration";
	private static final long serialVersionUID = 2740437090361841747L;
	private static final int generationSize = 8;
	private final int WINDOW_WIDTH = 640;
	private final int WINDOW_HEIGHT = 480;
	private final String title = "Snake Game";
	private Display[] displays;
	private JSpinner numberOfIterationsJSpinner, numberOfGenerationsJSpinner;
	private JProgressBar simulationProgress;
	private JButton startSimulation, startSimulationWithouGui, stopSimulation;
	private Session session;
	private MainWindowActionListener actionListener;

	/******************/
	/* METHODS */
	/******************/
	public MainWindow()
	{
		super();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		this.setLocationRelativeTo(null); // sets window centered
		this.setTitle(title);
		this.setResizable(true);

		this.actionListener = new MainWindowActionListener(this);
		this.session = new Session();
		session.init(generationSize);
		createLayout();
	}

	public Thread runSimulation(final boolean shouldUseDisplay)
	{
		Thread simulation = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				STOP_SIMULATION = false;
				int generations = (Integer) numberOfGenerationsJSpinner
						.getValue();
				int iterations = (Integer) numberOfIterationsJSpinner
						.getValue();
				int progress = 0;

				simulationProgress.setValue(progress);
				simulationProgress.setMaximum(generations * iterations);

				while ((generations--) > 0)
				{
					session.singleCycle(displays, iterations,
							simulationProgress, shouldUseDisplay);
					progress += iterations;
					simulationProgress.setValue(progress);
				}
				STOP_SIMULATION = true;
			}
		});
		simulation.start();

		return simulation;
	}

	void setButtonsEnabled(boolean isEnabled)
	{
		startSimulation.setEnabled(isEnabled);
		startSimulationWithouGui.setEnabled(isEnabled);
		stopSimulation.setEnabled(!isEnabled);
	}

	void saveSession()
	{
		try
		{
			FileOutputStream fileOut = new FileOutputStream("saved.gen");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(session);
			out.close();
			fileOut.close();
			System.out.printf("Serialized data is saved in saved.gen");
		}
		catch (IOException i)
		{
			i.printStackTrace();
		}
	}

	void loadSession()
	{
		try
		{
			FileInputStream fileIn = new FileInputStream("saved.gen");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			session = (Session) in.readObject();
			in.close();
			fileIn.close();
			System.out.printf("Data load successful!");
		}
		catch (IOException i)
		{
			i.printStackTrace();
			return;
		}
		catch (ClassNotFoundException c)
		{
			System.out.println("Session class not found");
			c.printStackTrace();
			return;
		}
	}
	
	void createNewPopulationSize(int size)
	{
		session.init(size);
		System.out.println("New population size set to: " + size);
	}

	private void createLayout()
	{
		this.setJMenuBar(createMenu());
		this.getContentPane().setLayout(new BorderLayout(20, 20));
		this.add(Box.createRigidArea(new Dimension(WINDOW_WIDTH,
				WINDOW_HEIGHT / 12)));
		this.add(getDisplaysJPanel(2, 4), BorderLayout.CENTER);
		this.add(getWrapperPanel(), BorderLayout.SOUTH);

	}

	private JMenuBar createMenu()
	{
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(createMenuItems("File", "New", "Save", "Load"));
		// Add here new menus if needed

		return menuBar;
	}

	private JMenu createMenuItems(String submenuName, String... itemNames)
	{
		JMenu menu = new JMenu(submenuName);

		for (String name : itemNames)
		{
			JMenuItem item = new JMenuItem(name);
			item.setActionCommand("MenuItem" + name);
			item.addActionListener(actionListener);
			menu.add(item);
		}

		return menu;
	}

	private JPanel getDisplaysJPanel(int rowSize, int colSize)
	{
		JPanel wrapper = new JPanel();
		wrapper.setLayout(new GridLayout(rowSize, colSize));
		displays = new Display[rowSize * colSize];

		for (int i = 0; i < rowSize; i++)
			for (int j = 0; j < colSize; j++)
			{
				displays[i * colSize + j] = new Display(
						(j % 2 + i % 2) % 2 == 0 ? Color.WHITE : Color.GRAY);
				wrapper.add(displays[i * colSize + j]);
			}

		return wrapper;
	}

	private JPanel getWrapperPanel()
	{
		JPanel wrapper = new JPanel();
		wrapper.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		wrapper.add(getJComponentsPanel(), c);

		c.gridx = 1;
		c.gridy = 0;
		wrapper.add(getButtonsPanel(), c);

		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0.0;
		c.gridwidth = 2;
		wrapper.add(simulationProgress = new JProgressBar(), c);

		return wrapper;
	}

	private JPanel getJComponentsPanel()
	{
		JPanel generationsPanel = new JPanel();
		generationsPanel.setLayout(new BoxLayout(generationsPanel,
				BoxLayout.Y_AXIS));
		numberOfIterationsJSpinner = new JSpinner();
		// numberOfIterationsJSpinner.setMaximumSize(new Dimension(60, 20));
		numberOfIterationsJSpinner.setAlignmentX(Component.CENTER_ALIGNMENT);
		numberOfIterationsJSpinner.setBorder(new TitledBorder("Iterations"));
		generationsPanel.add(numberOfIterationsJSpinner);

		numberOfGenerationsJSpinner = new JSpinner();
		numberOfGenerationsJSpinner.setAlignmentX(Component.CENTER_ALIGNMENT);
		numberOfGenerationsJSpinner.setBorder(new TitledBorder("Generations"));
		generationsPanel.add(numberOfGenerationsJSpinner);

		return generationsPanel;
	}

	private JPanel getButtonsPanel()
	{
		JPanel buttonWrapper = new JPanel();
		buttonWrapper.setLayout(new BoxLayout(buttonWrapper, BoxLayout.Y_AXIS));

		JButton button = startSimulation = new JButton("Start simulation");
		button.setActionCommand(START_SIMULATION_ACTION_COMMAND);
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonWrapper.add(button);
		button.addActionListener(actionListener);

		button = startSimulationWithouGui = new JButton(
				"Start simulation wihout gui");
		button.setActionCommand(START_ITERATION_ACTION_COMMAND);
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonWrapper.add(button);
		button.addActionListener(actionListener);

		button = stopSimulation = new JButton("Stop simulation");
		button.setActionCommand(STOP_ITERATION_ACTION_COMMAND);
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonWrapper.add(button);
		button.addActionListener(actionListener);
		button.setEnabled(false);

		return buttonWrapper;
	}

}
