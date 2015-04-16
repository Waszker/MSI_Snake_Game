package snakemain;

import gui.Display;
import gui.MainWindow;

import java.io.Serializable;
import java.util.Random;

import javax.swing.JProgressBar;

import snake.Snake;

/**
 * <p>
 * Class providing interface to manage generation evolution. Provides
 * initializing new generation, saving and loading evolution state and
 * performing evolution process itself.
 * </p>
 * 
 * @author Filip
 */
public class Session implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4086000904018584540L;

	/* VARIABLES */
	private Generation currentGeneration = new Generation();
	private Simulation[] simulations = null;
	private Random r = new Random();

	/* METHODS */

	/**
	 * <p>
	 * Method initializes first generation randomly, approximately equal to
	 * zero.
	 * </p>
	 * 
	 * @param populationSize
	 *            number of snakes in a single generation
	 */
	public void init(int populationSize)
	{
		currentGeneration.init(populationSize);
		simulations = new Simulation[populationSize];
		for (int i = 0; i < populationSize; i++)
			simulations[i] = new Simulation();
	}

	/**
	 * <p>
	 * Performs single generation evolution. If display is not null, it provides
	 * preview online.
	 * </p>
	 * 
	 * @param display
	 *            drawing output, can be null
	 * @param simulationSpeed
	 *            speed of the simulation preview, ignored when display is null
	 */
	public void singleCycle(Display[] displays, int numberOfIterations,
			JProgressBar progress, boolean isGui)
	{
		resetScores();
		while ((numberOfIterations--) > 0 && !MainWindow.STOP_SIMULATION)
		{
			resetSimulations();
			runSimulations(displays, isGui);
			progress.setValue(progress.getValue() + 1);
		}
		if (!MainWindow.STOP_SIMULATION)
			currentGeneration.evaluate();
	}

	/* PRIVATE AUXILIARY FUNCTIONS */

	private void resetSimulations()
	{
		final long randSeed = r.nextLong();
		final Snake[] currentSnakes = currentGeneration.getPopulation();

		for (int i = 0; i < currentSnakes.length; i++)
			simulations[i].resetSimulation(currentSnakes[i], randSeed);
	}

	private void resetScores()
	{
		final Snake[] currentSnakes = currentGeneration.getPopulation();
		for (int i = 0; i < currentSnakes.length; i++)
			currentSnakes[i].resetScore();
	}

	private void runSimulations(final Display[] displays, boolean isGui)
	{
		Integer i = 0;
		Thread[] threads = new Thread[simulations.length];
		for (final Simulation s : simulations)
		{
			threads[i] = runSimulation(s, (isGui ? (i < displays.length ? displays[i] : null) : null));
			i++;
		}

		for (Thread t : threads)
		{
			try
			{
				t.join();
			}
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		/*
		 * for each simulation s in simulations { run thread { while (
		 * !s.isSnakeDead() ) { s.singleStep(); display( s.getMap() ); } } }
		 */
	}

	private Thread runSimulation(final Simulation s, final Display display)
	{
		Thread thread = (new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				while (!s.isSnakeDead() && !MainWindow.STOP_SIMULATION)
				{
					s.singleStep();
					if (null != display)
					{
						display.updateView(s.getMap());
						try
						{
							Thread.sleep(100);
						}
						catch (InterruptedException e)
						{
						}
					}
				}
			}
		}));
		thread.start();

		return thread;
	}
}
