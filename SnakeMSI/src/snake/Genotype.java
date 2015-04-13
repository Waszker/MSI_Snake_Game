package snake;

import java.util.Random;

/**
 * <p>
 * Used as data structure containing weight for each action for given situations.
 * </p>
 * @author Filip
 */
public class Genotype
{
	/* CONSTANTS */
	public  static final int NUMGENS = 20;
	
	private static final int MINMUTATEGENS = 2;
	private static final int MAXMUTATEGENS = 5;
	private static final int MINCROSSGENS = 2;
	private static final int MAXCROSSGENS = 5;
	private static final int MAXMUTATION = 2;
	
	private static final Random r = new Random();
	
	/* VARIABLES */
	protected int[][] weights = null; //visible only for Genotype and Snake
	
	
	
	
	
	/* PUBLIC METHODS */
	
	public Genotype()
	{
		weights = new int[NUMGENS][3];
	}
	
	/**
	 * <p>Initializes genotype randomly, approximately equal to zero.</p>
	 */
	public void init()
	{
		for ( int i=0; i<NUMGENS; i++)
		{
			for ( int j=0; j<weights[i].length; j++ )
				weights[i][j] = 0;
			mutateGene(i);
		}
	}
	
	/**
	 * <p>Clones and returns genotype.</p>
	 */
	public Genotype clone()
	{
		Genotype ret = new Genotype();
		for ( int i=0; i<NUMGENS; i++)
			for ( int j=0; j<weights[i].length; j++ )
				ret.weights[i][j] = weights[i][j];
		return ret;
	}
	
	/**
	 * <p>Performs genotype mutation.</p>
	 */
	public void mutate()
	{
		boolean[] mutated = new boolean[NUMGENS];
		for ( int i=0; i<mutated.length; i++ )mutated[i] = false;
		
		final int gensToMutate = MINMUTATEGENS + r.nextInt(MAXMUTATEGENS - MINMUTATEGENS);
		for ( int i=0; i<gensToMutate; i++ )
		{
			int j;
			do
			{
				j = r.nextInt(NUMGENS);
			}while ( mutated[j] );
			mutateGene(j);
			mutated[j] = true;
		}
	}
	
	/**
	 * <p>Modifies calling genotype by crossing it with another one.</p>
	 * @param g genotype to cross calling genotype with
	 */
	public void crossWith(Genotype g)
	{
		boolean[] crossed = new boolean[NUMGENS];
		for ( int i=0; i<crossed.length; i++ )crossed[i] = false;
		
		final int gensToCross = MINCROSSGENS + r.nextInt(MAXCROSSGENS - MINCROSSGENS);
		for ( int i=0; i<gensToCross; i++ )
		{
			int j;
			do
			{
				j = r.nextInt(NUMGENS);
			}while ( crossed[j] );
			for ( int k=0; k<weights[j].length; k++ )
				weights[j][k] = g.weights[j][k];
			crossed[j] = true;
		}
	}
	
	
	
	
	
	
	
	/* PRIVATE AUXILIARY FUNCTIONS */
	void mutateGene(int i)
	{
		for ( int j=0; j<weights[i].length; j++ )
			weights[i][j] += r.nextInt(2*MAXMUTATION+1)-MAXMUTATION-1;
	}
}
