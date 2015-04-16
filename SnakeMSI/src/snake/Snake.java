package snake;

import java.io.Serializable;
import java.util.Random;

import snakemain.Simulation;

/**
 * <p>
 * Snake class. Provides: decisions and score counting.
 * </p>
 * @author Filip
 */
public final class Snake implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 817520418058916942L;

	/* ENUMS */
	public enum Movement { FORWARD, LEFT, RIGHT };
	public enum AppleposX { APL, APR };
	public enum AppleposY { APF, APB };
	
	/* CONSTANS */
	private static final int MAXANOMALY = 20;
	
	/* VARIABLES */
	private Genotype genotype;
	private long score;
	private Random r;
	private Movement prev, prevprev;
	
	/* METHODS */
	
	public Snake()
	{
		genotype = new Genotype();
		r = new Random();
		reset();
	}
	
	/**
	 * <p>Getter for previous move</p>
	 */
	public Movement getPrev()
	{
		return prev;
	}
	
	/**
	 * <p>Getter for previous to previous move</p>
	 */
	public Movement getPrevprev()
	{
		return prevprev;
	}
	
	/**
	 * <p>Resets snake state to beginning.</p>
	 */
	public void reset()
	{
		prev = Movement.FORWARD;
		prevprev = Movement.FORWARD;
	}
	
	/**
	 * <p>Sets score to 0.</p>
	 */
	public void resetScore()
	{
		score = 0;
	}
	
	/**
	 * <p>Adds given number to score.</p>
	 * @param points
	 */
	public void increaseScore(int points)
	{
		score += points;
	}
	
	/**
	 * <p>Getter for score.</p>
	 */
	public long getScore()
	{
		return score;
	}
	/**
	 * <p>
	 * Performs snake decision process depending on genotype.
	 * For each situation which occures, weights for snake actions are sumed up.
	 * Result action (Movement) is the one, which has the greatest weight sum.
	 * </p>
	 * @param neighbourhood 9x9 array of map bytes oriented according to snake direction
	 * @return the result action
	 */
	public Movement decision(Simulation.Field[][] neighbourhood, AppleposX apx, AppleposY apy)
	{
		long[] sum = genotype.weightsForSituation(neighbourhood, prev, prevprev, apx, apy);
		for ( int i=0; i<sum.length; i++ )
			sum[i] += r.nextInt(MAXANOMALY) - MAXANOMALY/2;
		
		int result = 0;
		Movement ret = null;
		
		for ( int i=1; i<Genotype.NUMACTIONS; i++ )
		{
			if ( (sum[i] > sum[result])||(sum[i] == sum[result] && r.nextBoolean()) )
				result = i;
		}
		
		switch ( result )
		{
		case 0:
			ret = Movement.FORWARD;
			break;
		case 1:
			ret = Movement.RIGHT;
			break;
		case 2:
			ret = Movement.LEFT;
			break;
		}
		
		prevprev = prev;
		prev = ret;
		
		return ret;
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
}
