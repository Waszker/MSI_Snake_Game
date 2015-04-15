package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
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
	static final String START_SIMULATION_ACTION_COMMAND = "StartSimulation";
	static final String START_ITERATION_ACTION_COMMAND = "StartIteration";
	private static final long serialVersionUID = 2740437090361841747L;
	private static final int generationSize = 8;
	private final int WINDOW_WIDTH = 640;
	private final int WINDOW_HEIGHT = 480;
	private final String title = "Snake Game";
	private Display[] displays;
	private JSpinner numberOfIterationsJSpinner;
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

	public void runSimulation()
	{
		new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				session.singleCycle(displays,
						(Integer) numberOfIterationsJSpinner.getValue());
			}
		}).start();
	}

	private void createLayout()
	{
		this.getContentPane().setLayout(
				new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		this.add(Box.createRigidArea(new Dimension(WINDOW_WIDTH,
				WINDOW_HEIGHT / 12)));
		this.add(getDisplaysJPanel(2, 4));
		// this.add(new Label("Snake Game"));
		this.add(getButtonsPanel());

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
				wrapper.add(displays[i * j + j]);
			}

		return wrapper;
	}

	private JPanel getButtonsPanel()
	{
		JPanel wrapper = new JPanel();
		wrapper.setLayout(new GridLayout(1, 2));

		JPanel generationsPanel = new JPanel();
		generationsPanel.setLayout(new BoxLayout(generationsPanel,
				BoxLayout.Y_AXIS));
		generationsPanel.setBorder(new TitledBorder("Iterations"));
		numberOfIterationsJSpinner = new JSpinner();
		numberOfIterationsJSpinner.setMaximumSize(new Dimension(60, 20));
		numberOfIterationsJSpinner.setAlignmentX(Component.CENTER_ALIGNMENT);
		generationsPanel.add(numberOfIterationsJSpinner);

		JPanel buttonWrapper = new JPanel();
		buttonWrapper.setLayout(new BoxLayout(buttonWrapper, BoxLayout.Y_AXIS));
		JButton button = new JButton("Start simulation");
		button.setActionCommand(START_SIMULATION_ACTION_COMMAND);
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonWrapper.add(button);
		button.addActionListener(actionListener);
		button = new JButton("Start iteration");
		button.setActionCommand(START_ITERATION_ACTION_COMMAND);
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonWrapper.add(button);
		button.addActionListener(actionListener);

		wrapper.add(generationsPanel);
		wrapper.add(buttonWrapper);

		return wrapper;
	}

}
