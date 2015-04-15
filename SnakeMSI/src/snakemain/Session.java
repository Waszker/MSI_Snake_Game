package snakemain;

import gui.Display;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

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
public class Session
{
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
	 * Saves current generation (with every snake genotype) to file.
	 * </p>
	 * 
	 * @param file
	 *            path to target file
	 * @throws IOException
	 */
	public void save(String file) throws IOException
	{
		File output = new File(file);
		if (!output.exists())
			output.createNewFile();

		OutputStream out = new FileOutputStream(output);
		Snake[] snakes = currentGeneration.getPopulation();

		for (Snake s : snakes)
			s.save(out);

		out.close();
	}

	/**
	 * <p>
	 * Loads generation state from file.
	 * </p>
	 * 
	 * @param file
	 *            path to target file
	 * @throws IOException
	 */
	public void load(String file) throws IOException
	{
		File input = new File(file);
		InputStream in = new FileInputStream(input);
		List<Snake> listOfSnakes = new LinkedList<Snake>();

		while (in.available() > 0)
		{
			Snake next = new Snake();
			next.load(in);
			listOfSnakes.add(next);
		}

		currentGeneration.setPopulation((Snake[]) listOfSnakes.toArray());
		in.close();
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
	public void singleCycle(Display[] displays, int numberOfIterations)
	{
		resetSimulations();
		while (numberOfIterations > 0)
		{
			runSimulations(displays);
			numberOfIterations--;
		}
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

	private void runSimulations(final Display[] displays)
	{
		Integer i = 0;
		for (final Simulation s : simulations)
		{
			runSimulation(s, displays[i++]);
		}

		/*
		 * for each simulation s in simulations { run thread { while (
		 * !s.isSnakeDead() ) { s.singleStep(); display( s.getMap() ); } } }
		 */
	}

	private void runSimulation(final Simulation s, final Display display)
	{
		(new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				while (!s.isSnakeDead())
				{
					s.singleStep();
					if ( display != null )display.updateView(s.getMap());
					try
					{
						Thread.sleep(1);
					}
					catch (InterruptedException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		})).run();
	}
}
