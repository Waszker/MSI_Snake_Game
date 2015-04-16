package snakemain;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

import snake.Snake;

public final class Generation implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7974042914520978460L;
	
	
	/* CONSTANTS */
	private static final int PERCENTOFMUTATIONS = 50;
	private static final Random r = new Random();

	/* VARIABLES */
	private Snake[] population = null;

	/* METHODS */

	/**
	 * <p>
	 * Creates and initializes snakes population of given size.
	 * </p>
	 * 
	 * @param populationSize
	 *            number of snakes in population
	 */
	public void init(int populationSize)
	{
		population = new Snake[populationSize];
		for (int i = 0; i < populationSize; i++)
		{
			population[i] = new Snake();
			population[i].init();
		}
	}

	/**
	 * <p>
	 * Removes worse half of snakes, performs crosses and mutations to fill
	 * population.
	 * </p>
	 */
	public void evaluate()
	{
		// sort snakes by score descending
		Arrays.sort(population, new Comparator<Snake>()
		{
			@Override
			public int compare(Snake o1, Snake o2)
			{
				long ret = o2.getScore() - o1.getScore();
				if (ret == 0)
					return 0;
				if (ret > 0)
					return 1;
				return -1;
			}
		});

		// we replace the second half of the array
		final int start = population.length / 2 + population.length % 2;
		for (int i = start; i < population.length; i++)
		{
			final int sourceSnake = i - start;
			population[i] = population[sourceSnake].clone();

			if (r.nextInt(100) < PERCENTOFMUTATIONS)
				population[i].mutate();
			else
			{
				int toCrossWith;
				do
				{
					toCrossWith = r.nextInt(start);
				} while (toCrossWith == sourceSnake);

				population[i].crossWith(population[toCrossWith]);
			}

		}
	}

	/**
	 * <p>
	 * Getter for population.
	 * </p>
	 */
	public Snake[] getPopulation()
	{
		return population;
	}

	/**
	 * <p>
	 * Setter for population.
	 * </p>
	 */
	public void setPopulation(Snake[] p)
	{
		population = p;
	}
}
