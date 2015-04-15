package snake;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

import snakemain.Simulation;

/**
 * <p>
 * Snake class. Provides: decisions and score counting.
 * </p>
 * @author Filip
 */
public final class Snake
{
	/* ENUMS */
	public enum Movement { FORWARD, LEFT, RIGHT };
	
	/* VARIABLES */
	private Genotype genotype;
	private int score;
	
	/* METHODS */
	
	public Snake()
	{
		genotype = new Genotype();
		reset();
	}
	
	/**
	 * <p>Sets score to 0 and dead to false.</p>
	 */
	public void reset()
	{
		score = 0;
	}
	
	public void increaseScore(int points)
	{
		score += points;
	}
	
	/**
	 * <p>Getter for score.</p>
	 */
	public int getScore()
	{
		return score;
	}
	
	Random r = new Random(); //TODO: Delete
	/**
	 * <p>
	 * Performs snake decision process depending on genotype.
	 * For each situation which occures, weights for snake actions are sumed up.
	 * Result action (Movement) is the one, which has the greatest weight sum.
	 * </p>
	 * @param neighbourhood 9x9 array of map bytes oriented according to snake direction
	 * @return the result action
	 */
	public Movement decision(Simulation.Field[][] neighbourhood)
	{
		//TODO: Complete decision process
		
		int det = r.nextInt(7);
		if ( det < 5 )return Movement.FORWARD;
		if ( det == 5 )return Movement.RIGHT;
		return Movement.LEFT;
		
	}
	
	/**
	 * <p>
	 * Performs crossing with other's snake genotype.
	 * Calling snake is modified.
	 * </p>
	 * @param s snake to cross with
	 */
	public void crossWith(Snake s)
	{
		genotype.crossWith(s.genotype);
	}
	
	/**
	 * <p>Performs snake genotype mutation.</p>
	 */
	public void mutate()
	{
		genotype.mutate();
	}
	
	/**
	 * <p>Clones and returns snake.</p>
	 */
	public Snake clone()
	{
		Snake ret = new Snake();
		ret.genotype = genotype.clone();
		return ret;
	}
	
	/**
	 * <p>Initializes snakes genotype randomly, approximately equal to zero.</p>
	 */
	public void init()
	{
		genotype.init();
	}
	
	/**
	 * <p>Saves genotype to given output stream.</p>
	 * @throws IOException
	 */
	public void save(OutputStream o) throws IOException
	{
		genotype.save(o);
	}
	
	/**
	 * <p>Loads genotype from given input stream.</p>
	 * @throws IOException 
	 */
	public void load(InputStream in) throws IOException
	{
		genotype.load(in);
	}
}
